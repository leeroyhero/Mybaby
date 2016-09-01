package ru.bogdanov.mybaby.Forum;

import java.util.ArrayList;

import ru.bogdanov.mybaby.Forum.ForumItems.ForumComment;
import ru.bogdanov.mybaby.Forum.ForumItems.ForumTopic;

/**
 * Created by Владимир on 24.08.2016.
 */
public class ForumStorage {
   static ArrayList<ForumTopic> listTopic;
   static ArrayList<ForumComment> listComment;
    static long currentTopicId;
    static String nickName="User";
    static int iconId=0;

    public static int getTopicCursor() {
        return topicCursor;
    }

    public static void setTopicCursor(int topicCursor) {
        ForumStorage.topicCursor = topicCursor;
    }

    static int topicCursor=0;

    public static int getIconId() {
        return iconId;
    }

    public static void setIconId(int iconId) {
        ForumStorage.iconId = iconId;
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        ForumStorage.nickName = nickName;
    }

    public static long getCurrentTopicId() {
        return currentTopicId;
    }

    public static void setCurrentTopicId(long currentTopicId) {
        ForumStorage.currentTopicId = currentTopicId;
    }

    public static ArrayList<ForumComment> getListComment() {
        return listComment;
    }

    public static void setListComment(ArrayList<ForumComment> listComment) {
        ForumStorage.listComment = listComment;
    }

    public static ArrayList<ForumTopic> getListTopic() {
        return listTopic;
    }

    public static void setListTopic(ArrayList<ForumTopic> listTopic) {
        ForumStorage.listTopic = listTopic;
    }
}
