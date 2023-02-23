package com.example.noteactivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    int mNotePosition;
    boolean mIsCancelling;

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

        if(misNewNote){
            createNewNote();
        }else {
            mNote= DataManager.getInstance().getNotes().get(postion);
        }
    }

    private void createNewNote() {
        DataManager dm= DataManager.getInstance();
        mNotePosition = dm.createNewNote();

        mNote= dm.getNotes().get(mNotePosition);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note, menu);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       if(item.getItemId()== R.id.sendEmail){
           sendEmail();
           return true;
       }else if (item.getItemId() == R.id.cancel){
            mIsCancelling =true;
            finish(); //activity ends
       }

        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {

        //traer detalles del curso seleccionado
        CourseInfo course = (CourseInfo) binding.contentNote.spContentNote.getSelectedItem();
       //cargar asunto y body que llevara el correo
        String subject = binding.contentNote.etFirstEditText.getText().toString();
        String body= "Check whar I learned in the Pluralsight course \"" +
                course.getTitle() + "\"\n" +  binding.contentNote.etSecondEditText.getText().toString();

        //identificar a donde enviaremos la info
        Intent intent = new Intent(Intent.ACTION_SEND);
        //indicamos el tipo de dato que se enviara, en este caso es un mensaje tipo email
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mIsCancelling){
            if(misNewNote)
                DataManager.getInstance().removeNote(mNotePosition);
        }else {
            //save the note
            saveNote();
        }
    }

    private void saveNote(){
        mNote.setCourse((CourseInfo)  binding.contentNote.spContentNote.getSelectedItem());
        mNote.setTitle(binding.contentNote.etFirstEditText.getText().toString());
        mNote.setText(binding.contentNote.etSecondEditText.getText().toString());
    }
}
