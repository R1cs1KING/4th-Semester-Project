package com.example.android.PickFood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    DatabaseReference mRef = database.getReferenceFromUrl("https://pickfood-5c351.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://pickfood-5c351.appspot.com");
    String id = "";
    String link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        cancel.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view){

                finish();

            }
        });

        uploadPic.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view){
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

                galleryAddPic();
                startActivityForResult(intent, 0);
                //dispatchTakePictureIntent();

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

        uploadPic.setOnClickListener(new View.OnClickListener() {
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
                                            link
                                    );

                                    //Accesses "foods" table and saves the entire object "food" to the table
                                    mRef.child("foods").push().setValue(food);

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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/FOLDER");
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void toastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

   /* @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        mPicture.setImageBitmap(bitmap);

    }
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
