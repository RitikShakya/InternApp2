package com.company.internapp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddNote extends AppCompatActivity {

    Intent intent;

    String username;

    EditText title,description;
    ImageView image;
    Button Addbtn;
    Uri imageuri;
    boolean imageset =false;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        intent = getIntent();
        username =intent.getStringExtra("username");

        title = findViewById(R.id.titleset);
        description = findViewById(R.id.descriptionset);
        image = findViewById(R.id.imagecard);
        Addbtn = findViewById(R.id.add);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleget = title.getText().toString();
                String desc = description.getText().toString();
                addNote(titleget,desc);

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imagechoose();

            }
        });
    }

    public void addNote(String title,String description ){

        String key = databaseReference.child("Users").child(firebaseUser.getUid()).child("notes").push().getKey();
        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("description", description);



        databaseReference.child("Users").child(firebaseUser.getUid()).child("notes").child(key).setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            databaseReference.child("Users").child(firebaseUser.getUid()).child("notes").child(key).setValue(map);

                            if(imageset){

                                UUID uuid = UUID.randomUUID();
                                String image_name = "image/*"+uuid+".jpg";

                                databaseReference.child("Users").child(firebaseAuth.getUid()).child("notes").child(key).child("image").setValue(imageuri.toString());
                                Toast.makeText(AddNote.this," Profile added", Toast.LENGTH_SHORT).show();


//
//                                storageReference.child(image_name).putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                        StorageReference mystorageReference = firebaseStorage.getReference(image_name);
//
//                                        mystorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
//
//                                                String filepath = uri.toString();
//                                                databaseReference.child("Users").child(firebaseAuth.getUid()).child("notes").child(key).child("image").setValue(filepath).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void unused) {
//                                                        //databaseReference.child("Users").child(firebaseAuth.getUid()).child("notes").child(key).child("image").setValue(filepath);
//                                                        Toast.makeText(AddNote.this,"Success in uploading file", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }).addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        Toast.makeText(AddNote.this," Failed in uploading file", Toast.LENGTH_SHORT).show();
//
//                                                    }
//                                                });
//
//                                            }
//                                        });
//                                    }
//                                });
                            }
                            else{
                                Toast.makeText(AddNote.this," Profile not added", Toast.LENGTH_SHORT).show();


                                databaseReference.child("Users").child(firebaseAuth.getUid()).child("notes").child(key).child("image").setValue("null");
                            }



                            Toast.makeText(AddNote.this, "succes ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddNote.this, MainActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(AddNote.this,"failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

        //databaseReference.child("Users").child(firebaseUser.getUid()).child("notes").child("title").setValue(title);
        //databaseReference.child("Users").child(firebaseUser.getUid()).child("notes").child("description").setValue(description);




    public void imagechoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&requestCode==1&& data!=null) {


            imageuri =data.getData();
            Picasso.get().load(imageuri).into(image);
            imageset=true;
        }else
        {
            imageset=false;
        }
    }
}