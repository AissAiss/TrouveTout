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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
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

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Tous");
        spinnerArray.add("Voitures");
        spinnerArray.add("Maisons/appartement");
        spinnerArray.add("Autres");

        setupRecyclerView();



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                view.getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) view.findViewById(R.id.spinner);
        sItems.setAdapter(adapter);

        HomeFragment homeFragment = this;

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view1, int i, long l) {

                Log.d("TESTOTOTOT", rv + " ");
                String s = (String) adapterView.getItemAtPosition(i);
                Query query;
                FirebaseRecyclerOptions<Annonce> options;
                AnnoncesAdapter adapter1;
                LinearLayoutManager linearLayoutManager;
                switch (s){
                    case "Voitures":
                        Log.d("ALLES", "Voitures");
                        query = MainActivity.MDATABASE.getDatabase().getReference("Annonces").orderByChild("categorie").equalTo("Car");
                        options = new FirebaseRecyclerOptions.Builder<Annonce>()
                                .setQuery(query, Annonce.class)
                                .build();
                        homeFragment.adapter = new AnnoncesAdapter(options, getContext());
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv.setLayoutManager(linearLayoutManager);
                        rv.setAdapter(homeFragment.adapter);
                        homeFragment.adapter.startListening();


                        break;
                    case "Maisons/appartement":
                        query = MainActivity.MDATABASE.getDatabase().getReference("Annonces").orderByChild("categorie").equalTo("House");
                        options = new FirebaseRecyclerOptions.Builder<Annonce>()
                                .setQuery(query, Annonce.class)
                                .build();
                        homeFragment.adapter = new AnnoncesAdapter(options,getContext());
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv.setLayoutManager(linearLayoutManager);
                        rv.setAdapter(homeFragment.adapter);
                        homeFragment.adapter.startListening();
                        break;
                    case "Autres":
                        query = MainActivity.MDATABASE.getDatabase().getReference("Annonces").orderByChild("categorie").equalTo("Other");
                        options = new FirebaseRecyclerOptions.Builder<Annonce>()
                                .setQuery(query, Annonce.class)
                                .build();
                        homeFragment.adapter = new AnnoncesAdapter(options,getContext());
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv.setLayoutManager(linearLayoutManager);
                        rv.setAdapter(homeFragment.adapter);
                        homeFragment.adapter.startListening();
                        break;
                    default:
                        query = MainActivity.MDATABASE.getDatabase().getReference("Annonces");
                        options = new FirebaseRecyclerOptions.Builder<Annonce>()
                                .setQuery(query, Annonce.class)
                                .build();
                        homeFragment.adapter = new AnnoncesAdapter(options,getContext());
                        //linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        //rv.setLayoutManager(linearLayoutManager);
                        rv.setAdapter(homeFragment.adapter);
                        homeFragment.adapter.startListening();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        adapter = new AnnoncesAdapter(options,getContext());
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
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
