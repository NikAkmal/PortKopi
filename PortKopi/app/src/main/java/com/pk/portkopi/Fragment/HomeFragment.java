//package com.pk.portkopi.Fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.pk.portkopi.Adapter.PostAdapter;
//import com.pk.portkopi.Model.Post;
//import com.pk.portkopi.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HomeFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private PostAdapter postAdapter;
//    private ArrayList<Post> postList;
//
//    //followingList changed into userList
//    private List<String> userlist;
//
//    ProgressBar progress_circular;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(mLayoutManager);
//        postList = new ArrayList<>();
//        postAdapter = new PostAdapter(getContext(), postList);
//        recyclerView.setAdapter(postAdapter);
//
//        readPosts();
//
//        return view;
//    }
//
//    private void readPosts(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Posts");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                postList.clear();
//                postList = new ArrayList<Post>();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Post post = snapshot.getValue(Post.class);
//                    postList.add(post);
//                }
////                postAdapter.notifyDataSetChanged();
//                postAdapter = new PostAdapter(HomeFragment.this, postList);
//                recyclerView.setAdapter(postAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//}
