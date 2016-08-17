package ru.bogdanov.mybaby.HintFragments;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ru.bogdanov.mybaby.DBHintsHelper.DBHintsHelper;
import ru.bogdanov.mybaby.DBHintsHelper.SubtitleText;
import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubtitleFragment extends Fragment {
ArrayList<SubtitleText> subtitleTextArrayList;
    DBHintsHelper dbHintsHelper;
    SQLiteDatabase sqLiteDatabase;
    View view;
    LinearLayout contentLL;

    public SubtitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subtitle, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        dbHintsHelper=new DBHintsHelper(getActivity());
        sqLiteDatabase=dbHintsHelper.getReadableDatabase();
        subtitleTextArrayList=dbHintsHelper.getSubtitle(sqLiteDatabase,HintState.getSUBTITLE());

        fillList();
    }

    private void fillList() {
        contentLL=(LinearLayout) getActivity().findViewById(R.id.content_subtitle_ll);
        for (SubtitleText st:subtitleTextArrayList) {
            view=View.inflate(getActivity(),R.layout.list_item,null);
            TextView textView=(TextView) view.findViewById(R.id.textViewListItem);
            textView.setText(st.getSubtitle());
            final String text=st.getText();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HintState.setTEXT(text);
                    boom();
                }
            });
            contentLL.addView(view);
        }
    }

    private void boom() {
        this.getFragmentManager().beginTransaction()
                .replace(R.id.content_fragment_layout,new TextFragment())
                .addToBackStack(null)
                .commit();
    }
}
