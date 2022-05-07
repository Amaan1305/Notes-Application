package com.lizzy.notes.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lizzy.notes.R;

import org.w3c.dom.Text;


public class Note1Fragment extends Fragment {

    private ImageView imgUpArrow;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note1, container, false);

        imgUpArrow = view.findViewById(R.id.imgArrowLogin);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        navController = NavHostFragment.findNavController(Note1Fragment.this);

        imgUpArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_note1Fragment_to_loginMainActivity);
            }
        });
    }
}