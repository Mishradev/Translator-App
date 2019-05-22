

package com.nitish.android.speech;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

/** An Adapter that works with a collection of selectable card items */
class SelectableCardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<Item> items;

  private SelectionTracker<Long> selectionTracker;

  private Context mContext;

  public SelectableCardsAdapter(Context context) {
    this.items = new ArrayList<>();
    this.mContext = context;

  }

  public void setItems(List<Item> items) {
    this.items = items;
  }


  public List<Item> getItems(){
    return items;
  }

  public void addItem(Item item){
    items.add(0,item);
    notifyItemInserted(0);
  }

  @Override
  public int getItemViewType(int position) {
    return 0;
  }

  public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
    this.selectionTracker = selectionTracker;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.recording_card_item, parent, false);
    return new ItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
    Item item = items.get(position);
    viewHolder.itemView.setTag(item.id);
    ((ItemViewHolder) viewHolder).bind(item, position);
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public void deleteItemAtIndex(int id) {
    items.remove(id);
    notifyItemRemoved(id);
  }

  class ItemViewHolder extends RecyclerView.ViewHolder {

    private final Details details;
    private final MaterialCardView materialCardView;
    private final TextView titleView;
    private final TextView filenameAudio;
    private final TextView filenameText;
    private final TextView filenameEnglishAudio;
    private final Button playButton_audioChinese;
    private final Button playButton_audioEnglish;
    private final Button viewButtonText;

    ItemViewHolder(View itemView) {
      super(itemView);
      materialCardView = itemView.findViewById(R.id.item_card);
      titleView = itemView.findViewById(R.id.cat_card_title);
      filenameAudio = itemView.findViewById(R.id.cat_card_audio_file);
      filenameText = itemView.findViewById(R.id.cat_card_english_text);
      filenameEnglishAudio = itemView.findViewById(R.id.cat_card_english_audio);
      playButton_audioChinese = itemView.findViewById(R.id.play_id);
      playButton_audioEnglish = itemView.findViewById(R.id.play_english);
      viewButtonText = itemView.findViewById(R.id.text_view);
      details = new Details();
    }

    private void bind(Item item, int position) {
      details.position = position;
      titleView.setText(item.title);
      filenameAudio.setText(getOnlyFileName(item.filenameAudio));
      filenameText.setText(getOnlyFileName(item.filenameText));
      filenameEnglishAudio.setText(getOnlyFileName(item.filenameEnglishAudio));
      playButton_audioEnglish.setTag("");
      playButton_audioEnglish.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Toast.makeText(mContext,"English audio not generated", Toast.LENGTH_SHORT).show();
        }
      });

      playButton_audioChinese.setTag(item.filenameAudio);

      playButton_audioChinese.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {

          File f = new File(playButton_audioChinese.getTag().toString());
          Uri contentUri = FileProvider.getUriForFile(mContext, "fileprovider", f);
          Log.i("TAG","File playing is"+f.getAbsolutePath());
          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setType("audio/*");
          intent.setData(contentUri);
          intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
          } else {
            Toast.makeText(mContext,"No application present in the file to view the text file",Toast.LENGTH_SHORT).show();
          }

        }
      });

      viewButtonText.setTag(item.filenameText);

      viewButtonText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           File f = new File(viewButtonText.getTag().toString());
           Uri contentUri = FileProvider.getUriForFile(mContext, "fileprovider", f);
          Log.i("TAG","File viewing is"+f.getAbsolutePath());
          Intent intent = new Intent(Intent.ACTION_VIEW);
          intent.setType("text/*");
          intent.setData(contentUri);
          intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
          } else {
            Toast.makeText(mContext,"No application present in the file to view the text file",Toast.LENGTH_SHORT).show();
          }

        }
      });

      if (selectionTracker != null) {
        bindSelectedState();
      }
    }


    private String getOnlyFileName(String file){
      String args1[]  = file.split("/");
      if(args1.length>0){
        String fileComponents = args1[args1.length-1];
        return  fileComponents;
      }
      return  file;
    }


    private void bindSelectedState() {
      materialCardView.setChecked(selectionTracker.isSelected(details.getSelectionKey()));
    }

    ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
      return details;
    }
  }

  static class DetailsLookup extends ItemDetailsLookup<Long> {

    private final RecyclerView recyclerView;

    DetailsLookup(RecyclerView recyclerView) {
      this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
      View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
      if (view != null) {
        RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
        if (viewHolder instanceof ItemViewHolder) {
          return ((ItemViewHolder) viewHolder).getItemDetails();
        }
      }
      return null;
    }
  }

  static class KeyProvider extends ItemKeyProvider<Long> {

    KeyProvider(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
      super(ItemKeyProvider.SCOPE_MAPPED);
    }

    @Nullable
    @Override
    public Long getKey(int position) {
      return (long) position;
    }

    @Override
    public int getPosition(@NonNull Long key) {
      long value = key;
      return (int) value;
    }
  }

  static class Item {

    private  String title;
    private  String filenameAudio;
    private  String filenameText;
    private  String filenameEnglishAudio;
    private  int id;

    Item(String title, String filenameAudio,String filenameText,String filenameEnglishAudio, int id) {
      this.title = title;
      this.filenameAudio = filenameAudio;
      this.filenameText = filenameText;
      this.filenameEnglishAudio = filenameEnglishAudio;
      this.id = id;
    }


    public void setId(int id){
      this.id = id;
    }

    public String getTitle() {
      return title;
    }

    public String getFilenameAudio() {
      return filenameAudio;
    }

    public String getFilenameText() {
      return filenameText;
    }

    public String getFilenameEnglishAudio() {
      return filenameEnglishAudio;
    }

    public int getId() {
      return id;
    }
  }

  static class Details extends ItemDetailsLookup.ItemDetails<Long> {

    long position;

    Details() {
    }

    @Override
    public int getPosition() {
      return (int) position;
    }

    @Nullable
    @Override
    public Long getSelectionKey() {
      return position;
    }

    @Override
    public boolean inSelectionHotspot(@NonNull MotionEvent e) {
      return false;
    }

    @Override
    public boolean inDragRegion(@NonNull MotionEvent e) {
      return true;
    }
  }
}
