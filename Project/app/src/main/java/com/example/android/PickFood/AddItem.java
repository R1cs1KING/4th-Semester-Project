package com.example.android.PickFood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by R1cs1 on 2017.04.04..
 */

public class AddItem extends AppCompatActivity{

    private EditText mName, mLocation, mDescription;
    private ImageView mPicture;
    private Button proceed, cancel, uploadPic;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReferenceFromUrl("https://pickfood-5c351.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        mName = (EditText) findViewById(R.id.edit_name);
        mLocation = (EditText) findViewById(R.id.edit_location);
        mDescription = (EditText) findViewById(R.id.edit_description);

        mPicture = (ImageView) findViewById(R.id.img);

        proceed = (Button) findViewById(R.id.btn_proceed);
        cancel = (Button) findViewById(R.id.btn_cancel);
        uploadPic = (Button) findViewById(R.id.btn_upload);

        final Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checks the selection boxes choice
                String selectedItem = spinner.getSelectedItem().toString();

                //Creates a new object "food" with all the properties taken from editText
                FoodItem food = new FoodItem(selectedItem,
                        mName.getText().toString(),
                        mLocation.getText().toString(),
                        mDescription.getText().toString());

                //Accesses "foods" table and saves the entire object "food" to the table
                mRef.child("foods").push().setValue(food);

                //And obviously, displays that it was finished :D
                //Though we will need to add so it only would say that IF
                //it really adds it or not.
                toastMessage("Successfully added");
            }
        });

        uploadPic.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view){
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

            }
        });




    }

    private void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        mPicture.setImageBitmap(bitmap);
    }
}
