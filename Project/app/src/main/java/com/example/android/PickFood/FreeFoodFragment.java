package com.example.android.PickFood;


import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FreeFoodFragment extends Fragment {

    public FreeFoodFragment() {

}

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReferenceFromUrl("https://pickfood-5c351.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://pickfood-5c351.appspot.com");
    DatabaseReference fb = mRef.child("foods");
    String test1 = "";
    String test2 = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        final View rootView = inflater.inflate(R.layout.word_list, container, false);

        final ArrayList<FoodItem> foods = new ArrayList<>();
        final ArrayList<Word> words = new ArrayList<>();
        final String TAG = "Test";

        fb.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_freefood);

                ListView listView = (ListView) rootView.findViewById(R.id.list);

                listView.setAdapter(adapter);
                foods.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    FoodItem food = postSnapshot.getValue(FoodItem.class);
                    foods.add(food);

                    words.add(new Word(food.Name, food.Description, R.drawable.ch));
                    test1 = food.Name;
                    test2 = food.Description;

                }
            }

            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        /*fb.addChildEventListener(new ChildEventListener() {
                                       @Override
                                       public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                           FoodItem food = dataSnapshot.getValue(FoodItem.class);
                                           foods.add(food);
                                           words.add(new Word(food.Name, food.Description, R.drawable.ch));
                                           Log.i(TAG,"add food name = " + food.Name);

                                       }
                                       public void onChildRemoved(DataSnapshot dataSnapshot)
                                       {

                                       }
                                       public void onCancelled(DatabaseError firebaseError) {
                                       System.out.println("The read failed: " + firebaseError.getMessage());
                                       }
                                       public void onChildChanged(DataSnapshot dataSnapshot, String test)
                                       {

                                       }
                                       public void onChildMoved(DataSnapshot dataSnapshot, String test)
                                       {

                                       }
                                   });
                                   */
       /* words.add(new Word(R.string.test, R.string.miwok_number_one,
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

                */



        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
        */
        return rootView;

    }


}
