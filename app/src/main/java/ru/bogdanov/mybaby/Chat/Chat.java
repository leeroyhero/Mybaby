package ru.bogdanov.mybaby.Chat;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ru.bogdanov.mybaby.R;
import ru.bogdanov.mybaby.SharedPref;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chat extends Fragment implements View.OnClickListener {
private String mChatName="Боец";
private int mChatIcon=0;
    String LOG="firebase_log";
    private final String CHILD="chat";
    EditText editTextPostText;
    private DatabaseReference mDatabase;
    ScrollView scrollView;
    LinearLayout ll;
    Context context;
    ArrayList<ItemData> spinnerList;
    ArrayList<String> userPostArrayList;
    Spinner spinner;
    ChildEventListener childEventListener;
    DateFormat dateFormat;
    ImageButton button;


    public Chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onStart() {
        dateFormat = new SimpleDateFormat("HH:mm");
        context=getActivity();
        userPostArrayList=new ArrayList<>();
        ll=(LinearLayout) getActivity().findViewById(R.id.chatContentLayout);
        ll.removeAllViews();
        scrollView=(ScrollView) getActivity().findViewById(R.id.scrollView3);
        mChatName=getChatName();
        button=(ImageButton) getActivity().findViewById(R.id.buttonChat);
        button.setOnClickListener(this);
        editTextPostText=(EditText) getActivity().findViewById(R.id.editTextPostText);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getActivity().setTitle("Чат");
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataChanged(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                dataChanged(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Проблема с подключением к чату",Toast.LENGTH_SHORT).show();
                Log.i(LOG,databaseError.getMessage());
            }
        };

        mDatabase.child(CHILD).addChildEventListener(childEventListener);

    }

    private void dataChanged(DataSnapshot dataSnapshot) {
        UserPost up1=dataSnapshot.getValue(UserPost.class);

        if (up1!=null) {
            Calendar calendar = Calendar.getInstance();
            String s=up1.getTimeMillis();
            long l=Long.valueOf(s);
            calendar.setTimeInMillis(l);
            String d = dateFormat.format(calendar.getTime());
            up1.setTime(d);
            addToChat(up1);
            Log.i(LOG, up1.getName() + ": " + up1.getText());

            userPostArrayList.add(dataSnapshot.getKey());
        }
    }

    private void addToChat(UserPost up1) {
        View view=View.inflate(context,R.layout.chat_item,null);
        TextView textview=(TextView) view.findViewById(R.id.textViewItemChat);
        TextView textviewText=(TextView) view.findViewById(R.id.textViewItemChatText);
        TextView textviewTime=(TextView) view.findViewById(R.id.textViewChatTime);
        textview.setText(up1.getName()+":");
        textviewTime.setText(up1.getTime());

        ImageView imageViewChatIcon=(ImageView) view.findViewById(R.id.imageViewChatIcon);

        imageViewChatIcon.setImageResource(context.getResources().getIdentifier("chat_icon"+up1.getIconId(),"drawable",context.getPackageName()));
        textviewText.setText(up1.getText());
        ll.addView(view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                if (scrollView!=null)
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onPause() {
        mDatabase.child(CHILD).removeEventListener(childEventListener);
        super.onPause();
    }

    private void spinnerArray() {
        spinnerList = new ArrayList<>();
        spinnerList.add(new ItemData("Пустой", R.drawable.chat_icon0));
    }


    public String getChatName(){
        return new SharedPref(getActivity()).getName();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonChat:
                sendMessage();
                break;
        }

    }

    private void sendMessage() {

        String postText=editTextPostText.getText().toString();
        if (postText.equals(""))
            Toast.makeText(getActivity(),"Введите сообщение",Toast.LENGTH_LONG).show();
        else {
            mChatName=mChatName.replaceAll("\n"," ");
            postText=postText.replaceAll("\n"," ");
            postText=postText.trim();

            UserPost userPost = new UserPost(mChatName, postText, mChatIcon);

            mDatabase.child(CHILD).push().setValue(userPost);

            editTextPostText.setText("");
        }
         if (userPostArrayList.size()>30) deleteLastMessage();
    }

    private void deleteLastMessage() {
        mDatabase.child(CHILD).child(userPostArrayList.get(0)).removeValue();
        mDatabase.child(CHILD).child(userPostArrayList.get(1)).removeValue();
    }


}


