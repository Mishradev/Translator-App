package com.nitish.android.speech;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;

public class CustomMaterialFloatingButton extends FloatingActionButton {

    private boolean isRecording = false;

    private static final int[] STATE_RECORDING = {R.attr.state_recording};

    public CustomMaterialFloatingButton(Context context) {
        super(context);
    }

    public CustomMaterialFloatingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMaterialFloatingButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecordingState, 0, 0);

        // R.styleable.CustomStates_has_new_data is an ID that is created automatically when you create your
        // custom state list in attributes.xml
        isRecording = typedArray.getBoolean(R.styleable.RecordingState_state_recording, false);
    }

    public void setRecording(boolean recording){
        isRecording = recording;
        refreshDrawableState();
    }


    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        // If the message is unread then we merge our custom message unread state into
        // the existing drawable state before returning it.
        if (isRecording) {
            // We are going to add 1 extra state.
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, STATE_RECORDING);
            return drawableState;
        } else {
            return super.onCreateDrawableState(extraSpace);
        }
    }

}
