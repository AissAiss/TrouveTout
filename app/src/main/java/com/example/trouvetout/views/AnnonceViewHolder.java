package com.example.trouvetout.views;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trouvetout.R;

public class AnnonceViewHolder extends RecyclerView.ViewHolder{

    public TextView nameTxt, dscrptTct;

    public AnnonceViewHolder(View itemView) {
        super(itemView);


        nameTxt=(TextView) itemView.findViewById(R.id.title_annonce_card);
        dscrptTct = (TextView) itemView.findViewById(R.id.description_annonce_card);
    }
}