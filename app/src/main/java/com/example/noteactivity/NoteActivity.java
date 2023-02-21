package com.example.noteactivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.noteactivity.databinding.ActivityContentNoteBinding;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityContentNoteBinding binding;

    public static final int POSITION_NOT_SET= -1;
    public static final String NOTE_POSITION= "com.example.noteactivity.NOTE_POSITION";
    NoteInfo mNote;
    boolean misNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContentNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> courseInfoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        courseInfoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.contentNote.spContentNote.setAdapter(courseInfoArrayAdapter);


        //leer los intent
        readDisplayStateValue();

        //mostrar las notas seleccionadas
        if(!misNewNote)
            displayNote();
    }
    //llamar campos en el content_activity y llenamos con el noteinfo  seleccionado
    private void displayNote() {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(mNote.getCourse());

        binding.contentNote.spContentNote.setSelection(courseIndex);
        binding.contentNote.etFirstEditText.setText(mNote.getTitle());
        binding.contentNote.etSecondEditText.setText(mNote.getText());

    }

    private void readDisplayStateValue() {

        //obtener referencia enviada usando parcelable 
        Intent intent = getIntent();
        //mNote = intent.getParcelableExtra(NOTE_POSITION);
        int postion= intent.getIntExtra(NOTE_POSITION,POSITION_NOT_SET);
        //misNewNote= mNote== null;
        misNewNote= postion == POSITION_NOT_SET;

        if(!misNewNote)
            mNote= DataManager.getInstance().getNotes().get(postion);
    }

}