package ru.bogdanov.mybaby.Forum.ForumItems;

import java.util.Calendar;
import java.util.Locale;

import ru.bogdanov.mybaby.Forum.ForumStorage;

/**
 * Created by Владимир on 23.08.2016.
 */
public class ForumTopic {
    private String mNickname;
    private String mText;
    private String mTopic;
    private long mDate;
    private int mIcon_id;
    private String mSection;
    private String userID;

    public String getmSection() {
        return mSection;
    }

    public void setmSection(String mSection) {
        this.mSection = mSection;
    }

    public long getmDate() {
        return mDate;
    }

    public void setmDate(long mDate) {
        this.mDate = mDate;
    }

    public int getmIcon_id() {
        return mIcon_id;
    }

    public void setmIcon_id(int mIcon_id) {
        this.mIcon_id = mIcon_id;
    }

    public String getmNickname() {
        return mNickname;
    }

    public void setmNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmTopic() {
        return mTopic;
    }

    public void setmTopic(String mTopic) {
        this.mTopic = mTopic;
    }

    public ForumTopic() {

    }

    public ForumTopic(String mNickname, String mText, String mTopic, int mIcon_id ) {

        this.mIcon_id = mIcon_id;
        this.mNickname = mNickname;
        this.mText = mText;
        this.mTopic = mTopic;
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        this.mDate = calendar.getTimeInMillis();
        this.mSection="common";
        this.userID= ForumStorage.getUserID();

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
