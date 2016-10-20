package ru.bogdanov.mybaby;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Владимир on 26.08.2016.
 */
public class SharedPref {
    Context context;
    private final String NAME="user_name";
    private final String USER_ID="userID";

    public SharedPref(Context context) {
        this.context = context.getApplicationContext();
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

    public boolean isNameSaved(){
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.contains(NAME);
    }

    public void saveUserID(String userID){
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString(USER_ID, userID);
        editor.commit();
    }

    public String getUserID(){
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_ID,"");
    }

    public boolean isUserIDSaved(){
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.contains(USER_ID);
    }
}
