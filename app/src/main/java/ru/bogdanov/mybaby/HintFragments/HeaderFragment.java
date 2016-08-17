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
import ru.bogdanov.mybaby.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderFragment extends Fragment {
DBHintsHelper dbHintsHelper;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<String> headerList;
    LinearLayout contentLayout;
    View view;

    public HeaderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        dbHintsHelper=new DBHintsHelper(getActivity());
        sqLiteDatabase=dbHintsHelper.getReadableDatabase();
        headerList=dbHintsHelper.getHeaders(sqLiteDatabase);

        fillHeaderList();


    }

    private void fillHeaderList() {
        contentLayout=(LinearLayout) getActivity().findViewById(R.id.content_header_ll);
        for (final String s:headerList) {
            view=View.inflate(getActivity(),R.layout.list_item,null);
            final TextView textView=(TextView) view.findViewById(R.id.textViewListItem);
            textView.setText(s);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ss=textView.getText().toString();
                    HintState.setSUBTITLE(ss);
                    boom();
                }
            });
            contentLayout.addView(view);
        }
    }

    private void boom() {
        this.getFragmentManager().beginTransaction()
                .replace(R.id.content_fragment_layout,new SubtitleFragment())
                .addToBackStack(null)
                .commit();
    }
}
