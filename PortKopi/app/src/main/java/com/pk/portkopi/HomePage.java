package com.pk.portkopi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.pk.portkopi.Adapter.ViewHolder;
import com.pk.portkopi.Model.Post;

public class HomePage extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Posts");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Post, ViewHolder>firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Post, ViewHolder>(
                    Post.class,
                    R.layout.image,
                    ViewHolder.class,
                    reference
            ){
                @Override
                protected void populateViewHolder(ViewHolder viewHolder, Post post, int i) {
                    viewHolder.setdetails(getApplicationContext(),Post.getDescription().Post.getPostimage());
                }
            };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

        }
}