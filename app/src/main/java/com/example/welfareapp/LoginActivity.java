package com.example.welfareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn;
    Button tvSignUp;
    FirebaseAuth mFirebaseAuth;
    Button btnForgotPass;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

          mFirebaseAuth=FirebaseAuth.getInstance();
          emailId=findViewById(R.id.editText);
          password=findViewById(R.id.editText2);
          btnSignIn=findViewById(R.id.loginbutton);
          tvSignUp=findViewById(R.id.registerButton);
          btnForgotPass=findViewById(R.id.btnForgotPass);

          mAuthStateListener = new FirebaseAuth.AuthStateListener() {
              @Override
              public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                  FirebaseUser mFireBaseUser = mFirebaseAuth.getCurrentUser();

                  if(mFireBaseUser != null){
                      Toast.makeText(LoginActivity.this, "You are logged in",Toast.LENGTH_SHORT).show();
                      Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                      startActivity(i);
                  }
                  else{
                      Toast.makeText(LoginActivity.this, "Please Login",Toast.LENGTH_SHORT).show();
                  }
              }
          };

          btnSignIn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String email = emailId.getText().toString();
                  String psw = password.getText().toString();


                  if(email.isEmpty()){
                      emailId.setError("Please enter an Email address");
                      emailId.requestFocus();
                  }
                  else if(psw.isEmpty()){
                      password.setError("Please provide a valid password");
                      password.requestFocus();
                  }
                  else if(email.isEmpty() && psw.isEmpty()){
                      Toast.makeText(LoginActivity.this,"The required fields are empty!",Toast.LENGTH_SHORT).show();
                  }
                  else if(!(email.isEmpty() && psw.isEmpty())){
                      mFirebaseAuth.signInWithEmailAndPassword(email,psw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              if(!task.isSuccessful()){
                                  Toast.makeText(LoginActivity.this, "Login Error, Please Try Again", Toast.LENGTH_SHORT).show();
                              }
                              else{
                                  Intent intHome = new Intent(LoginActivity.this, HomeActivity.class);
                                  startActivity(intHome);
                              }
                          }
                      });

                  }
                  else {
                      Toast.makeText(LoginActivity.this,"An Error occurred while creating your account, Please Try Again!",Toast.LENGTH_SHORT).show();
                  }
              }

          });
          tvSignUp.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent iSignUp = new Intent(LoginActivity.this, MainActivity.class);
                  startActivity(iSignUp);
              }
          });
          btnForgotPass.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent iForgotPass=new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(iForgotPass);
              }
          });

      }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
