package com.example.trouvetout.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trouvetout.R;
import com.example.trouvetout.models.Annonce;
import com.example.trouvetout.views.AnnonceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;


public class AnnoncesAdapter extends FirebaseRecyclerAdapter<Annonce, AnnonceViewHolder> {


    public AnnoncesAdapter(@NonNull FirebaseRecyclerOptions<Annonce> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position, @NonNull Annonce model) {
        holder.nameTxt.setText(model.getNom());
        holder.dscrptTct.setText(model.getDescpription());
        holder.pos.setText(model.getPosition());
        holder.id = this.getRef(position).getKey();
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_menu_item_layout,
                viewGroup, false);
        return new AnnonceViewHolder(v);
    }




}