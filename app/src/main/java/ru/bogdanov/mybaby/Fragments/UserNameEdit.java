package ru.bogdanov.mybaby.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.bogdanov.mybaby.DB.DataBase;
import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserNameEdit extends Fragment implements View.OnClickListener{
Button buttonNext;
    EditText editText;

    public UserNameEdit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_name_edit, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        buttonNext = (Button) getActivity().findViewById(R.id.buttonNext);
        editText=(EditText) getActivity().findViewById(R.id.editTextYourName);
        buttonNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.buttonNext){
            buttonNextClicked();
        }
    }

    private void buttonNextClicked() {
        String str=editText.getText().toString();
        if (str.isEmpty()){
            Toast.makeText(getActivity(),R.string.not_enterred_name,Toast.LENGTH_SHORT).show();
        }
        else {
            DataBase.setUserName(str);
        }
    }
}
