package com.hossam.devloper.meplace.ActivityNavigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hossam.devloper.meplace.Activity.Messages;
import com.hossam.devloper.meplace.Contracts.Normal;
import com.hossam.devloper.meplace.R;
import com.squareup.picasso.Picasso;

/**
 * Created by kareem on 9/28/2016.
 */

/**
 * Copyright (c) $today.year.kareem elsayed aly,no one has the authority to edit,delete,copy any part without my permission
 */

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String UserUID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        initVariables();
        initListeners();
    }

    public void Clicked(String User1UID, String User2UID) {
        Intent i = new Intent(ChatActivity.this, Messages.class);
        i.putExtra(Normal.User1String, User1UID);
        i.putExtra(Normal.User2String, User2UID);
        startActivity(i);
    }

    private void initListeners() {
        FirebaseRecyclerAdapter<V, VH> adapter = null;
        if (!UserUID.isEmpty()) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("messageidentifier").child(UserUID);
            adapter = new FirebaseRecyclerAdapter<V, VH>(V.class, R.layout.message_viewer, VH.class, ref) {
                @Override
                protected void populateViewHolder(final VH viewHolder, final V model, int position) {
                    viewHolder.collector(ChatActivity.this, UserUID, model.uid);
                    FirebaseDatabase.getInstance().getReference().child("doctors").child(model.uid).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null && dataSnapshot.getValue(String.class) != null) {
                                viewHolder.name.setText(dataSnapshot.getValue(String.class));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference main = storage.getReference();
                    StorageReference storageReference = main.child("images").child(model.uid);
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null) {
                                Picasso.with(ChatActivity.this).load(uri.toString()).into(viewHolder.img);
                            }
                        }

                    });
                    FirebaseDatabase.getInstance().getReference().child("messages").child(model.id).limitToLast(1).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                if (data != null && data.getValue(msg.class) != null) {
                                    msg msg = data.getValue(ChatActivity.msg.class);
                                    viewHolder.message.setText(msg.msg);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            };
        }
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }
    }

    private void initVariables() {
        recyclerView = (RecyclerView) findViewById(R.id.ChatGroupHeads);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        UserUID = getIntent().getStringExtra(Normal.UserString);

    }

    public static class V {
        public String id, uid;

        public V(String id, String uid) {
            this.id = id;
            this.uid = uid;
        }

        public V() {
            this.uid = "";
            this.id = "";
        }
    }

    public static class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, message;
        ImageView img;
        String User1UID, User2UID;
        ChatActivity main;

        public VH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.message_name);
            message = (TextView) itemView.findViewById(R.id.message_last_message);
            img = (ImageView) itemView.findViewById(R.id.message_img);
            itemView.setOnClickListener(this);
        }

        public void collector(ChatActivity main, String User1UID, String User2UID) {
            this.User1UID = User1UID;
            this.User2UID = User2UID;
            this.main = main;
        }

        @Override
        public void onClick(View view) {
            main.Clicked(User1UID, User2UID);
        }
    }

    public static class msg {
        public String msg, id;

        public msg(String msg, String id) {
            this.msg = msg;
            this.id = id;
        }

        public msg() {
            this.msg = "";
            this.id = "";
        }
    }
}
