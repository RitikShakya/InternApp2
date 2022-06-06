package com.company.internapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    List<String> userlist;
    Context context;
    String username;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public AdapterRecycler(List<String> details, MainActivity mainActivity, String username) {
        this.userlist = details;
        this.context = mainActivity;
        this.username = username;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new ViewHolder(view);
    }

    //@SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        databaseReference.child("Users").child(firebaseAuth.getUid()).child("notes").child(userlist.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //String title = snapshot.child("username").getValue().toString()
                //;
//                holder.title.setText(userlist.get(position).getTitle());
//                holder.description.setText(userlist.get(position).getMessage());


                String title = snapshot.child("title").getValue().toString();
                String description =snapshot.child("description").getValue().toString();

                holder.title.setText(title);
                holder.description.setText(description);


                String imageurl = snapshot.child("image").getValue().toString();

                //holder.username.setText(othername);

                if(imageurl.equals("null")) {
                    holder.image.setImageResource(R.drawable.addphoto);
                }
                else{
                    Picasso.get().load(imageurl).into(holder.image);
                }

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, Description.class);

                        intent.putExtra("title", title);
                        intent.putExtra("description",description);
                        intent.putExtra("imagepath", imageurl);

                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        CardView cardView;
        TextView title,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imagecard);
            cardView = itemView.findViewById(R.id.card_user);
            title = itemView.findViewById(R.id.titleset);
            description = itemView.findViewById(R.id.descriptionset);


        }
    }
}
