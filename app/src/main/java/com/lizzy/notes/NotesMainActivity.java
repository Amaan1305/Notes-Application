package com.lizzy.notes;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lizzy.notes.LoginActivity.LoginMainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesMainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView RVNotes;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter<FireBaseModel,NoteViewHolder> NotesAdapter;
    private Toolbar mtoolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_main);

        setData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesMainActivity.this,CreateNotesActivity.class);
                startActivity(intent);
            }
        });

        Query query = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").orderBy("title",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<FireBaseModel> usernotes = new FirestoreRecyclerOptions.Builder<FireBaseModel>().setQuery(query,FireBaseModel.class).build();


        NotesAdapter = new FirestoreRecyclerAdapter<FireBaseModel, NoteViewHolder>(usernotes) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull FireBaseModel model) {

                int colorchanger = getRandomColor();
                holder.noteLayout.setBackgroundColor(holder.itemView.getResources().getColor(colorchanger,null));

                holder.noteTitle.setText(model.getTitle());
                holder.noteContent.setText(model.getContent());

                String DocId = NotesAdapter.getSnapshots().getSnapshot(position).getId();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NotesMainActivity.this,NoteDetailActivity.class);
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("content",model.getContent());
                        intent.putExtra("noteId",DocId);
                        startActivity(intent);
                    }
                });

                holder.optionsNotes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                        popupMenu.setGravity(Gravity.END);
                        popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Intent intent = new Intent(v.getContext(),EditNoteActivity.class);
                                intent.putExtra("title",model.getTitle());
                                intent.putExtra("content",model.getContent());
                                intent.putExtra("noteId",DocId);
                                v.getContext().startActivity(intent);
                                return false;
                            }
                        });
                        popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(DocId);
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(NotesMainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(NotesMainActivity.this, "Failed to Delete", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });

            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item,parent,false);
                return new NoteViewHolder(view);
            }
        };

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        RVNotes.setLayoutManager(staggeredGridLayoutManager);
        RVNotes.setAdapter(NotesAdapter);

    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView noteTitle,noteContent;
        private LinearLayout noteLayout;
        private ImageView optionsNotes;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.NoteCardTitle);
            noteContent = itemView.findViewById(R.id.notesCardContent);
            noteLayout = itemView.findViewById(R.id.layoutNoteCard);
            optionsNotes = itemView.findViewById(R.id.notesCardOptions);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logoutNotes:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(NotesMainActivity.this, LoginMainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NotesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (NotesAdapter != null){
            NotesAdapter.startListening();
        }
        else{
            NotesAdapter.stopListening();
        }
    }

    private int getRandomColor(){
        List<Integer> RandomColor = new ArrayList<>();
        RandomColor.add(R.color.blue);
        RandomColor.add(R.color.green);
        RandomColor.add(R.color.gray);
        RandomColor.add(R.color.red);
        RandomColor.add(R.color.orange);
        RandomColor.add(R.color.dark_blue);
        RandomColor.add(R.color.pink);
        RandomColor.add(R.color.yellow);

        Random random = new Random();
        int num = random.nextInt(RandomColor.size());
        return RandomColor.get(num);
    }

    private void setData() {
        floatingActionButton = findViewById(R.id.floatingAddNotes);
        RVNotes = findViewById(R.id.NotesRV);
        mtoolBar = findViewById(R.id.mainToolBar);

        setSupportActionBar(mtoolBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
}