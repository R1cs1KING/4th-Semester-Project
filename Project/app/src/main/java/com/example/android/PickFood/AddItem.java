package com.example.android.PickFood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by R1cs1 on 2017.04.04..
 */

public class AddItem extends AppCompatActivity{

    private EditText mName, mLocation, mDescription;
    private ImageView mPicture;
    private Button proceed, cancel, uploadPic;

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

        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }
}
