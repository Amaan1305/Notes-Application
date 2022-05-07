package com.lizzy.notes.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.lizzy.notes.R;

public class Note2Fragment extends Fragment {

    private TextView txtSkip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note2, container, false);

        txtSkip = view.findViewById(R.id.logintxtSkip);

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LoginMainActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}