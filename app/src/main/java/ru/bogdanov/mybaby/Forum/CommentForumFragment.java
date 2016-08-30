package ru.bogdanov.mybaby.Forum;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.bogdanov.mybaby.Forum.ForumItems.ForumComment;
import ru.bogdanov.mybaby.Forum.ForumItems.ForumTopic;
import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentForumFragment extends Fragment implements View.OnClickListener {
ForumTopic forumTopic;
    DateFormat dateFormat;
    Button buttonAdd;
    FireBase firebase;

    public CommentForumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment_forum, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        fillTopView();
        buttonAdd=(Button) getActivity().findViewById(R.id.buttonCommentAdd);
        buttonAdd.setOnClickListener(this);
        firebase=new FireBase();
        new AsyncComment().execute();

    }

    private void fillTopView() {
        for (ForumTopic ft:ForumStorage.getListTopic())
            if (ft.getmDate()==ForumStorage.getCurrentTopicId()){
                forumTopic=ft;
                break;
            }
        TextView textViewTopText=(TextView) getActivity().findViewById(R.id.textViewTopicText);
        TextView textViewTopTitle=(TextView) getActivity().findViewById(R.id.textViewTopicTitle);
        TextView textViewTopDate=(TextView) getActivity().findViewById(R.id.textViewTopicDate);
        TextView textViewTopName=(TextView) getActivity().findViewById(R.id.textViewTopicName);
        textViewTopText.setText(forumTopic.getmText());
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(forumTopic.getmDate());
        String date=dateFormat.format(calendar.getTime());
        textViewTopDate.setText(date);
        textViewTopTitle.setText(forumTopic.getmTopic());
        textViewTopName.setText(forumTopic.getmNickname());
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.buttonCommentAdd) addComment();
    }

    private void addComment() {
        EditText editText=(EditText) getActivity().findViewById(R.id.editTextCommentAdd);
        String text=editText.getText().toString();
        text=text.replaceAll("\n"," ");
        text=text.trim();
        if (text.isEmpty()) Toast.makeText(getActivity(),"Введите сообщение",Toast.LENGTH_SHORT).show();
        else {
            ForumComment forumComment=new ForumComment(ForumStorage.currentTopicId,ForumStorage.nickName,text, 0);
            firebase.newForumComment(forumComment);
            editText.setText("");
            new AsyncComment().execute();
        }

    }

    class AsyncComment extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            firebase.getForumComments();
            while (ForumStorage.getListComment()==null){}
            while (ForumStorage.getListComment().size()==0){}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LinearLayout contentLL=(LinearLayout) getActivity().findViewById(R.id.comment_content_ll);
            contentLL.removeAllViews();
            for (ForumComment fc:ForumStorage.getListComment()) {
                if (fc.getmTopicId()==ForumStorage.getCurrentTopicId()){
                View view=View.inflate(getActivity(),R.layout.comment_item,null);
                TextView textviewNick=(TextView) view.findViewById(R.id.textViewCommentNickname);
                TextView textviewDate=(TextView) view.findViewById(R.id.textViewCommentDate);
                TextView textviewText=(TextView) view.findViewById(R.id.textViewCommentText);
                textviewNick.setText(fc.getmNickName());
                textviewText.setText(fc.getmText());
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(fc.getmDate());
                String date=dateFormat.format(calendar.getTime());
                textviewDate.setText(date);

                contentLL.addView(view);}
            }
            final ScrollView scrollView=(ScrollView) getActivity().findViewById(R.id.scrollViewCommentForum);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    if (scrollView!=null)
                        scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    }
}
