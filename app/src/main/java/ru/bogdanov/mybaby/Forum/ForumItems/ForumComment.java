package ru.bogdanov.mybaby.Forum.ForumItems;

import java.util.Calendar;
import java.util.Locale;

import ru.bogdanov.mybaby.Forum.ForumStorage;

/**
 * Created by Владимир on 23.08.2016.
 */
public class ForumComment {
    private String userID;
    private long mTopicId;
    private long mDate;
    private String mNickName;
    private String mText;
    private int mIconId;

    public long getmDate() {
        return mDate;
    }

    public void setmDate(long mDate) {
        this.mDate = mDate;
    }

    public int getmIconId() {
        return mIconId;
    }

    public void setmIconId(int mIconId) {
        this.mIconId = mIconId;
    }

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public long getmTopicId() {
        return mTopicId;
    }

    public void setmTopicId(long mTopicId) {
        this.mTopicId = mTopicId;
    }

    public ForumComment(){
    }

    public ForumComment(long mTopicId, String mNickName, String mText, int mIconId) {
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        this.mDate = calendar.getTimeInMillis();
        this.mIconId = mIconId;
        this.mNickName = mNickName;
        this.mText = mText;
        this.mTopicId = mTopicId;
        this.userID= ForumStorage.getUserID();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
