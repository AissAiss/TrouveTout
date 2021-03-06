package com.example.trouvetout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trouvetout.Fragment.ConversationsFragment;
import com.example.trouvetout.Fragment.FavFragment;
import com.example.trouvetout.Fragment.HomeFragment;
import com.example.trouvetout.Fragment.MessageFragment;
import com.example.trouvetout.Fragment.MyProfileFragment;
import com.example.trouvetout.Fragment.ShopFragment;
import com.example.trouvetout.Fragment.UserFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static DatabaseReference MDATABASE;
    public static FirebaseStorage STORAGE;
    public static FirebaseAuth MAUTH;

    // Les 5 fragments de l'applications
    private Fragment fragment_Fav;
    private Fragment fragment_Home;
    //private Fragment fragment_Message;
    private Fragment fragment_Conv;
    private Fragment fragment_Shop;
    private Fragment fragment_User;
    private Fragment fragment_Profile;

    // Les 5 boutons de l'application
    private LinearLayout linearLayoutFav;
    private LinearLayout linearLayoutHome;
    private LinearLayout linearLayoutMessage;
    private LinearLayout linearLayoutShop;

    private TextView textView_home;
    private TextView textView_fav;
    private TextView textView_message;
    private TextView textView_shop;


    private ImageButton button_User;
    private ImageButton[] navBarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        MDATABASE = FirebaseDatabase.getInstance("https://trouvetout-133fb-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        STORAGE = FirebaseStorage.getInstance("gs://trouvetout-133fb.appspot.com");
        MAUTH = FirebaseAuth.getInstance();

        fragment_Fav        = new FavFragment();
        fragment_Home       = new HomeFragment();
        fragment_Conv       = new ConversationsFragment();
        fragment_Shop       = new ShopFragment();
        fragment_User       = new UserFragment();
        fragment_Profile    = new MyProfileFragment();

        linearLayoutHome     = findViewById(R.id.homeLayout);
        linearLayoutFav      = findViewById(R.id.favLayout);
        linearLayoutMessage  = findViewById(R.id.messageLayout);
        linearLayoutShop     = findViewById(R.id.shopLayout);
        button_User          = findViewById(R.id.btnUser);

        textView_home = findViewById(R.id.textHome);
        textView_fav = findViewById(R.id.textFav);
        textView_message = findViewById(R.id.textMessage);
        textView_shop = findViewById(R.id.textShop);


        changeIconFromNavBar("home");
        replaceCurrentFragmentBy(fragment_Home);

        linearLayoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIconFromNavBar("home");
                replaceCurrentFragmentBy(fragment_Home);
            }
        });

        linearLayoutFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIconFromNavBar("fav");
                replaceCurrentFragmentBy(fragment_Fav);
            }

        });

        linearLayoutMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIconFromNavBar("message");
                replaceCurrentFragmentBy(fragment_Conv);
            }
        });

        linearLayoutShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIconFromNavBar("shop");
                replaceCurrentFragmentBy(fragment_Shop);
            }
        });

        button_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FirebaseHelper helper = new FirebaseHelper(MDATABASE);
                Annonce annonce = new Annonce("maison", "photo.png", "descpription", "position");
                helper.save(annonce);*/

                changeIconFromNavBar("user");
                FirebaseUser currentUser = MAUTH.getCurrentUser();
                if (currentUser != null){
                    replaceCurrentFragmentBy(fragment_Profile);

                }else{
                    replaceCurrentFragmentBy(fragment_User);

                }

            }
        });


        // TODO: Faire un syst??me de gestion des permissions propre...
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(MainActivity.this, "READ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Toast.makeText(MainActivity.this, "No READ", Toast.LENGTH_SHORT).show();
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission")
                        .setMessage("Dis oui")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();

            }
            else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }



        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(MainActivity.this, "INTERNET", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Toast.makeText(MainActivity.this, "No INTERNET", Toast.LENGTH_SHORT).show();
        }


        ActivityCompat.requestPermissions(this, new
                String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},1);
        ActivityCompat.requestPermissions(this, new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);





        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        }else {



        }




    }

    private void replaceCurrentFragmentBy(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }

    private void changeIconFromNavBar(String icon){
        //textView_home.setVisibility(View.INVISIBLE);
        //textView_fav.setVisibility(View.INVISIBLE);
        //textView_message.setVisibility(View.INVISIBLE);
        //textView_shop.setVisibility(View.INVISIBLE);

        textView_home.setText("");
        textView_fav.setText("");
        textView_message.setText("");
        textView_shop.setText("");

        findViewById(R.id.homeLayout).setBackground(null);
        findViewById(R.id.favLayout).setBackground(null);
        findViewById(R.id.messageLayout).setBackground(null);
        findViewById(R.id.shopLayout).setBackground(null);
        findViewById(R.id.btnUser).setBackground(null);

        switch (icon){
            case "home":
                removeIcon();
                findViewById(R.id.btnHome).setBackgroundResource(R.drawable.ic_home_2_fill);
                //textView_home.setVisibility(View.VISIBLE);
                textView_home.setText(getResources().getString(R.string.title_menu_home));
                findViewById(R.id.homeLayout).setBackground(ContextCompat.getDrawable(this,R.drawable.icon_background));
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_spawn);
                linearLayoutHome.startAnimation(animation1);

                break;
            case "fav":
                removeIcon();
                findViewById(R.id.btnFav).setBackgroundResource(R.drawable.ic_heart_fill);
                //textView_fav.setVisibility(View.VISIBLE);
                textView_fav.setText(getResources().getString(R.string.title_menu_favori));
                findViewById(R.id.favLayout).setBackground(ContextCompat.getDrawable(this,R.drawable.icon_background));
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_spawn);
                linearLayoutFav.startAnimation(animation2);
                break;
            case "message":
                removeIcon();
                findViewById(R.id.btnMessage).setBackgroundResource(R.drawable.ic_message_fill);
                //textView_message.setVisibility(View.VISIBLE);
                textView_message.setText(getResources().getString(R.string.title_menu_message));
                findViewById(R.id.messageLayout).setBackground(ContextCompat.getDrawable(this,R.drawable.icon_background));
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_spawn);
                linearLayoutMessage.startAnimation(animation3);
                break;
            case "shop":
                removeIcon();
                findViewById(R.id.btnShop).setBackgroundResource(R.drawable.ic_shopping_cart_fill);
                //textView_shop.setVisibility(View.VISIBLE);
                textView_shop.setText(getResources().getString(R.string.title_menu_shop));
                findViewById(R.id.shopLayout).setBackground(ContextCompat.getDrawable(this,R.drawable.icon_background));
                Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_spawn);
                linearLayoutShop.startAnimation(animation4);
                break;
            case "user":
                removeIcon();
                ImageButton imageButton = findViewById(R.id.btnUser);
                imageButton.setImageResource(R.drawable.ic_profile_fill);
                findViewById(R.id.btnUser).setBackground(ContextCompat.getDrawable(this,R.drawable.icon_background));
                Animation animation5 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_spawn);
                button_User.startAnimation(animation5);
        }
    }

    private void removeIcon() {
        findViewById(R.id.btnHome).setBackgroundResource(R.drawable.ic_home_2);
        findViewById(R.id.btnFav).setBackgroundResource(R.drawable.ic_heart);
        findViewById(R.id.btnMessage).setBackgroundResource(R.drawable.ic_message);
        findViewById(R.id.btnShop).setBackgroundResource(R.drawable.ic_shopping_cart);
        ImageButton imageButton =  findViewById(R.id.btnUser);
        imageButton.setImageResource(R.drawable.ic_profile);

    }

    @Override
    public void onBackPressed(){

    }
}