package com.example.trouvetout.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AnnoncesAdapter extends FirebaseRecyclerAdapter<Annonce, AnnonceViewHolder>  {
    ArrayList<Favori> favoris = new ArrayList<>();
    public Context context;


    public AnnoncesAdapter(@NonNull FirebaseRecyclerOptions<Annonce> options, Context context) {
        super(options);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.context = context;


    }

    @Override
    protected void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position, @NonNull Annonce model) {
        Log.d("BindVIEW", model.toString());
        holder.nameTxt.setText(model.getNom());
        holder.dscrptTct.setText(model.getDescpription());

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

        holder.id = this.getRef(position).getKey();
        dlImageFromFireBaseStoarage(holder, model.getPhoto().get(0));



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            MainActivity.MDATABASE.getDatabase().getReference("Favoris").orderByChild("id").equalTo(user.getUid()+";"+holder.id).get().addOnCompleteListener(
                    new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.d("firebase", String.valueOf(task.getResult().getValue()));

                            } else {
                                Favori favori = task.getResult().getValue(Favori.class);
                                if(favori != null){
                                    holder.checkBox.setChecked(true);
                                }else {
                                    holder.checkBox.setChecked(false);

                                }
                            }
                        }
                    }
            );
            for(Favori favori: favoris){
                if(favori.getIdAnnonce().equals(this.getRef(position).getKey())){
                    holder.checkBox.setChecked(true);
                }
            }
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(holder.checkBox.isChecked()){
                    if(user == null){
                        holder.checkBox.setChecked(false);
                        Toast.makeText(view.getContext(),
                                "Vous devez ??tre connect?? pour mettre en favori une annonce ",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        String key = MainActivity.MDATABASE.getDatabase().getReference("Favoris").push().getKey();
                        Favori favori = new Favori(user.getUid(), holder.id);
                        MainActivity.MDATABASE.child("Favoris").child(key).setValue(favori);
                    }
                }else {
                    MainActivity.MDATABASE.child("Favoris")
                            .orderByChild("id")
                            .equalTo(user.getUid()+";"+holder.id)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().removeValue();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e("TAG", "onCancelled", databaseError.toException());
                                }
                            });
                }
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