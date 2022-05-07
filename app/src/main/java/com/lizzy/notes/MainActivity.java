package com.lizzy.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cuberto.liquid_swipe.LiquidPager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lizzy.notes.LoginActivity.LoginMainActivity;
import com.lizzy.notes.LoginActivity.LoginViewPager;

public class MainActivity extends AppCompatActivity {

    LiquidPager liquidPager;
    LoginViewPager loginViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        liquidPager = findViewById(R.id.loginPager);
        loginViewPager = new LoginViewPager(getSupportFragmentManager(),1);
        liquidPager.setAdapter(loginViewPager);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            finish();
            startActivity(new Intent(MainActivity.this,NotesMainActivity.class));
        }
    }
}