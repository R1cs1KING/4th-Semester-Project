package com.example.android.PickFood;

import android.app.ProgressDialog;
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
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;
import android.Manifest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by R1cs1 on 2017.04.04..
 */

public class AddItem extends AppCompatActivity{
    String mCurrentPhotoPath;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    static final int REQUEST_TAKE_PHOTO = 1;
    ProgressDialog pd;
    private EditText mName, mLocation, mDescription;
    private ImageView mPicture;
    private Button proceed, cancel, uploadPic, fromGallery;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth;
    DatabaseReference mRef = database.getReferenceFromUrl("https://pickfood-5c351.firebaseio.com/");
    private FirebaseAuth.AuthStateListener mAuthListener;


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://pickfood-5c351.appspot.com");
    String id = "";
    String link = "";
    String localOwner = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.add_item);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
        mName = (EditText) findViewById(R.id.edit_name);
        mLocation = (EditText) findViewById(R.id.edit_location);
        mDescription = (EditText) findViewById(R.id.edit_description);

        mPicture = (ImageView) findViewById(R.id.img);

        proceed = (Button) findViewById(R.id.btn_proceed);
        cancel = (Button) findViewById(R.id.btn_cancel);
        uploadPic = (Button) findViewById(R.id.btn_upload);
        fromGallery = (Button) findViewById(R.id.btn_gallery);

        final Spinner spinner = (Spinner) findViewById(R.id.category_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            uploadPic.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
//
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        cancel.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view){

                finish();

            }
        });

        uploadPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                filePath = Uri.fromFile(getOutputMediaFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);

                startActivityForResult(intent, 100);
            }
        });


        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a new intent so that you can choose an image from the gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                //Selects available pictures from the phone
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uploads the offer together with the image and links them together
                if(filePath != null) {
                    pd.show();

                    //Generates a new unique name for the image
                    id = UUID.randomUUID().toString();
                    StorageReference childRef = storageRef.child(id + ".jpg");

                    //Uploading the image to the Firebase
                    UploadTask uploadTask = childRef.putFile(filePath);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    localOwner = user.getEmail();

                    //Checks whether the task was successful or a fail
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();

                            //Finds the image with the unique id from the Firebase
                            storageRef.child(id + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //Adds the fetched link to the local variable
                                    link = String.valueOf(uri);

                                    //Selects the choice
                                    String selectedItem = spinner.getSelectedItem().toString();

                                    //Creates a new object "food" with all the properties taken from editText
                                    FoodItem food = new FoodItem(selectedItem,
                                            mName.getText().toString(),
                                            mLocation.getText().toString(),
                                            mDescription.getText().toString(),
                                            link,
                                            localOwner
                                    );

                                    //Accesses "foods" table and saves the entire object "food" to the table
                                    mRef.child(selectedItem).push().setValue(food);

                                    Toast.makeText(AddItem.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(AddItem.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                                }
                            });

                            //link = String.valueOf(storageRef.child(id + ".jpg").getDownloadUrl().getResult());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(AddItem.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(AddItem.this, "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PickFood");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    private void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                uploadPic.setEnabled(true);
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                mPicture.setImageURI(filePath);
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                mPicture.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
