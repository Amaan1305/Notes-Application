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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lizzy.notes.R;

public class SignUpMainActivity extends AppCompatActivity {

    private ImageView imgBackregister;
    private TextView txtLogin;
    private EditText emailRegsiter,passwordRegsiter;
    private Button btnSignUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_main);

        setData();

        firebaseAuth = FirebaseAuth.getInstance();



        imgBackregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpMainActivity.this,LoginMainActivity.class);
                startActivity(intent);
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpMainActivity.this,LoginMainActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailRegsiter.getText().toString().trim();
                String password = passwordRegsiter.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignUpMainActivity.this, "Some Fields Are Empty", Toast.LENGTH_SHORT).show();
                }
                else if (password.length()<8){
                    Toast.makeText(SignUpMainActivity.this, "Password Should Contain AtLeast 8 Digits", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUpMainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            else{
                                Toast.makeText(SignUpMainActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(SignUpMainActivity.this, "Email Verification Sent", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    Intent intent = new Intent(SignUpMainActivity.this,LoginMainActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Toast.makeText(this, "Failed to Sent Verification", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        imgBackregister = findViewById(R.id.imgBackRegister);
        txtLogin = findViewById(R.id.LoginInHereRegister);
        emailRegsiter = findViewById(R.id.inputEmailRegister);
        passwordRegsiter = findViewById(R.id.inputPasswordRegister);
        btnSignUp = findViewById(R.id.btnSignUpRegister);
    }
}