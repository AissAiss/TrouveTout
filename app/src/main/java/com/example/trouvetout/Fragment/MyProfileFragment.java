package com.example.trouvetout.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.firebase.ui.auth.AuthUI;
import com.example.trouvetout.ChooseCategoryActivity;
import com.example.trouvetout.MainActivity;
import com.example.trouvetout.R;
import com.example.trouvetout.adapter.MesAnnoncesAdapter;
import com.example.trouvetout.models.Annonce;
import com.firebase.ui.auth.AuthUI;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

public class MyProfileFragment extends Fragment {
    MesAnnoncesAdapter adapter;
    RecyclerView rv;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_profile, container, false);
        view.findViewById(R.id.buttonsignOut).setOnClickListener(signoutOutListener(view));
        view.findViewById(R.id.buttonChooseCategory).setOnClickListener(addAnnonceListener(view));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        rv = view.findViewById(R.id.rv_own_annonce);
        setupRecyclerView();
        return view;
    }


    /*
     * Methode qui retourne le listener nécéssaire pour le bouton de déconnexion, il utilise la La classe
     * AuthUI venant de FireBase
     */
    private View.OnClickListener signoutOutListener(View view){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(view.getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Fragment fragment = new HomeFragment();
                                Context context = view.getContext();
                                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        });
            }
        };
    }

    private View.OnClickListener addAnnonceListener(View view){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChooseCategoryActivity.class);
                startActivity(intent);

            }
        };
    }

    private void setupRecyclerView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = MainActivity.MDATABASE.getDatabase().getReference("Annonces")
                .orderByChild("idOwner")
                .equalTo(user.getUid());


        FirebaseRecyclerOptions<Annonce> options = new FirebaseRecyclerOptions.Builder<Annonce>()
                .setQuery(query, Annonce.class)
                .build();
        adapter = new MesAnnoncesAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
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
        super.onStop();if (adapter != null) {
            adapter.stopListening();
        }
    }



}