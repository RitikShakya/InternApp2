package com.company.internapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPage extends AppCompatActivity {


    EditText email,password;
    Button signin,signup;
    EditText username, mobile;
    //boolean imageset=false;
    //Modal modal = new Modal(title, message);

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Validations validations = new Validations();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("SignUp");
        setContentView(R.layout.activity_sign_up_page);

        email = findViewById(R.id.emailsignup);
        password =findViewById(R.id.passwordsignup);
        username = findViewById(R.id.username);
        signin = findViewById(R.id.signinbtn);
        signup = findViewById(R.id.signupbtn);
        mobile = findViewById(R.id.mobile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference();

        //list = new ArrayList<>();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    String username_= username.getText().toString();
                    String email_=email.getText().toString();
                    String phone_=mobile.getText().toString();
                    String password_=password.getText().toString();

                    if(!username_.isEmpty()){

                        username.setError(null);
                        if ( !email_.isEmpty()){
                            email.setError(null);

                            if( !phone_.isEmpty()){
                                mobile.setError(null);
                                if(!password_.isEmpty()){
                                    password.setError(null);


//                                    String username_s= username.getText().toString();
//                                    String email_s=email.getText().toString();
//                                    String phone_s=mobile.getText().toString();
//                                    String password_s=password.getText().toString();



                                    signupwithemail(email_, password_,username_,phone_);









                                }else{
                                    password.setError("enter password");
                                }
                            }else{
                                mobile.setError("enter number");
                            }

                        }else{
                            email.setError("enter email which is valid");
                        }
                    }else{
                        username.setError("enter username");
                    }
                }



            }
        );


    }

    public void signupwithemail(String email,String password,String user,String contact){



        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    databaseReference.child("Users").child(firebaseAuth.getUid()).child("username").setValue(user);
                    databaseReference.child("Users").child(firebaseAuth.getUid()).child("contact").setValue(contact);


                    signup.setClickable(false);
                    Toast.makeText(SignUpPage.this, "succes ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                    intent.putExtra("username",user);
                    startActivity(intent);

                }else{
                    Toast.makeText(SignUpPage.this,"failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}