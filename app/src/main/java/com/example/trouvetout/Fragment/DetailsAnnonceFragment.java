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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trouvetout.MainActivity;
import com.example.trouvetout.R;
import com.example.trouvetout.models.Annonce;
import com.example.trouvetout.views.AnnonceViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

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
                    TextView desc = view.findViewById(R.id.descpt_details_annonce);
                    CarouselView carouselView = view.findViewById(R.id.carouselView);
                    Log.d("firebase", ""+carouselView);

                    annonce = task.getResult().getValue(Annonce.class);

                    title.setText(annonce.getNom());
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

    private void dlImageFromFireBaseStoarage(String url){

    }
}