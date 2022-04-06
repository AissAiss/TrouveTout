package com.example.trouvetout.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trouvetout.Fragment.DetailsAnnonceFragment;
import com.example.trouvetout.R;

public class AnnonceViewHolder extends RecyclerView.ViewHolder{
    private View.OnClickListener onItemClickListener;


    public TextView nameTxt, dscrptTct, pos;
    public ImageView imageView;
    public String id;

    public AnnonceViewHolder(View itemView) {
        super(itemView);
        nameTxt=(TextView) itemView.findViewById(R.id.title_annonce_card);
        dscrptTct = (TextView) itemView.findViewById(R.id.description_annonce_card);
        pos =  (TextView) itemView.findViewById(R.id.position_annonce_card);
        imageView = (ImageView) itemView.findViewById(R.id.appercu_imageView);
        id = "no";

        itemView.setTag(this);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), id+"coucou", Toast.LENGTH_SHORT).show();




                Fragment fragment =  DetailsAnnonceFragment.newInstance(id);
                Context context = itemView.getContext();
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }


}