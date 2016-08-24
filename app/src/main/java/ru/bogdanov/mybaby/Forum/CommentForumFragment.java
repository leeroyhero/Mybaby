package ru.bogdanov.mybaby.Forum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentForumFragment extends Fragment {


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

        TextView textView=(TextView) getActivity().findViewById(R.id.textFragmentComment);
        textView.setText(String.valueOf(ForumStorage.getCurrentTopicId()));
    }
}
