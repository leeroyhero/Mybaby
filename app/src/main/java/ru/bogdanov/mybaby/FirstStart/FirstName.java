package ru.bogdanov.mybaby.FirstStart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstName extends Fragment implements View.OnClickListener{
Button button;
    EditText editText;

    public FirstName() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_name, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        button=(Button) getActivity().findViewById(R.id.buttonFirstNext);
        editText=(EditText) getActivity().findViewById(R.id.editTextFirstName);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if (id==button.getId()){
        String str=editText.getText().toString();
            if (str.isEmpty())
                Toast.makeText(getActivity(), getString(R.string.name_empty),Toast.LENGTH_SHORT).show();
            else {
                FirstStorage.user_name=str;
                setFragment(new FirstBaby());
            }
        }
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentLayoutFirst,fragment);
        fragmentTransaction.commit();
    }
}
