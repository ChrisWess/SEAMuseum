package com.seamuseum.auswahlelement.werke;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.seamuseum.auswahlelement.R;
import com.theartofdev.edmodo.cropper.CropImage;

public class WerkActivity extends Activity {

    private ImageButton _selectImage;
    private EditText _werkTitle;
    private EditText _werkDesc;
    private Button _submitBtn;
    private Uri _imageUri = null;

    private StorageReference _storage;
    private DatabaseReference _database;

    private ProgressDialog _progress;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_werk);

        _selectImage = (ImageButton) findViewById(R.id.imageSelect);
        _werkTitle = (EditText) findViewById(R.id.titleField);
        _werkDesc = (EditText) findViewById(R.id.descField);
        _submitBtn = (Button) findViewById(R.id.submitBtn);

        _storage = FirebaseStorage.getInstance().getReference();
        _database = FirebaseDatabase.getInstance().getReference().child("Werke");

        _progress = new ProgressDialog(this);

        _selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        _submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }

        });
    }

    private void startPosting() {
        _progress.setMessage("Werk wird hochgeladen");
        _progress.show();

        String titleValue = _werkTitle.getText().toString().trim();
        String descValue = _werkDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titleValue) && !TextUtils.isEmpty(descValue) && _imageUri != null) {
            StorageReference filepath = _storage.child("Werke_Bilder").child(_imageUri.getLastPathSegment());
            filepath.putFile(_imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newWerk = _database.push();
                    newWerk.child("titel").setValue(_werkTitle);
                    newWerk.child("beschreibung").setValue(_werkDesc);
                    newWerk.child("bildUrl").setValue(downloadUrl.toString());

                    Intent homeIntent = new Intent(getApplicationContext(), WerkeMainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    _progress.dismiss();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            _imageUri = data.getData();
            CropImage.activity(_imageUri)
                    .setAspectRatio(3, 2)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                _selectImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
