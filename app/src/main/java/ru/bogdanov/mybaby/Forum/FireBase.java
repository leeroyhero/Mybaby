package ru.bogdanov.mybaby.Forum;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ru.bogdanov.mybaby.Forum.ForumItems.BanItem;
import ru.bogdanov.mybaby.Forum.ForumItems.ForumComment;
import ru.bogdanov.mybaby.Forum.ForumItems.ForumTopic;

/**
 * Created by Владимир on 23.08.2016.
 */
public class FireBase {
    private DatabaseReference mDatabase;
    private String LOG = "firebase_log";

    public FireBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.i(LOG, "Firebase started");
    }

    public void addUserToBan(String userId) {
        mDatabase.child("banned").push().setValue(new BanItem(userId));
    }


    public void newForumTopic(ForumTopic forumTopic) {
        mDatabase.child("forum").push().setValue(forumTopic);
        Log.i(LOG, "New topic added: " + forumTopic.getmTopic());
    }

    public void newForumComment(ForumComment forumComment) {
        mDatabase.child("comments").push().setValue(forumComment);
        Log.i(LOG, "New comment added: " + forumComment.getmText());
    }

    public void sendToMe(String text) {
        mDatabase.child("advice").push().setValue(ForumStorage.getNickName() + ": " + text);
        Log.i(LOG, "New advice: " + text);
    }

    public void setBanned() {
        mDatabase.child("banned").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BanItem banItem;
                String userId=ForumStorage.getUserID();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    banItem=snapshot.getValue(BanItem.class);
                    if (userId.equals(banItem.getUserID())) ForumStorage.setBanned();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getForumComments() {
        final ArrayList<ForumComment> listComment = new ArrayList<>();
        final ArrayList<String> listKeysComment = new ArrayList<>();

        mDatabase.child("comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ForumComment forumComment = dataSnapshot.getValue(ForumComment.class);
                Log.i(LOG, forumComment.getmNickName() + " " + forumComment.getmDate() + " " + forumComment.getmText());
                listComment.add(forumComment);
                listKeysComment.add(dataSnapshot.getKey());
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
        ForumStorage.setListKeysComment(listKeysComment);

    }

    public void getForumTopics() {
        final ArrayList<ForumTopic> listTopic = new ArrayList<>();
        final ArrayList<String> listKeysTopic = new ArrayList<>();
        mDatabase.child("forum").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ForumTopic forumTopic = dataSnapshot.getValue(ForumTopic.class);
                Log.i(LOG, forumTopic.getmNickname() + " " + forumTopic.getmDate() + " " + forumTopic.getmTopic());
                listTopic.add(forumTopic);
                listKeysTopic.add(dataSnapshot.getKey());
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
        ForumStorage.setListKeysTopic(listKeysTopic);

    }

    public void deletePostAndComments(String topicName, String userName) {
        for (ForumTopic ft : ForumStorage.getListTopic()) {
            if (ft.getmNickname().equals(userName))
                if (ft.getmTopic().equals(topicName)) {
                    int pos = ForumStorage.getListTopic().indexOf(ft);
                    String deleteTopicKeys = ForumStorage.getListKeysTopic().get(pos);
                    deletePost(deleteTopicKeys);
                    deleteComments(ft.getmDate());
                    Log.i(LOG, "Topic deleted = " + topicName);
                }
        }
    }

    private void deleteComments(long l) {
        for (ForumComment fc : ForumStorage.getListComment()) {
            if (fc.getmTopicId() == l) {
                int pos = ForumStorage.getListComment().indexOf(fc);
                String key = ForumStorage.getListKeysComment().get(pos);
                mDatabase.child("comments").child(key).removeValue();
            }
        }
    }

    public void deleteComment(String text) {
        for (ForumComment fc : ForumStorage.getListComment()) {
            if (fc.getmText() == text) {
                int pos = ForumStorage.getListComment().indexOf(fc);
                String key = ForumStorage.getListKeysComment().get(pos);
                mDatabase.child("comments").child(key).removeValue();
            }
        }
    }

    private void deletePost(String deleteTopicKeys) {
        mDatabase.child("forum").child(deleteTopicKeys).removeValue();
    }
}
