package ru.bogdanov.mybaby.Forum.ForumItems;

/**
 * Created by Владимир on 20.10.2016.
 */

public class BanItem {
    public BanItem() {
    }

    public BanItem(String userID) {

        this.userID = userID;
    }

    public String getUserID() {

        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String userID;
}
