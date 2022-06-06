package com.company.internapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String username;
    RecyclerView recyclerView;
    List<String> details;
    Button btn;
    Intent intent;



    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;


    AdapterRecycler adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();



        intent = getIntent();
        intent.getStringExtra("username");
        recyclerView = findViewById(R.id.recycler);
        btn = findViewById(R.id.addbtn);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        details = new ArrayList<>();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                intent.putExtra("username", username);
                startActivity(intent);




            }
        });

        getList();


//        databaseReference.child("Users").child(firebaseAuth.getUid()).child("username").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                username= snapshot.getValue().toString();
//
//                getList();
//                adapterRecycler = new AdapterRecycler(details, MainActivity.this,username);
//                recyclerView.setAdapter(adapterRecycler);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    public void getList(){
         databaseReference.child("Users").child(firebaseAuth.getUid()).child("notes").addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@androidx.annotation.NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


//                 Modal modal = snapshot.getValue(Modal.class);
//                 details.add(modal);

                 String key = snapshot.getKey();
                 details.add(key);
                 adapterRecycler.notifyDataSetChanged();
             }

             @Override
             public void onChildChanged(@androidx.annotation.NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

             }

             @Override
             public void onChildRemoved(@androidx.annotation.NonNull DataSnapshot snapshot) {

             }

             @Override
             public void onChildMoved(@androidx.annotation.NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

             }

             @Override
             public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

             }


         });

         adapterRecycler = new AdapterRecycler(details,MainActivity.this,username);
         recyclerView.setAdapter(adapterRecycler);

    }

}