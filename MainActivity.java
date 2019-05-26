package com.nitish.android.speech;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import com.nitish.android.speech.SelectableCardsAdapter.Item;

import androidx.appcompat.view.ActionMode;
import com.splunk.mint.Mint;



public class MainActivity extends AppCompatActivity implements MessageDialogFragment.Listener ,
        View.OnClickListener,Handler.Callback {

    private static final String FRAGMENT_MESSAGE_DIALOG = "message_dialog";

    private static final String STATE_RESULTS = "results";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;

    private SpeechService mSpeechService;
    private MediaRecorder recorder = null;
    private String mCurrentAudioTextFilePath =  null;
    private static final String TAG = "Speech";
    private ProgressDialog dialog;
    private BottomAppBar bottomAppBar;
    private ActionMode actionMode;
    private CustomMaterialFloatingButton floatingActionButton = null;
    private SelectableCardsAdapter adapter;
    private SelectionTracker<Long> selectionTracker;
    private boolean isRecording = false;
    private String translatedString = null;
    private ContentResolver mContentResolver = null;
    private File mTempRecordingFile = null;
    private Handler mHandler ;
    private CoordinatorLayout coordinatorLayout = null;


    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            mSpeechService = SpeechService.from(binder);
            mSpeechService.addListener(mSpeechServiceListener);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mSpeechService = null;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.demo_primary, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_language:
                showActionSheet();
            break;
            case R.id.menu_search:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean showActionSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.cat_bottomsheet_content);
        View bottomSheetInternal = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        ListView listSheet = bottomSheetDialog.findViewById(R.id.sheet_list);
        int[] colors = {0, 0xFF000000, 0}; // red for the example
        listSheet.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        listSheet.setDividerHeight(1);
        BottomSheetBehavior.from(bottomSheetInternal).setPeekHeight(400);
        bottomSheetDialog.show();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.setApplicationEnvironment(Mint.appEnvironmentDevelopment);
        Mint.initAndStartSession(this.getApplication(), "//api key");
        File storage = getStoragePath();
        if(!storage.exists()){
            storage.mkdir();
        }
        mContentResolver = getContentResolver();
        setContentView(R.layout.cat_bottomappbar);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator_layout);
        bottomAppBar = findViewById(R.id.bar);
        setSupportActionBar(bottomAppBar);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        setUpRecyclerView(recyclerView);
        mHandler = new Handler(getMainLooper(),this);
    }

    protected void setUpRecyclerView(RecyclerView recyclerView) {
        adapter = new SelectableCardsAdapter(this);
        adapter.setItems(queryItem());
        recyclerView.setAdapter(adapter);
        selectionTracker =
                new SelectionTracker.Builder<>(
                        "card_selection",
                        recyclerView,
                        new SelectableCardsAdapter.KeyProvider(adapter),
                        new SelectableCardsAdapter.DetailsLookup(recyclerView),
                        StorageStrategy.createLongStorage())
                        .withSelectionPredicate(SelectionPredicates.createSelectAnything())
                        .build();

        adapter.setSelectionTracker(selectionTracker);
        selectionTracker.addObserver(
                new SelectionTracker.SelectionObserver<Long>() {
                    @Override
                    public void onSelectionChanged() {
                        if (selectionTracker.getSelection().size() > 0) {
                            if (actionMode == null) {
                                actionMode = startSupportActionMode(callback);
                            }
                            actionMode.setTitle(String.valueOf(selectionTracker.getSelection().size()));
                        } else if (actionMode != null) {
                            actionMode.finish();
                        }
                    }
                });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private boolean checkForValidInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if(!checkForValidInternetConnection()){
                    Toast.makeText(this, "Application don't have a valid internet connection",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isRecording){
                    startRecording();
                } else {
                    isRecording = false;
                    showSnackbar("Recording stopped");
                    stopRecording();
                    showProgressDialog("Translation in progress, please wait");
                    doTranslate();
                }
                break;
            default:
                break;
        }
    }


    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mTempRecordingFile = new File(getTempStoragePath(),""+System.currentTimeMillis()+".3gp");
        //currentFilePath = f.getAbsolutePath();
        Log.i(TAG,"Temp File Recording is "+mTempRecordingFile.getAbsolutePath());
        recorder.setOutputFile(mTempRecordingFile.getAbsolutePath());
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setAudioSamplingRate(8000);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        try {
            recorder.start();
        }catch(IllegalStateException e){
            e.printStackTrace();
            Log.e(TAG,"start called in an uninsialized state");
            releaseMediaRecorder();
        }
        isRecording  = true;
        showSnackbar("Recording started");
        floatingActionButton.setRecording(true);
    }


    private void stopRecording(){
        if(recorder !=null) {
            recorder.stop();
            releaseMediaRecorder();
            recorder = null;
        }
        floatingActionButton.setRecording(false);
    }

    private void releaseMediaRecorder(){
        recorder.reset();
        recorder.release();
    }


    @Override
    public boolean handleMessage (Message msg){
        switch (msg.what){
            case 0:
                confirmSaveFile();
             break;
            default:
             break;
        }
        return true;
    }


    private void confirmSaveFile() {
        View view = LayoutInflater.from(this).inflate(R.layout.edit_text, null);
        final EditText subjectEditText = view.findViewById(R.id.text_1);
        final EditText filenameEditText = view.findViewById(R.id.text_2);
        MaterialAlertDialogBuilder builder =  new MaterialAlertDialogBuilder(this)
                .setTitle("Save File")
                .setView(view)
                .setPositiveButton("Save",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         String subject =  subjectEditText.getText().toString();
                         String filename = filenameEditText.getText().toString();
                         if(!TextUtils.isEmpty(subject) && !TextUtils.isEmpty(filename)){
                            showProgressDialog("Translating File");
                            saveFileAndTranslateToEnglish(subject,filename);
                         }
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         mTempRecordingFile.delete();
                    }
                });
        builder.show();
    }

    private void saveFileAndTranslateToEnglish(final String subject, String fileName) {
        File f = mTempRecordingFile;
        File outFile = new File(getStoragePath(),fileName+".3gp");
        try {
            copyFile(new FileInputStream(f), new FileOutputStream(outFile));
            f.delete();
         } catch(Exception e){
            e.printStackTrace();
            hideProgressDialog();
            showError("Could not save file, error in saving file");
            f.delete();
            return;
        }

       AsyncTask<Void,Void,Item> asyncTask =  new AsyncTask<Void, Void, Item>(

        ) {
            @Override
            protected Item doInBackground(Void... voids) {
                Translate translate = TranslateOptions.getDefaultInstance().getService();
                TranslateOptions translateOptions = TranslateOptions.newBuilder().setApiKey("AIzaSyAI-1TxCuC5UogsYz3M6VwzytDXniOYiDc").setProjectId("voice-1555146549002").build();
                Translate translate1 = new TranslateOptions.DefaultTranslateFactory().create(translateOptions);
                Translation translation =   translate1.translate(translatedString,Translate.TranslateOption.sourceLanguage("zh"),
                        Translate.TranslateOption.targetLanguage("en"));
                if(translation != null){
                    File file = new File(getStoragePath(),fileName+".txt");
                    FileWriter fileWriter =  null;
                    try{
                        fileWriter = new FileWriter(file);
                        fileWriter.write(translation.getTranslatedText());
                        fileWriter.flush();
                        Item item = new Item(subject,outFile.getAbsolutePath(),file.getAbsolutePath(),"",-1);
                        return item;
                    }catch (IOException e){
                        e.printStackTrace();
                        return  null;
                    }finally {
                        if (fileWriter != null){
                            try {
                                fileWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Item item) {
                    hideProgressDialog();
                    if(item != null){
                       Uri uri =   insertNewRecording(item);
                       if(uri != null){
                          int id =  Integer.parseInt(uri.getLastPathSegment());
                          item.setId(id);
                          adapter.addItem(item);
                       }
                    }
            }
        };
        asyncTask.execute();
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream) throws IOException {
                try {
                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                    inputStream.close();
                }
    }


    private void showError(String errorMessaage){
        Toast.makeText(this,errorMessaage,Toast.LENGTH_LONG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRecording();
    }




    private void doTranslate(){
        try {
                    mSpeechService.recognizeInputStream(new FileInputStream(mTempRecordingFile));
                }catch(java.io.FileNotFoundException e){
                    e.printStackTrace();
                }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Prepare Cloud Speech API
        bindService(new Intent(this, SpeechService.class), mServiceConnection, BIND_AUTO_CREATE);

        // Start listening to voices
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            showPermissionMessageDialog();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    @Override
    protected void onStop() {
        if(mSpeechService !=null) {
            mSpeechService.removeListener(mSpeechServiceListener);
        }
        unbindService(mServiceConnection);
        mSpeechService = null;
        super.onStop();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (permissions.length == 1 && grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                showPermissionMessageDialog();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            finish();
        }
    }


    private void showPermissionMessageDialog() {
        MessageDialogFragment
                .newInstance(getString(R.string.permission_message))
                .show(getSupportFragmentManager(), FRAGMENT_MESSAGE_DIALOG);
    }


    @Override
    public void onMessageDialogDismissed() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                REQUEST_RECORD_AUDIO_PERMISSION);
    }

    private final SpeechService.Listener mSpeechServiceListener =
            new SpeechService.Listener() {
                @Override
                public void onSpeechRecognized(final String text, final boolean isFinal) {
                    if (!TextUtils.isEmpty(text) && isFinal) {
                         Log.i(TAG,"trnslatedString "+text);
                         translatedString = text;
                         mHandler.sendEmptyMessage(0);
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressDialog();
                                showError("Invalid audio, please record again");
                            }
                        });
                    }
                }

                @Override
                public void  onCompleted(){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                            hideProgressDialog();
                       }
                   });
                }

                @Override
                public void onError(){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showError("Invalid audio, please record again");
                            hideProgressDialog();
                        }
                    });
                }
            };


    private void showProgressDialog(String s1){
        if(!isFinishing()) {
            dialog = new ProgressDialog(this);
            dialog.setMessage(s1);
            dialog.show();
        }
    }

    private void hideProgressDialog(){
        if(dialog != null && dialog.isShowing() && !isFinishing()){
            dialog.dismiss();
            dialog = null;
        }
    }


    private  ActionMode.Callback callback = new ActionMode.Callback(){

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.action_mode_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_share:
                    shareItem();
                    break;
                case R.id.action_delete:
                    deleteItem();
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectionTracker.clearSelection();
            actionMode = null;
        }
    };

    private  void shareItem(){
         List <Item>items =  adapter.getItems();
         ArrayList <Uri>uri  = null;
        if(selectionTracker != null){
            androidx.recyclerview.selection.Selection<Long>selections  = selectionTracker.getSelection();
            uri = new ArrayList<>();
            int counter = 0;
            for(long id : selections){
                Log.i(TAG,"selected items are "+id);
                Item item =  items.get((int)id);
                int _id = item.getId();
                Cursor cursor = mContentResolver.query(SpeechStorageDescriptor.SpeechStorage.CONTENT_URI,null,
                        "_id = ?",new String[]{""+_id},null);
                if(cursor.moveToFirst()){
                    String filenameText = cursor.getString(cursor.getColumnIndexOrThrow(SpeechStorageDescriptor.SpeechStorage.Cols.DESTINATION_TEXT_FILE_PATH));
                    File f = new File(filenameText);
                    Log.i(TAG,"File sharing is"+f.getAbsolutePath());
                    Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "fileprovider", f);
                    uri.add(contentUri);
                    counter++;
                }
            }
            if(uri.size()>0){
                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("text/*");
                //intent.setData(contentUri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //startActivityForResult(intent,100);
                //intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(intent, "Share file with"));
            }
        }
        actionMode.finish();
    }

    private void deleteItem(){
        if(selectionTracker != null){
            List <Item>items =  adapter.getItems();
            androidx.recyclerview.selection.Selection<Long>selections  = selectionTracker.getSelection();
            for(long id : selections){
                Item item =  items.get((int)id);
                int _id = item.getId();
                int numRowsDeleted = mContentResolver.delete(SpeechStorageDescriptor.SpeechStorage.CONTENT_URI,
                        "_id = ?",new String[]{""+_id});
                if(numRowsDeleted > 0){
                    adapter.deleteItemAtIndex((int)id);
                }
            }
        }
        actionMode.finish();
    }


    private File getStoragePath(){
       return new File(MainActivity.this.getExternalFilesDir(null),"folder");
    }


    private File getTempStoragePath(){
        return getExternalCacheDir ();
    }

    private Uri insertNewRecording(Item item) {
        ContentValues values = new ContentValues();
        values.put(SpeechStorageDescriptor.SpeechStorage.Cols.SOURCE_LANGUAGE, "zh");
        values.put(SpeechStorageDescriptor.SpeechStorage.Cols.AUDIO_FILE_PATH,item.getFilenameAudio());
        values.put(SpeechStorageDescriptor.SpeechStorage.Cols.DESTINATION_TEXT_FILE_PATH,item.getFilenameText());
        values.put(SpeechStorageDescriptor.SpeechStorage.Cols.RECORDING_TIME,""+System.currentTimeMillis());
        values.put(SpeechStorageDescriptor.SpeechStorage.Cols.Source_AUDIO_ENCODING,"amr");
        values.put(SpeechStorageDescriptor.SpeechStorage.Cols.Source_AUDIO_SUBJECT,item.getTitle());
        return mContentResolver.insert(SpeechStorageDescriptor.SpeechStorage.CONTENT_URI,values);
    }

    private List<Item> queryItem(){
        List<Item>itemList = new ArrayList<>();
        Cursor cursor = mContentResolver.query(SpeechStorageDescriptor.SpeechStorage.CONTENT_URI,
                null,null,null,"_id DESC");
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(SpeechStorageDescriptor.SpeechStorage.Cols.Source_AUDIO_SUBJECT));
                String filenameAudio = cursor.getString(cursor.getColumnIndexOrThrow(SpeechStorageDescriptor.SpeechStorage.Cols.AUDIO_FILE_PATH));
                String filenameText = cursor.getString(cursor.getColumnIndexOrThrow(SpeechStorageDescriptor.SpeechStorage.Cols.DESTINATION_TEXT_FILE_PATH));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(SpeechStorageDescriptor.SpeechStorage.Cols._ID));
                Item item = new Item(title, filenameAudio, filenameText, "", id);
                itemList.add(item);
            }
            cursor.close();
        }
        return itemList;
    }

    private void showSnackbar(CharSequence text) {
        Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT)
                .setAnchorView(floatingActionButton.getVisibility() == View.VISIBLE ? floatingActionButton : bottomAppBar)
                .show();
    }

}
