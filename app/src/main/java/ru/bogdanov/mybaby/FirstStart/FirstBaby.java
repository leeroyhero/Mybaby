package ru.bogdanov.mybaby.FirstStart;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstBaby extends Fragment implements View.OnClickListener {
Button buttonDate;
    Button buttonNext;
    EditText editTextName;
    Calendar calendar;
    SimpleDateFormat dateFormat;

    public FirstBaby() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_baby, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        buttonDate=(Button) getActivity().findViewById(R.id.buttonDateBirth);
        buttonNext=(Button) getActivity().findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this);
        buttonDate.setOnClickListener(this);
        editTextName=(EditText) getActivity().findViewById(R.id.editTextBabyName);
        calendar=Calendar.getInstance();
        dateFormat=new SimpleDateFormat("dd.MM.yy");
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        if (id==buttonDate.getId()){
            datePick();
        }

        if (id==buttonNext.getId())
            next();

    }

    private void next() {
        String str=editTextName.getText().toString();
        if (str.isEmpty())
            Toast.makeText(getActivity(),getString(R.string.name_empty),Toast.LENGTH_SHORT).show();
        else{
            FirstStorage.baby_name=str;
            FirstStorage.baby_birth=calendar.getTimeInMillis();

            Toast.makeText(getActivity(),
                    FirstStorage.user_name+" "+FirstStorage.baby_name+" "+FirstStorage.baby_birth,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void datePick() {
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                buttonDate.setText(dateFormat.format(calendar));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }
}
