package com.lizzy.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateNotesActivity extends AppCompatActivity {

    private EditText titleCreateNote,MainContentCreateNote;
    private FloatingActionButton saveFloatingButton;
    private Toolbar createNoteToolBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        setData();

        setSupportActionBar(createNoteToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        saveFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleCreateNote.getText().toString();
                String content = MainContentCreateNote.getText().toString();
                if (title.isEmpty() || content.isEmpty()){
                    Toast.makeText(CreateNotesActivity.this, "Either of the Fields are Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();
                    Map<String,Object> note = new HashMap<>();
                    note.put("title",title);
                    note.put("content",content);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CreateNotesActivity.this, "Note Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreateNotesActivity.this,NotesMainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateNotesActivity.this, "Failed to Create Note", Toast.LENGTH_SHORT).show();
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
        titleCreateNote = findViewById(R.id.createNotesTitle);
        MainContentCreateNote = findViewById(R.id.createNotesMainContent);
        saveFloatingButton = findViewById(R.id.floatingSaveNotes);
        createNoteToolBar = findViewById(R.id.mainCreateNoteToolBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



    }
}