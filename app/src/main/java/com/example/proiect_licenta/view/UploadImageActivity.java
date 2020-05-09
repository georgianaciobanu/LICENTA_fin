package com.example.proiect_licenta.view;


import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.proiect_licenta.R;
import com.example.proiect_licenta.model.Upload;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;



public class UploadImageActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button uploadBtn;
    Button finishBtn;
    TextView chooseFile;
    ImageView imageView;
    ProgressBar pb;
    Upload ex;
    ProgressDialog progressDialog ;
    Uri FilePathUri;
    Upload imageUploadInfo;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private StorageTask mUploadTask;
    String currentServiceEmail;
    private StorageReference Folder;
    String currentUrl;



    public static final String Database_Path = "All_Image_Uploads_Database";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        Intent i = getIntent();

        currentServiceEmail = (String) i.getSerializableExtra("ServiceEmail");
        ex=new Upload();

        progressDialog = new ProgressDialog(UploadImageActivity.this);

        uploadBtn=(Button)findViewById(R.id.BTN_upload);
        chooseFile=(TextView)findViewById(R.id.tx_chooseFile);
        imageView=(ImageView) findViewById(R.id.img_view_upload);
        finishBtn=(Button)findViewById(R.id.BTN_finish);
       imageUploadInfo = new Upload();


      Folder=FirebaseStorage.getInstance().getReference().child("Imagefolder");
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);


        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {

                    uploadImage();
                }
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), HomeScreenClientActivity.class);
                startActivity(it);

            }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            Picasso.get().load(FilePathUri).into(imageView);

        }
    }

    private void uploadImage(){
        if (FilePathUri!=null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            final StorageReference imagename = Folder.child("image" + FilePathUri.getLastPathSegment());

            imagename.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            currentUrl=uri.toString();
                            DatabaseReference imagestore = FirebaseDatabase.getInstance().getReference().child("Image");

                            String ImageUploadId = databaseReference.push().getKey();

                            imageUploadInfo.setImageUrl(currentUrl);
                            imageUploadInfo.setEmailService(currentServiceEmail);

                            imagestore.child(ImageUploadId).setValue(imageUploadInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });


                }
            });
        }
        else{
            Toast.makeText(UploadImageActivity.this, "Please Select An Image ", Toast.LENGTH_LONG).show();
        }

    }


}

