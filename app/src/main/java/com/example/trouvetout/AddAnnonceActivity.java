package com.example.trouvetout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.trouvetout.models.Annonce;
import com.example.trouvetout.models.AnnonceCar;
import com.example.trouvetout.models.AnnonceHouse;
import com.example.trouvetout.models.UserPro;
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

public class AddAnnonceActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    Activity activity;
    ImageView imageViewSelected;
    ArrayList<String> photos;
    String id;
    String category;
    double longitude;
    double lattitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_annonce);
        id = getIntent().getStringExtra("idAnnonce");

        checkPersmissionPro();

        if (id != null) {
            ((Button) findViewById(R.id.buttonConfirmAnnonce)).setText("Modifier");
            MainActivity.MDATABASE.child("Annonces").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Annonce annonce = task.getResult().getValue(Annonce.class);
                    category = annonce.getCategorie();
                    longitude = annonce.getLongitude();
                    lattitude = annonce.getLattitude();
                    switch (category) {
                        case "Car":
                            replaceCurrentFragmentBy(R.layout.fragment_car_add_annonce);
                            annonce = task.getResult().getValue(AnnonceCar.class);
                            ((EditText) findViewById(R.id.editTextKilometrage)).setText(((AnnonceCar) annonce).getKilometrage() + "");
                            break;
                        case "House":
                            replaceCurrentFragmentBy(R.layout.fragment_house_add_annonce);
                            annonce = task.getResult().getValue(AnnonceHouse.class);
                            ((EditText) findViewById(R.id.editTextLoyer)).setText(((AnnonceHouse) annonce).getPrixLoyer() + "");
                            ((EditText) findViewById(R.id.editTextSurface)).setText(((AnnonceHouse) annonce).getSurface());
                            ((EditText) findViewById(R.id.editTextCaution)).setText((((AnnonceHouse) annonce).getCaution() + ""));
                            break;
                        case "Other":
                            replaceCurrentFragmentBy(R.layout.fragment_other_add_annonce);
                            break;
                    }
                    ((EditText) findViewById(R.id.editTextTitleAnnonce)).setText(annonce.getNom());
                    ((EditText) findViewById(R.id.editTextDescription)).setText(annonce.getDescpription());


                    for (String s : annonce.getPhoto()) {
                        switch (s.split("/")[1]) {
                            case "image1.png":
                                dlImageFromFireBaseStoarage(findViewById(R.id.image1), s);
                                break;
                            case "image2.png":
                                dlImageFromFireBaseStoarage(findViewById(R.id.image2), s);
                                break;
                            case "image3.png":
                                dlImageFromFireBaseStoarage(findViewById(R.id.image3), s);
                                break;
                            case "image4.png":
                                dlImageFromFireBaseStoarage(findViewById(R.id.image4), s);
                                break;
                        }
                    }
                }
            });

        } else {
            category = getIntent().getStringExtra("Category");
            if (category != null) {
                switch (category) {
                    case "Other":
                        replaceCurrentFragmentBy(R.layout.fragment_other_add_annonce);
                        break;
                    case "Car":
                        replaceCurrentFragmentBy(R.layout.fragment_car_add_annonce);
                        break;
                    case "House":
                        replaceCurrentFragmentBy(R.layout.fragment_house_add_annonce);
                        break;
                }
            }

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

                // TODO : Régler cette merde de localisation de mort
                //LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //Location location = null;

                try {
                    //location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } catch (SecurityException e) {
                    Toast.makeText(view.getContext(), "erreur, pas d'autorisation pour le GPS", Toast.LENGTH_LONG).show();
                    activity.finish();
                }

                if(id == null) {
                    key = MainActivity.MDATABASE.getDatabase().getReference("Annonces").push().getKey();
                    longitude = 43.6036036036036; //location.getLongitude();
                    lattitude = 3.8816465074753554; // location.getLatitude();

                }else {
                    key = id;

                }
                EditText nom = (EditText) findViewById(R.id.editTextTitleAnnonce);
                EditText description = (EditText) findViewById(R.id.editTextDescription);
                Annonce annonce;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                switch (category){
                    case "Car":
                        EditText kilometrage = (EditText) findViewById(R.id.editTextKilometrage);
                        annonce = new AnnonceCar(key,
                                nom.getText().toString(),
                                photos, description.getText().toString(),
                                longitude,
                                lattitude,
                                user.getUid(),
                                user.getDisplayName(),
                                category,
                                Integer.parseInt(kilometrage.getText().toString()));
                        break;
                    case "House":
                        EditText prixLoyer = (EditText) findViewById(R.id.editTextLoyer);
                        EditText surface = (EditText) findViewById(R.id.editTextSurface);
                        EditText caution = (EditText) findViewById(R.id.editTextCaution);

                        annonce = new AnnonceHouse(key,
                                nom.getText().toString(),
                                photos, description.getText().toString(),
                                longitude,
                                lattitude,
                                user.getUid(),
                                user.getDisplayName(),
                                category,
                                Double.parseDouble(prixLoyer.getText().toString()),
                                surface.getText().toString(),
                                Double.parseDouble(caution.getText().toString())
                                );
                        break;
                    case "Other":
                        annonce = new Annonce(key, nom.getText().toString(),
                                photos, description.getText().toString(),
                                longitude,
                                lattitude,
                                user.getUid(),
                                user.getDisplayName(),
                                category);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + category);
                }



                if(((ImageView) findViewById(R.id.image1)).getDrawable() != null)
                    storageImage(findViewById(R.id.image1), "image1", key);
                if(((ImageView) findViewById(R.id.image2)).getDrawable() != null)
                    storageImage(findViewById(R.id.image2), "image2", key);
                if(((ImageView) findViewById(R.id.image3)).getDrawable() != null)
                    storageImage(findViewById(R.id.image3), "image3", key);
                if(((ImageView) findViewById(R.id.image4)).getDrawable() != null)
                    storageImage(findViewById(R.id.image4), "image4", key);


                MainActivity.MDATABASE.child("Annonces").child(key).setValue(annonce);
                activity.finish();
            }
        });

    }

    private void checkPersmissionPro() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        MainActivity.MDATABASE.child("users").child(user.getUid()).get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        UserPro userPro = task.getResult().getValue(UserPro.class);
                        if (userPro/*.getNumCard()*/ == null){
                            findViewById(R.id.image3).setVisibility(View.INVISIBLE);
                            findViewById(R.id.image4).setVisibility(View.INVISIBLE);
                        }

                    }
                }
        );

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
    private void replaceCurrentFragmentBy(int fragment){
        FrameLayout frameLayout = findViewById(R.id.fragmentAddAnnonce);
        LayoutInflater inflater = getLayoutInflater();
        frameLayout.addView(inflater.inflate(fragment, null));

    }


}
