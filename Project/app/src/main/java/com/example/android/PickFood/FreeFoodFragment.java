package com.example.android.PickFood;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
    DatabaseReference fb = mRef.child("Food");
    final Context context = getActivity();
    MainActivity activity = (MainActivity) getActivity();
    String test1 = "";
    String test2 = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        final View rootView = inflater.inflate(R.layout.word_list, container, false);

        final ArrayList<FoodItem> foods = new ArrayList<>();
        final ArrayList<Word> words = new ArrayList<>();
        final String TAG = "Test";
        final WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_freefood);
        final ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        fb.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {



                foods.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    FoodItem food = postSnapshot.getValue(FoodItem.class);
                    foods.add(food);






                    words.add(new Word(food.Name, food.Description,
                            food.url, food.Location, food.Owner, food.Type));
                    test1 = food.Name;
                    test2 = food.Description;

                }
            }

            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = (Word)listView.getAdapter().getItem(position);
                System.out.println("TEST: " + word.NameString);
                FoodItemLocal.Name = word.getDefaultTranslationId();
                FoodItemLocal.Type = word.getTypeString();
                FoodItemLocal.Description = word.DescriptionString;
                FoodItemLocal.Owner = word.getOwnerString();
                FoodItemLocal.Location = word.getLocationString();
                FoodItemLocal.url = word.getUrlString();
                startActivity(new Intent(getActivity(), FoodInformation.class));
            }
        });

        return rootView;

    }


}
