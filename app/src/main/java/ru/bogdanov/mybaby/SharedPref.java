package ru.bogdanov.mybaby;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Владимир on 26.08.2016.
 */
public class SharedPref {
    Context context;
    private final String NAME="name";

    public SharedPref(Context context) {
        this.context = context;
    }

    public void saveName(String name){
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getName(){
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
       return sharedPreferences.getString(NAME,"User");
    }
}
