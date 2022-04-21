package com.example.trouvetout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.trouvetout.models.Annonce;
import com.example.trouvetout.views.AnnonceViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AddAnnonceActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    Activity activity;
    ImageView imageViewSelected ;
    ArrayList<String> photos;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_annonce);

        id = getIntent().getStringExtra("idAnnonce");

        if (id != null){

            ( (Button) findViewById(R.id.buttonConfirmAnnonce)).setText("Modifier");

            MainActivity.MDATABASE.child("Annonces").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Annonce annonce = task.getResult().getValue(Annonce.class);

                    ((EditText)  findViewById(R.id.editTextTitleAnnonce)).setText(annonce.getNom());
                    ((EditText)  findViewById(R.id.editTextDescription)).setText(annonce.getDescpription());

                    for(String s : annonce.getPhoto()){

                        switch (s.split("/")[1]){
                            case "image1.png":
                                dlImageFromFireBaseStoarage(findViewById(R.id.image1),s);
                                break;
                            case "image2.png":
                                dlImageFromFireBaseStoarage(findViewById(R.id.image2),s);
                                break;
                            case "image3.png":
                                dlImageFromFireBaseStoarage(findViewById(R.id.image3),s);
                                break;
                            case "image4.png":
                                dlImageFromFireBaseStoarage(findViewById(R.id.image4),s);
                                break;


                        }

                    }


                }
            });
        }




        activity = this;
        photos = new ArrayList<>();





        findViewById(R.id.image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeryActivityResultLauncher.launch(i);
                imageViewSelected = findViewById(R.id.image1);
            }
        });
        findViewById(R.id.image2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeryActivityResultLauncher.launch(i);
                imageViewSelected = findViewById(R.id.image2);

            }
        });
        findViewById(R.id.image3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeryActivityResultLauncher.launch(i);
                imageViewSelected = findViewById(R.id.image3);

            }
        });
        findViewById(R.id.image4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeryActivityResultLauncher.launch(i);
                imageViewSelected = findViewById(R.id.image4);

            }
        });
        Activity activity = this;
        findViewById(R.id.buttonConfirmAnnonce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key;
                if(id == null)
                     key = MainActivity.MDATABASE.getDatabase().getReference("Annonces").push().getKey();
                else
                    key = id;

                EditText nom = (EditText) findViewById(R.id.editTextTitleAnnonce);
                EditText description = (EditText) findViewById(R.id.editTextDescription);



                if(((ImageView) findViewById(R.id.image1)).getDrawable() != null)
                    storageImage(findViewById(R.id.image1), "image1", key);
                if(((ImageView) findViewById(R.id.image2)).getDrawable() != null)
                    storageImage(findViewById(R.id.image2), "image2", key);
                if(((ImageView) findViewById(R.id.image3)).getDrawable() != null)
                    storageImage(findViewById(R.id.image3), "image3", key);
                if(((ImageView) findViewById(R.id.image4)).getDrawable() != null)
                    storageImage(findViewById(R.id.image4), "image4", key);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Annonce annonce = new Annonce(key, nom.getText().toString() , photos, description.getText().toString(), "toto", user.getUid());

                MainActivity.MDATABASE.child("Annonces").child(key).setValue(annonce);
                activity.finish();
            }
        });

    }
    public void storageImage(ImageView imageView, String nom, String key){
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        photos.add(key+"/"+nom+".png");

        UploadTask uploadTask = MainActivity.STORAGE.getReference(key+"/"+nom+".png").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }


    ActivityResultLauncher<Intent> galeryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri selectedImage = data.getData();
                        Glide.with(activity).load(selectedImage).into(imageViewSelected);
                        imageViewSelected.setBackground(null);
                    }
                }
            });

    private void dlImageFromFireBaseStoarage(ImageView imageView, String url){
        StorageReference imageRef = MainActivity.STORAGE.getReference().child(url);
        final Bitmap[] img = new Bitmap[1];
        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                img[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(img[0]);
                imageView.setBackground(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("error", "error dl Image");
            }
        });
    }
}
