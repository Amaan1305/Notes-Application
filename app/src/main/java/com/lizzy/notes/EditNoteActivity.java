package com.lizzy.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {

    private EditText titleEditNote,MainContentEditNote;
    private FloatingActionButton SaveEditFloatingButton;
    private Toolbar EditNoteToolBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        setData();

        setSupportActionBar(EditNoteToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent getData = getIntent();

        String noteTitle = getData.getStringExtra("title");
        String noteContent = getData.getStringExtra("content");

        titleEditNote.setText(noteTitle);
        MainContentEditNote.setText(noteContent);

        SaveEditFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle = titleEditNote.getText().toString();
                String newContent = MainContentEditNote.getText().toString();

                if (newTitle.isEmpty() || newContent.isEmpty()){
                    Toast.makeText(EditNoteActivity.this, "Either of the Fields are Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(getData.getStringExtra("noteId"));
                    Map<String,Object> note = new HashMap<>();
                    note.put("title",newTitle);
                    note.put("content",newContent);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditNoteActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditNoteActivity.this,NotesMainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditNoteActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setData() {
        titleEditNote = findViewById(R.id.EditNotesTitle);
        MainContentEditNote = findViewById(R.id.EditNotesMainContent);
        SaveEditFloatingButton = findViewById(R.id.floatingSaveEditNotes);
        EditNoteToolBar = findViewById(R.id.mainEditNoteToolBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


    }
}