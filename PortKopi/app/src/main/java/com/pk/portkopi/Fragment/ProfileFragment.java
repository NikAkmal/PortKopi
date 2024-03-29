package com.pk.portkopi.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pk.portkopi.Adapter.MyFotoAdapter;
import com.pk.portkopi.EditProfileActivity;
import com.pk.portkopi.Model.Post;
import com.pk.portkopi.Model.User;
import com.pk.portkopi.OptionsActivity;
import com.pk.portkopi.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ProfileFragment extends Fragment {
    ImageView image_profile, option;
    TextView posts, followers, following, fullname, bio, username, location;
    Button edit_profile;

    RecyclerView recyclerView;
    MyFotoAdapter myFotoAdapter;
    List<Post> postList;

    FirebaseUser firebaseUser;
    String profileid;
    private DatabaseReference reference;

    ImageButton my_fotos, saved_fotos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");


        String data = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("profileid", "none");
        if (data.equals("none")){
            profileid = firebaseUser.getUid();
        } else {
            profileid = data;
            getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().clear().apply();
        }

        image_profile = view.findViewById(R.id.image_Profile);
        option = view.findViewById(R.id.options);
        posts = view.findViewById(R.id.posts);
        followers = view.findViewById(R.id.follower);
        following = view.findViewById(R.id.following);
        fullname = view.findViewById(R.id.fullname_profile);
        bio = view.findViewById(R.id.bio);
        username = view.findViewById(R.id.username_profile);
        my_fotos = view.findViewById(R.id.my_fotos);
        saved_fotos = view.findViewById(R.id.saved_fotos);
        edit_profile = view.findViewById(R.id.edit_profile);
        location= view.findViewById(R.id.locactionP);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = location.getText().toString();
                String url = "https://waze.com/ul?q=" + Uri.encode(""+link);
                Intent intentWaze = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intentWaze.setPackage("com.waze");

                String uriGoogle = "geo:0,0?q=" + Uri.encode(""+link);
                Intent intentGoogleNav = new Intent(Intent.ACTION_VIEW, Uri.parse(uriGoogle));
                intentGoogleNav.setPackage("com.google.android.apps.maps");


                String title = getContext().getString(R.string.title);
                Intent chooserIntent = Intent.createChooser(intentGoogleNav, title);
                Intent[] arr = new Intent[1];
                arr[0] = intentWaze;
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arr);
                getContext().startActivity(chooserIntent);
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        myFotoAdapter = new MyFotoAdapter(getContext(),postList);
        recyclerView.setAdapter(myFotoAdapter);

        if (profileid.equals(firebaseUser.getUid())){
            edit_profile.setText("Edit Profile");
        } else{
            checkFollow();
            saved_fotos.setVisibility(View.GONE);
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String btn = edit_profile.getText().toString();

                if(btn.equals("Edit Profile")){
                    startActivity(new Intent(getContext(), EditProfileActivity.class));

                }else if (btn.equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).setValue(true);
                    addNotification();
                }else if (btn.equals("following")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).removeValue();

                }
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OptionsActivity.class));
            }
        });

        userInfo();
        getFollower();
        getNrPosts();
        myFotos();
        return view;
    }

    private void addNotification(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(profileid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "started following you");
        hashMap.put("postid", "");
        hashMap.put("ispost", false);

        reference.push().setValue(hashMap);
    }

    private void userInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() == null){
                    return;
                }

                User user =snapshot.getValue(User.class);

                Glide.with(getContext()).load(user.getImageurl());
                Glide.with(getContext()).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());
                fullname.setText(user.getFullname());
                bio.setText(user.getBio());
                location.setText(user.getLocation());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkFollow(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(profileid).exists()){
                    edit_profile.setText("following");
                }else {
                    edit_profile.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFollower(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(profileid).child("followers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followers.setText(""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(profileid).child("following");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                following.setText(""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getNrPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Post post = snapshot1.getValue(Post.class);
                    if (post.getId().equals(profileid)){
                        i++;
                    }
                }

                posts.setText(String.valueOf(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void myFotos(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Post post = snapshot1.getValue(Post.class);
                    if (post.getId().equals(profileid)){
                        postList.add(post);
                    }
                }
                Collections.reverse(postList);
                myFotoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}