package com.example.noteactivity;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteActivityViewModel extends ViewModel {

    public static final String ORIGINAL_NOTE_COURSE_ID= "com.example.noteactiviy.ORIGINAL_NOTE_COURSE_ID";
    public static final String ORIGINAL_NOTE_TITLE_ID= "com.example.noteactiviy.ORIGINAL_NOTE_TITLE_ID";
    public static final String ORIGINAL_NOTE_TEXT_ID= "com.example.noteactiviy.ORIGINAL_NOTE_TEXT_ID";


    public String mOriginalNoteCourseId;
    public String mOriginalNoteTitle;
    public String mOriginalNoteText;
    public  boolean mIsNewlyCreated= true;

    public void saveState(Bundle bundle) {
        bundle.putString( ORIGINAL_NOTE_COURSE_ID, mOriginalNoteCourseId);
        bundle.putString( ORIGINAL_NOTE_TITLE_ID, mOriginalNoteTitle);
        bundle.putString( ORIGINAL_NOTE_TEXT_ID, mOriginalNoteText);
    }

    public void restoreState(Bundle bundle){
        mOriginalNoteCourseId= bundle.getString(ORIGINAL_NOTE_COURSE_ID);
        mOriginalNoteTitle= bundle.getString(ORIGINAL_NOTE_TITLE_ID);
        mOriginalNoteText= bundle.getString(ORIGINAL_NOTE_TEXT_ID);
    }
}
