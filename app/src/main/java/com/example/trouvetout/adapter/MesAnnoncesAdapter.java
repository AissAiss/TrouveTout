package com.example.trouvetout.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trouvetout.AddAnnonceActivity;
import com.example.trouvetout.Fragment.DetailsAnnonceFragment;
import com.example.trouvetout.MainActivity;
import com.example.trouvetout.R;
import com.example.trouvetout.models.Annonce;
import com.example.trouvetout.models.Favori;
import com.example.trouvetout.views.AnnonceViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MesAnnoncesAdapter extends FirebaseRecyclerAdapter<Annonce, AnnonceViewHolder> {
    ArrayList<Favori> favoris = new ArrayList<>();
    Context context;

    public MesAnnoncesAdapter(@NonNull FirebaseRecyclerOptions<Annonce> options, Context context) {
        super(options);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.context = context;


    }

    @Override
    protected void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position, @NonNull Annonce model) {
        holder.nameTxt.setText(model.getNom());
        holder.dscrptTct.setText(model.getDescpription());
        //holder.pos.setText(model.getPosition());
        holder.id = this.getRef(position).getKey();


        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses =null;
        try {
            addresses = geocoder.getFromLocation(model.getLattitude(),model.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            String cityName = addresses.get(0).getLocality();
            holder.pos.setText(cityName);

        } else {
            holder.pos.setText(model.getLattitude() + "; "+ model.getLongitude());
        }


        dlImageFromFireBaseStoarage(holder, model.getPhoto().get(0));
        holder.checkBox.setVisibility(View.INVISIBLE);

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Modification sur l'annonce")
                        .setMessage("Choisissez la modification ?? faire")
                        .setPositiveButton("Suppression", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.MDATABASE.child("Annonces").child(model.getId()).removeValue();
                                for(String s: model.getPhoto()){
                                    MainActivity.STORAGE.getReference().child(s).delete();
                                }
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                MainActivity.MDATABASE.child("Favoris").orderByChild("idAnnonce").equalTo(model.getId())
                                        .get().addOnCompleteListener(
                                                new OnCompleteListener<DataSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                        for (DataSnapshot appleSnapshot: task.getResult().getChildren()) {
                                                            appleSnapshot.getRef().removeValue();
                                                        }
                                                    }
                                                }
                                );
                            }
                        })
                        .setNegativeButton("Edition", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(v.getContext(), AddAnnonceActivity.class);
                                intent.putExtra("idAnnonce",model.getId());
                                v.getContext().startActivity(intent);
                            }
                        })
                        .setNeutralButton("Retour",null)
                        .show();
                return true;
            }
        });

    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_menu_item_layout,
                viewGroup, false);
        return new AnnonceViewHolder(v);
    }

    private void dlImageFromFireBaseStoarage(AnnonceViewHolder holder, String url){
        StorageReference imageRef = MainActivity.STORAGE.getReference().child(url);
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