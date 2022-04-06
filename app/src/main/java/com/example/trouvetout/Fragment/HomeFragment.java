package com.example.trouvetout.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trouvetout.Helper.FirebaseHelper;
import com.example.trouvetout.MainActivity;
import com.example.trouvetout.R;
import com.example.trouvetout.adapter.AnnoncesAdapter;
import com.example.trouvetout.models.Annonce;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;

public class HomeFragment extends Fragment {

    private AnnoncesAdapter annoncesAdapter;
    FirebaseHelper helper;
    AnnoncesAdapter adapter;
    RecyclerView rv;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        rv = view.findViewById(R.id.rv_home);
        setupRecyclerView();
        return view;
    }


    /*
     * Configuration du recylcerView en récupérant toutes les annonces provenant de la base de FireBase à l'aide
     * de classe définit pour Firebase
     */
    private void setupRecyclerView() {
        Query query = MainActivity.MDATABASE.getDatabase().getReference("Annonces");
        FirebaseRecyclerOptions<Annonce> options = new FirebaseRecyclerOptions.Builder<Annonce>()
                .setQuery(query, Annonce.class)
                .build();
        adapter = new AnnoncesAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override public void onStop() {
        super.onStop();if (adapter != null) {
            adapter.stopListening();
        }
    }
}
