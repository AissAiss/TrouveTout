package com.example.trouvetout.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trouvetout.MainActivity;
import com.example.trouvetout.R;
import com.example.trouvetout.models.Annonce;
import com.example.trouvetout.models.AnnonceCar;
import com.example.trouvetout.models.AnnonceHouse;
import com.example.trouvetout.models.ChatMessage;
import com.example.trouvetout.models.Conversation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsAnnonceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsAnnonceFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "id";

    private String id;
    private Annonce annonce;
    private Button btnConctacter;

    public DetailsAnnonceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id id
     * @return A new instance of fragment DetailsAnnonceFragment.
     */
    public static DetailsAnnonceFragment newInstance(String id) {
        DetailsAnnonceFragment fragment = new DetailsAnnonceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_ID);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_details_annonce, container, false);
        initFragmentWithAnnonce(view, id);

        btnConctacter = view.findViewById(R.id.btnContacter);

        btnConctacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idAnnonce    = annonce.getId();
                String idOwner      = annonce.getIdOwner();
                String idClient     = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String nomOwner     = "";
                String nomClient    = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String nomAnnonce   = annonce.getNom();
                String miniature    = annonce.getPhoto().get(0);

                String id           = idAnnonce + idOwner + idClient;

                FirebaseDatabase.getInstance()
                        .getReference("Conversations")
                        .push()
                        .setValue(new Conversation(
                                id,
                                idAnnonce,
                                idOwner,
                                idClient,
                                nomAnnonce,
                                nomOwner,
                                nomClient,
                                miniature)
                        );



            }
        });





        // Inflate the layout for this fragment
        return view;


    }


    private void initFragmentWithAnnonce(View view, String id){
        MainActivity.MDATABASE.child("Annonces").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    TextView title = view.findViewById(R.id.titleDetailsAnnonce);
                    CarouselView carouselView = view.findViewById(R.id.carouselView);
                    //Log.d("firebase", ""+carouselView);

                    annonce = task.getResult().getValue(Annonce.class);
                    title.setText(annonce.getNom());
                    switch (annonce.getCategorie()){
                        case "Car":
                            replaceCurrentFragmentBy(R.layout.details_car_fragment, view);
                            annonce = task.getResult().getValue(AnnonceCar.class);
                            ((TextView)  view.findViewById(R.id.textViewKilommetrage)).setText(((AnnonceCar)annonce).getKilometrage()+"");
                            break;
                        case "House":
                            replaceCurrentFragmentBy(R.layout.details_house_fragment, view);
                            annonce = task.getResult().getValue(AnnonceHouse.class);
                            ((TextView)  view.findViewById(R.id.textViewLoyer)).setText(((AnnonceHouse)annonce).getPrixLoyer()+"");
                            ((TextView)  view.findViewById(R.id.textViewSurface)).setText(((AnnonceHouse)annonce).getSurface());
                            ((TextView)  view.findViewById(R.id.textViewCaution)).setText((((AnnonceHouse)annonce).getCaution()+""));
                            break;
                        case "Other":
                            replaceCurrentFragmentBy(R.layout.details_other_fragment, view);
                            break;
                    }
                    TextView desc = view.findViewById(R.id.descpt_details_annonce);
                    desc.setText(annonce.getDescpription());



                    carouselView.setImageListener(imageListener);
                    carouselView.setPageCount(annonce.getPhoto().size());
                }
            }
        });
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            StorageReference imageRef = MainActivity.STORAGE.getReference().child(annonce.getPhoto().get(position));
            final Bitmap[] img = new Bitmap[1];
            final long ONE_MEGABYTE = 1024 * 1024;
            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    img[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(img[0]);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("error", "error dl Image");
                }
            });



            annonce.getPhoto().get(position);
        }
    };

    private void replaceCurrentFragmentBy(int fragment, View view){
        FrameLayout frameLayout = view.findViewById(R.id.fragmentDetailsAnnonce);
        LayoutInflater inflater = getLayoutInflater();
        frameLayout.addView(inflater.inflate(fragment, null));

    }
}