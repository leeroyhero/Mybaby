package ru.bogdanov.mybaby.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserNameEdit extends Fragment {


    public UserNameEdit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_name_edit, container, false);
    }

}
