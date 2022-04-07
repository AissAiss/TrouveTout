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

//import com.firebase.ui.auth.AuthUI;
import com.example.trouvetout.AddAnnonceActivity;
import com.example.trouvetout.R;
import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MyProfileFragment extends Fragment {

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
        view.findViewById(R.id.buttonAddAnnonce).setOnClickListener(addAnnonceListener(view));


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
                Intent intent = new Intent(view.getContext(), AddAnnonceActivity.class);
                startActivity(intent);

            }
        };
    }



}