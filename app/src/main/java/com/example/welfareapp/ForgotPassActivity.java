package com.example.welfareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {
    EditText emailId;
    FirebaseAuth mfirebaseAuth;
    Button forgotPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        emailId=findViewById(R.id.editText3);
        mfirebaseAuth=FirebaseAuth.getInstance();
        forgotPass=findViewById(R.id.button2);

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfirebaseAuth.sendPasswordResetEmail(emailId.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPassActivity.this, "Your Password Link has been Sent to your Email",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ForgotPassActivity.this, "An Error Occurred Please Try Again!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
