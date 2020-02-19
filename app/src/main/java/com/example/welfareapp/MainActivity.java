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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
EditText emailId, password,names,reg,phone;
Button btnSignUp;
TextView tvSignIn;
FirebaseFirestore fstore;
FirebaseAuth mFirebaseAuth;
String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth=FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        emailId=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        btnSignUp=findViewById(R.id.regbutton);
        tvSignIn=findViewById(R.id.textView);
        names = findViewById(R.id.names);
        reg = findViewById(R.id.reg);
        phone =findViewById(R.id.phone);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                String psw = password.getText().toString();
                final String fnames = names.getText().toString();
                final String regNo = reg.getText().toString();
                final String phoneNo = phone.getText().toString();



                if(email.isEmpty()){
                    emailId.setError("Please enter an Email address");
                    emailId.requestFocus();
                }
                else if(psw.isEmpty()){
                    password.setError("Please provide a valid password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && psw.isEmpty()){
                    Toast.makeText(MainActivity.this,"The required fields are empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && psw.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,psw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                userID = mFirebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("Users").document(userID);
                                Map<String,Object> user= new HashMap<>();
                                user.put("Full Names", fnames);
                                user.put("Registration Number",regNo);
                                user.put("Phone Number",phoneNo);
                                user.put("Email",email);
                                documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));

                            }
                            else{
                                Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else

                    {
                    Toast.makeText(MainActivity.this,"An Error occurred while creating your account, Please Try Again!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
