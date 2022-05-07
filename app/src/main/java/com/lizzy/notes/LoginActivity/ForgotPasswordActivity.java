package com.lizzy.notes.LoginActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.lizzy.notes.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextView loginInForgot;
    private EditText emailForgot;
    private Button btnGenerate;
    private ImageView imgBackForgot;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        setData();

        firebaseAuth = FirebaseAuth.getInstance();

        loginInForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this,LoginMainActivity.class);
                startActivity(intent);
            }
        });

        imgBackForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this,LoginMainActivity.class);
                startActivity(intent);
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = emailForgot.getText().toString().trim();
                if (mail.isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Enter Your Email Address to Continue..", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, "Email for Password Recovery is Sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPasswordActivity.this,LoginMainActivity.class));
                            }
                            else{
                                Toast.makeText(ForgotPasswordActivity.this, "Failed to Send Recovery Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });




    }

    private void setData() {

        loginInForgot = findViewById(R.id.LoginInForgot);
        emailForgot = findViewById(R.id.inputEmailForgot);
        btnGenerate = findViewById(R.id.btnGenerate);
        imgBackForgot = findViewById(R.id.imgBackforgotPassword);
    }
}