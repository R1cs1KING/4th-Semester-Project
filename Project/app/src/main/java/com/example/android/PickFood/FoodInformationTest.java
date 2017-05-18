package com.example.android.PickFood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.PickFood.FoodItem;
import com.example.android.PickFood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FoodInformationTest extends AppCompatActivity {

    private TextView text1, text2, text3;
    private Button Back, Message;
    private ImageView image, image2;
    ProgressDialog pd;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    DatabaseReference mRef = database.getReferenceFromUrl("https://pickfood-5c351.firebaseio.com/");
    private FirebaseAuth.AuthStateListener mAuthListener;


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://pickfood-5c351.appspot.com");
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodinformation);

        text1 = (TextView) findViewById(R.id.foodInfo);
        text2 = (TextView) findViewById(R.id.userName);
        text3 = (TextView) findViewById(R.id.infoAboutUser);
        Message = (Button) findViewById(R.id.buttonMessageToPerson);
        Back = (Button) findViewById(R.id.buttonReturn);
        text1.setText(
                "Name:" + FoodItemLocal.Location + "\n" +
                        "Type:" + FoodItemLocal.Location + "\n" +
                        "Location:" + FoodItemLocal.Location + "\n" +
                        "Description:" + FoodItemLocal.Location + "\n");
        text2.setText("User: " + FoodItemLocal.Owner);
        text3.setText("LOLOLOL");


        image = (ImageView) findViewById(R.id.imgFood);
        image2 = (ImageView) findViewById(R.id.profilePicture);

        Glide
                .with(context)
                .load(FoodItemLocal.url)
                .override(200, 200)
                .into(image);
        Glide
                .with(context)
                .load(FoodItemLocal.url)
                .override(200, 200)
                .into(image2);
        //Picasso.with(context).load(currentWord.getUrlString()).into(imageView);
        //imageView.setImageResource(currentWord.getUrlString());

        image.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_foodinformation);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        Back.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view){

                finish();

            }
        });

        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodInformationTest.this, Chat.class));
                UserDetails.chatWith = FoodItemLocal.Owner;
                finish();
            }
        });



    }


}
