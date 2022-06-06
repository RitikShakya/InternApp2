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

public class LoginPage extends AppCompatActivity {

    EditText email,password;




    Button SignIn, Signup;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email = findViewById(R.id.emailsignin);
        password =findViewById(R.id.passwordsignin);



        SignIn = findViewById(R.id.signin);
        Signup = findViewById(R.id.signup);


        firebaseAuth = FirebaseAuth.getInstance();

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(intent);
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = email.getText().toString();
                String userpass = password.getText().toString();

                if(!useremail.isEmpty()){
                    email.setError(null);
                    if(!userpass.isEmpty()){
                        password.setError(null);

                        signinwithemail(useremail,userpass);



                    }
                    else{
                        password.setError("enter pass");
                    }
                }else{
                    email.setError("enter email");

                }


                signinwithemail(useremail,userpass);

            }
        });
    }


    public void signinwithemail(String useremail,String userpass){

        firebaseAuth.signInWithEmailAndPassword(useremail,userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(LoginPage.this, "Succes Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginPage.this,"Failed Wrong username or pass", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}