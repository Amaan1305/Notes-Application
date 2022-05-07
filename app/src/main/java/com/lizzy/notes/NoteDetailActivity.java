package com.lizzy.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetailActivity extends AppCompatActivity {
    
    private TextView detailTitle,detailContent;
    private FloatingActionButton editFloatingButton;
    private Toolbar infoNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        
        setData();

        setSupportActionBar(infoNotes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent getData = getIntent();

        editFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteDetailActivity.this,EditNoteActivity.class);
                intent.putExtra("title",getData.getStringExtra("title"));
                intent.putExtra("content",getData.getStringExtra("content"));
                intent.putExtra("noteId",getData.getStringExtra("noteId"));
                startActivity(intent);
            }
        });

        detailTitle.setText(getData.getStringExtra("title"));
        detailContent.setText(getData.getStringExtra("content"));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setData() {
        detailTitle = findViewById(R.id.DetailNotesTitle);
        detailContent = findViewById(R.id.DetailNotesMainContent);
        editFloatingButton = findViewById(R.id.floatingEditNotes);
        infoNotes = findViewById(R.id.mainDetailNoteToolBar);

        
    }
}