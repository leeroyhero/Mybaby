package ru.bogdanov.mybaby.FirstStart;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.bogdanov.mybaby.DB.DataBase;
import ru.bogdanov.mybaby.MainActivity;
import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstCheck extends Fragment implements View.OnClickListener{
    TextView textViewName, textViewBabyName, textViewBabyBirth;
    Button buttonBack,buttonNext;
    SimpleDateFormat dateFormat;
    String userName, babyName;


    public FirstCheck() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_check, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        textViewName=(TextView) getActivity().findViewById(R.id.textViewYourName);
        textViewBabyName=(TextView) getActivity().findViewById(R.id.textViewBabyName);
        textViewBabyBirth=(TextView) getActivity().findViewById(R.id.textViewBabyBirth);
        buttonBack=(Button) getActivity().findViewById(R.id.buttonBackCheck);
        buttonNext=(Button) getActivity().findViewById(R.id.buttonNextCheck);
        buttonBack.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        dateFormat=new SimpleDateFormat("dd.MM.yy");

        userName=FirstStorage.user_name;
        babyName=FirstStorage.baby_name;
        userName.replaceAll("\n","");
        babyName.replaceAll("\n","");

        textViewName.setText(userName);
        textViewBabyName.setText(babyName);
        textViewBabyBirth.setText(dateFormat.format(new Date(FirstStorage.baby_birth)));
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if (id==buttonBack.getId())
            setFragment(new FirstName());
        if (id==buttonNext.getId())
            cancelFirst();
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentLayoutFirst,fragment);
        fragmentTransaction.commit();
    }

    private void cancelFirst() {


        DataBase database=new DataBase(getActivity());
        database.setUserName(FirstStorage.user_name);
        database.addBabyInfo(FirstStorage.baby_name,FirstStorage.baby_birth);
        database.close();

        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
