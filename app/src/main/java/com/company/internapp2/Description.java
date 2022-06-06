package com.company.internapp2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Description extends AppCompatActivity {

    Intent intent;

    TextView titlet,descriptionn;
    ImageView imagee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        titlet = findViewById(R.id.titletext);
        descriptionn = findViewById(R.id.descripiontext);
        imagee = findViewById(R.id.imagecardd);

        intent = getIntent();
        String title =intent.getStringExtra("title");

        String description = intent.getStringExtra("description");
        String imagepath = intent.getStringExtra("imagepath");

        titlet.setText(title);

        descriptionn.setText(description);

        Picasso.get().load(imagepath).into(imagee);

        if(imagepath.equals("null")) {
            imagee.setImageResource(R.drawable.addphoto);
        }
        else{
            Picasso.get().load(imagepath).into(imagee);
        }





    }
}