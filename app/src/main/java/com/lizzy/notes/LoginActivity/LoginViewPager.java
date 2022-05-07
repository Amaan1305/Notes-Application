package com.lizzy.notes.LoginActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginViewPager extends FragmentPagerAdapter {

    public LoginViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new WelcomeFragment();
            case 1 :
                return new Note2Fragment();
            case 2 :
                return new Note1Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
