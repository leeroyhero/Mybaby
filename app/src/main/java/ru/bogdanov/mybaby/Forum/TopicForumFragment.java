package ru.bogdanov.mybaby.Forum;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import ru.bogdanov.mybaby.Forum.ForumItems.ForumComment;
import ru.bogdanov.mybaby.Forum.ForumItems.ForumTopic;
import ru.bogdanov.mybaby.R;
import ru.bogdanov.mybaby.SharedPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicForumFragment extends Fragment implements View.OnClickListener {
LinearLayout contentLayout;
FireBase fireBase;
    ArrayList<ForumTopic> listTopic;
    DateFormat dateFormat;
    Calendar itemCalendar;
    Button buttonToppicAdd, buttonRefresh;
    AlertDialog alertDialog;
    EditText editTextTopic, editTextText;
    ProgressBar progressBar;
    int STEP=40;

    public TopicForumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_forum, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Форум");
        progressBar=(ProgressBar) getActivity().findViewById(R.id.progressBar);
       new FillTask().execute();
        getName();
        contentLayout=(LinearLayout) getActivity().findViewById(R.id.content_fragment_topic_forum);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        itemCalendar=Calendar.getInstance();
        buttonToppicAdd=(Button) getActivity().findViewById(R.id.buttonTopicAdd);
        buttonRefresh=(Button) getActivity().findViewById(R.id.buttonRefresh);
        buttonToppicAdd.setOnClickListener(this);
        buttonRefresh.setOnClickListener(this);
    }

    private void getName() {
        ForumStorage.setNickName(new SharedPref(getActivity()).getName());
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.buttonTopicAdd) topicAdd();

        if (id==R.id.buttonNewTopic) newTopic();

        if (id==R.id.buttonRefresh) {
            new FillTask().execute();
            Toast.makeText(getActivity(),"Страница обновлена",Toast.LENGTH_SHORT).show();
        }
    }

    private void newTopic() {
        String topic=editTextTopic.getText().toString();
        String text=editTextText.getText().toString();
        topic=topic.trim();
        text=text.trim();
        if (topic.isEmpty())
            Toast.makeText(getActivity(),"Введите заголовок",Toast.LENGTH_SHORT).show();
        else if (text.isEmpty())
            Toast.makeText(getActivity(),"Введите текст",Toast.LENGTH_SHORT).show();
        else {
            ForumTopic forumTopic = new ForumTopic(ForumStorage.getNickName(), text, topic, ForumStorage.getIconId());
            if (ForumStorage.isBanned())
                Toast.makeText(getActivity(), "Вы забанены", Toast.LENGTH_LONG).show();
            else fireBase.newForumTopic(forumTopic);

            new FillTask().execute();
            alertDialog.cancel();
        }
    }

    private void topicAdd() {
        View view=View.inflate(getActivity(),R.layout.new_topic,null);
        editTextTopic=(EditText) view.findViewById(R.id.editTextNewTopicTopic);
        editTextText=(EditText) view.findViewById(R.id.editTextNewTopicText);
        Button buttonNewTopic=(Button) view.findViewById(R.id.buttonNewTopic);
        buttonNewTopic.setOnClickListener(this);
        alertDialog=new AlertDialog.Builder(getActivity())
                .setView(view)
                .show();
    }

    private void boom() {
        this.getFragmentManager().beginTransaction()
                .replace(R.id.content_fragment_layout,new CommentForumFragment())
                .addToBackStack(null)
                .commit();
    }

    class FillTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fireBase=new FireBase();
            fireBase.getForumTopics();
            while (ForumStorage.getListTopic()==null){}
            while (ForumStorage.getListTopic().size()==0){}
            fireBase.getForumComments();
            while (ForumStorage.getListComment()==null){}
            while (ForumStorage.getListComment().size()==0){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ForumStorage.setTopicCursor(0);
            contentLayout.removeAllViews();
            listTopic=new ArrayList<>(ForumStorage.getListTopic());
            Collections.reverse(listTopic);
            Log.i("firebase_log",""+listTopic.size());
            int current=ForumStorage.getTopicCursor();
            int newCursor=current+STEP;
            if (listTopic!=null){
            if (listTopic.size()-1<newCursor) newCursor=listTopic.size()-1;

                for (int i=current;i<=newCursor;i++) {
                    ForumTopic forumTopic=listTopic.get(i);
                    int count=0;
                    for (ForumComment forumComment:ForumStorage.getListComment())
                        if (forumTopic.getmDate()==forumComment.getmTopicId()) count++;

                    View view=View.inflate(getActivity(),R.layout.topic_item,null);
                    TextView textViewCount=(TextView) view.findViewById(R.id.textViewCommentCount);
                    textViewCount.setText(count+"");
                    TextView textviewTopic=(TextView) view.findViewById(R.id.textViewTopic);
                    TextView textviewNickname=(TextView) view.findViewById(R.id.textViewNickNameTopic);
                    textviewTopic.setText(forumTopic.getmTopic());
                    textviewNickname.setText(forumTopic.getmNickname());
                    final long topicId=forumTopic.getmDate();
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ForumStorage.setCurrentTopicId(topicId);
                            boom();
                        }
                    });
                    if (ForumStorage.isAdmin())
                        view.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                TextView textviewTopic=(TextView) view.findViewById(R.id.textViewTopic);
                                TextView textviewNickname=(TextView) view.findViewById(R.id.textViewNickNameTopic);
                                String topicName=textviewTopic.getText().toString();
                                String userName=textviewNickname.getText().toString();
                                fireBase.deletePostAndComments(topicName, userName);
                                Toast.makeText(getActivity(),"Topic deleted",Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });
                    Animation anim= AnimationUtils.loadAnimation(getActivity(),R.anim.alpha);
                    view.startAnimation(anim);
                    contentLayout.addView(view);
                }
                if (getActivity()!=null)
                    if (listTopic.size()-current-STEP>0)
                if (current+STEP!=listTopic.size()){
                    View view=View.inflate(getActivity(),R.layout.button_more_topic,null);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ForumStorage.setTopicCursor(ForumStorage.getTopicCursor()+STEP);
                            new FillTask1().execute();
                        }
                    });
                    contentLayout.addView(view);
                }

            progressBar.setVisibility(View.GONE);
        }}
    }

    class FillTask1 extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            contentLayout.removeAllViews();
            listTopic=new ArrayList<>(ForumStorage.getListTopic());
            Collections.reverse(listTopic);
            Log.i("firebase_log",""+listTopic.size());
            int current=ForumStorage.getTopicCursor();
            int newCursor=current+STEP;
            if (listTopic!=null){
                if (listTopic.size()-1<newCursor) newCursor=listTopic.size()-1;

                for (int i=current;i<=newCursor;i++) {
                    ForumTopic forumTopic=listTopic.get(i);
                    int count=0;
                    for (ForumComment forumComment:ForumStorage.getListComment())
                        if (forumTopic.getmDate()==forumComment.getmTopicId()) count++;

                    View view=View.inflate(getActivity(),R.layout.topic_item,null);
                    TextView textViewCount=(TextView) view.findViewById(R.id.textViewCommentCount);
                    textViewCount.setText(count+"");
                    TextView textviewTopic=(TextView) view.findViewById(R.id.textViewTopic);
                    TextView textviewNickname=(TextView) view.findViewById(R.id.textViewNickNameTopic);
                    textviewTopic.setText(forumTopic.getmTopic());
                    textviewNickname.setText(forumTopic.getmNickname());
                    final long topicId=forumTopic.getmDate();
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ForumStorage.setCurrentTopicId(topicId);
                            boom();
                        }
                    });
                    contentLayout.addView(view);
                }
                if (listTopic.size()-current-STEP>0)
                if (current+STEP!=listTopic.size()){
                    View view=View.inflate(getActivity(),R.layout.button_more_topic,null);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ForumStorage.setTopicCursor(ForumStorage.getTopicCursor()+STEP);
                            new FillTask1().execute();
                        }
                    });
                    contentLayout.addView(view);
                }
                progressBar.setVisibility(View.GONE);

                final ScrollView scrollView=(ScrollView) getActivity().findViewById(R.id.scrollView4);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (scrollView!=null)
                            scrollView.fullScroll(View.FOCUS_UP);
                    }
                });
            }}
    }
}
