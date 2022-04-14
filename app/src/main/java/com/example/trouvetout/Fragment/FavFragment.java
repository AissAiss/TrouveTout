package com.example.trouvetout.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trouvetout.R;
import com.example.trouvetout.adapter.AnnoncesAdapter;
import com.example.trouvetout.adapter.FavorisAdapter;
import com.example.trouvetout.models.Annonce;
import com.example.trouvetout.models.Favori;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;


public class FavFragment extends Fragment{
    ArrayList<Annonce> annonces;
    AnnoncesAdapter adapter;
    RecyclerView rv;
    FirebaseUser user;



    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        annonces = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        rv = view.findViewById(R.id.rv_fav);


        setupRecyclerView();


        // Inflate the layout for this fragment
        return view;
    }



    private void setupRecyclerView() {
        ArrayList<Annonce> annonces = new ArrayList<>();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference query = FirebaseDatabase.getInstance().getReference()
                .child("Favoris");
        query.orderByChild("idUser")
                .equalTo(user.getUid());

        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot child : task.getResult().getChildren()) {
                    Favori favori = child.getValue(Favori.class);
                    Log.i("Fav", favori.toString());
                    DatabaseReference query2 = FirebaseDatabase.getInstance().getReference()
                            .child("Annonces")
                            .child(favori.getIdAnnonce());
                            query2.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            Log.i("final", task.getResult().getValue(Annonce.class).toString());
                            annonces.add(task.getResult().getValue(Annonce.class));

                            // un peu degueu mais j'ai juré j'ai la flemme
                            FavorisAdapter adapter = new FavorisAdapter(annonces);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rv.setLayoutManager(linearLayoutManager);
                            rv.setAdapter(adapter);
                        }
                    });





                }

            }
        });

    }

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


