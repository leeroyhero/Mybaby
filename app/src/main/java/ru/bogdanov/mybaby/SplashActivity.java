package ru.bogdanov.mybaby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Владимир on 09.10.2016.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPref sharedPref=new SharedPref(this);

        Intent intent;
        if (sharedPref.isNameSaved())
        intent = new Intent(this, MainActivity.class);
        else intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

        finish();
    }
}
