package ru.bogdanov.mybaby.Chat;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Calendar;
import java.util.Locale;

import ru.bogdanov.mybaby.Forum.ForumStorage;

/**
 * Created by Владимир on 06.07.2016.
 */
@IgnoreExtraProperties
public class UserPost {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String name;
    public String text;
    public String time;
    public String timeMillis;
    public String userID;

    public String getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(String timeMillis) {
        this.timeMillis = timeMillis;
    }

    public int getIconId() {
        return iconId;
    }

    public int iconId;
    public UserPost(){}

    public UserPost(String name, String text, int iconId){
        this.name=name;
        this.text=text;
        this.iconId=iconId;
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        timeMillis=String.valueOf(calendar.getTimeInMillis());
        this.userID= ForumStorage.getUserID();
    }


}

