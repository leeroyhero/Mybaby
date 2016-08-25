package ru.bogdanov.mybaby.Forum;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ru.bogdanov.mybaby.Forum.ForumItems.ForumComment;
import ru.bogdanov.mybaby.Forum.ForumItems.ForumTopic;

/**
 * Created by Владимир on 23.08.2016.
 */
public class FireBase {
    private DatabaseReference mDatabase;
    private String LOG="firebase_log";

    public FireBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.i(LOG,"Firebase started");
    }

    public void newForumTopic(ForumTopic forumTopic){
        mDatabase.child("forum").child(String.valueOf(forumTopic.getmDate()+" "+forumTopic.getmNickname()+" "+forumTopic.getmTopic())).setValue(forumTopic);
        Log.i(LOG,"New topic added: "+forumTopic.getmTopic());
    }

    public void newForumComment(ForumComment forumComment){
        mDatabase.child("comments").child(String.valueOf(forumComment.getmDate()+" "+forumComment.getmNickName())).setValue(forumComment);
        Log.i(LOG,"New comment added: "+forumComment.getmText());
    }

    public void getForumComments(){
        final  ArrayList<ForumComment> listComment=new ArrayList<>();

        mDatabase.child("comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ForumComment forumComment=dataSnapshot.getValue(ForumComment.class);
                Log.i(LOG,forumComment.getmNickName()+ " "+forumComment.getmDate()+" "+forumComment.getmText());
                listComment.add(forumComment);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ForumStorage.setListComment(listComment);

    }

    public void getForumTopics(){
        final ArrayList<ForumTopic> listTopic=new ArrayList<>();
        mDatabase.child("forum").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ForumTopic forumTopic = dataSnapshot.getValue(ForumTopic.class);
                Log.i(LOG,forumTopic.getmNickname()+" "+forumTopic.getmDate()+" "+forumTopic.getmTopic());
                listTopic.add(forumTopic);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ForumStorage.setListTopic(listTopic);

    }
}
