package com.example.noteactivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.noteactivity.databinding.ActivityNoteListBinding;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNoteListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNoteListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //crear una instancia de new noteActivity para crear una activity new
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
            }
        });

        initDisplayContent();
    }

    private void initDisplayContent(){
        //cargar id a utilizar
        final ListView listnote = binding.contentNoteList.lvContentNoteList;

        //carga data a mostrar
        List<NoteInfo> notes = DataManager.getInstance().getNotes();

        //crear adapter que tendra la lista de datos a mostrar
        ArrayAdapter<NoteInfo> adapterNotes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);

        //agregar adapter a id que tiene la listview en el ativity
        listnote.setAdapter(adapterNotes);

        //agregar onclick a opciones del adapter
        listnote.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //abre la actividad sin mostrar nada en especifico
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
              //  NoteInfo note = (NoteInfo) listnote.getItemAtPosition( i ); //obtener objeto clickeado

                //agregar como extra elemento seleccionado
                //para enviar un objeto noteinfo se esta haciendo uso de parcelable, para permitir enviar reference type via intentos
                intent.putExtra(NoteActivity.NOTE_POSITION,i);
                startActivity(intent);
            }
        });

    }




}