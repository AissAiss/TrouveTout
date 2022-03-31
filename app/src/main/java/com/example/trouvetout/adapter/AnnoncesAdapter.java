package com.example.trouvetout.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trouvetout.MainActivity;
import com.example.trouvetout.R;
import com.example.trouvetout.models.Annonce;
import com.example.trouvetout.views.AnnonceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

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
        dlImageFromFireBaseStoarage(holder, model.getPhoto());
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_menu_item_layout,
                viewGroup, false);
        return new AnnonceViewHolder(v);
    }

    private void dlImageFromFireBaseStoarage(AnnonceViewHolder holder, String url){
        StorageReference imageRef = MainActivity.STORAGE.getReference().child("default-image.jpeg");
        final Bitmap[] img = new Bitmap[1];
        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                img[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageView.setImageBitmap(img[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("error", "error dl Image");
            }
        });
    }
}