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
import com.lizzy.notes.MainActivity;
import com.lizzy.notes.NotesMainActivity;
import com.lizzy.notes.R;

public class LoginMainActivity extends AppCompatActivity {

    private ImageView backImg;
    private EditText emailEdit,PasswordEdit;
    private Button btnSignIn;
    private TextView forgotPassword,NewRegister;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        setData();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            finish();
            startActivity(new Intent(LoginMainActivity.this,NotesMainActivity.class));
        }

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        NewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this, SignUpMainActivity.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString().trim();
                String password = PasswordEdit.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginMainActivity.this, "Some Fields Are Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                checkEmailVerification();
                            }
                            else{
                                Toast.makeText(LoginMainActivity.this, "Account Does not Exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser.isEmailVerified()){
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginMainActivity.this, NotesMainActivity.class));
        }
        else{
            Toast.makeText(this, "Verify Your Email First", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    private void setData() {

        backImg = findViewById(R.id.imgBackLogin);
        emailEdit = findViewById(R.id.inputEmailLogin);
        PasswordEdit = findViewById(R.id.inputPasswordLogin);
        btnSignIn = findViewById(R.id.btnSignInLogin);
        forgotPassword = findViewById(R.id.txtForgotPasswordLogin);
        NewRegister = findViewById(R.id.RegisterHereLogin);
    }
}