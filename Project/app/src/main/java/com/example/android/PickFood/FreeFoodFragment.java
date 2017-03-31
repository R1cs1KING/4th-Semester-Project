package com.example.android.PickFood;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;



public class FreeFoodFragment extends Fragment {

    public FreeFoodFragment() {

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.number_one, R.string.miwok_number_one,
                R.drawable.c));
        words.add(new Word(R.string.number_two, R.string.miwok_number_two,
                R.drawable.ch));
        words.add(new Word(R.string.number_three, R.string.miwok_number_three,
                R.drawable.p));
        words.add(new Word(R.string.number_four, R.string.miwok_number_four,
                R.drawable.l));
        words.add(new Word(R.string.number_five, R.string.miwok_number_five,
                R.drawable.m));
        words.add(new Word(R.string.number_six, R.string.miwok_number_six,
                R.drawable.b));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_freefood);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
        return rootView;

    }
}
