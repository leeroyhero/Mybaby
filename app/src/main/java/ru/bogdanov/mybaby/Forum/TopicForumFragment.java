package ru.bogdanov.mybaby.Forum;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ru.bogdanov.mybaby.Forum.ForumItems.ForumTopic;
import ru.bogdanov.mybaby.HintFragments.SubtitleFragment;
import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicForumFragment extends Fragment implements View.OnClickListener {
LinearLayout contentLayout;
FireBase fireBase;
    ArrayList<ForumTopic> listTopic;
    DateFormat dateFormat;
    Calendar itemCalendar;
    Button buttonToppicAdd;
    AlertDialog alertDialog;
    EditText editTextTopic, editTextText;

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
        new FillTask().execute();
        contentLayout=(LinearLayout) getActivity().findViewById(R.id.content_fragment_topic_forum);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        itemCalendar=Calendar.getInstance();
        buttonToppicAdd=(Button) getActivity().findViewById(R.id.buttonTopicAdd);
        buttonToppicAdd.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.buttonTopicAdd) topicAdd();
        if (id==R.id.buttonNewTopic) newTopic();
    }

    private void newTopic() {
        String topic=editTextTopic.getText().toString();
        String text=editTextText.getText().toString();
        if (topic.isEmpty())
            Toast.makeText(getActivity(),"Введите заголовок",Toast.LENGTH_SHORT).show();
        else if (text.isEmpty())
            Toast.makeText(getActivity(),"Введите текст",Toast.LENGTH_SHORT).show();
        else {
            ForumTopic forumTopic = new ForumTopic(ForumStorage.getNickName(), text, topic, ForumStorage.getIconId());
            fireBase.newForumTopic(forumTopic);
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
        protected Void doInBackground(Void... voids) {
            fireBase=new FireBase();
            fireBase.getForumTopics();
            while (ForumStorage.getListTopic()==null){}
            while (ForumStorage.getListTopic().size()==0){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            contentLayout.removeAllViews();
            listTopic=new ArrayList<>(ForumStorage.getListTopic());
            Log.i("firebase_log",""+listTopic.size());
            if (listTopic!=null)
                for (ForumTopic forumTopic: listTopic) {
                    View view=View.inflate(getActivity(),R.layout.topic_item,null);
                    TextView textviewTopic=(TextView) view.findViewById(R.id.textViewTopic);
                    TextView textviewNickname=(TextView) view.findViewById(R.id.textViewNickNameTopic);
                    TextView textviewDate=(TextView) view.findViewById(R.id.textViewTopicDate);
                    textviewTopic.setText(forumTopic.getmTopic());
                    textviewNickname.setText(forumTopic.getmNickname());
                    itemCalendar.setTimeInMillis(forumTopic.getmDate());
                    String s=dateFormat.format(itemCalendar.getTime());
                    textviewDate.setText(s);
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
        }
    }
}
