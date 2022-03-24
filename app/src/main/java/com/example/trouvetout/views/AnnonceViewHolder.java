package com.example.trouvetout.views;

import android.os.Debug;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trouvetout.R;

public class AnnonceViewHolder extends RecyclerView.ViewHolder{

    public TextView nameTxt;

    public AnnonceViewHolder(View itemView) {
        super(itemView);

        nameTxt=(TextView) itemView.findViewById(R.id.fragment_main_item_title);
    }
}