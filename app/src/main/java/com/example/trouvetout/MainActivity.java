package com.example.trouvetout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.trouvetout.Helper.FirebaseHelper;
import com.example.trouvetout.models.Annonce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static DatabaseReference MDATABASE;

    // Les 5 fragments de l'applications
    private Fragment fragment_Fav;
    private Fragment fragment_Home;
    private Fragment fragment_Message;
    private Fragment fragment_Shop;
    private Fragment fragment_User;

    // Les 5 boutons de l'application
    private LinearLayout linearLayoutFav;
    private LinearLayout linearLayoutHome;
    private LinearLayout linearLayoutMessage;
    private LinearLayout linearLayoutShop;
    private ImageButton button_User;
    private ImageButton[] navBarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        MDATABASE = FirebaseDatabase.getInstance("https://trouvetout-133fb-default-rtdb.europe-west1.firebasedatabase.app/").getReference();


        fragment_Fav        = new FavFragment();
        fragment_Home       = new HomeFragment();
        fragment_Message    = new MessageFragment();
        fragment_Shop       = new ShopFragment();
        fragment_User       = new UserFragment();

        linearLayoutHome     = findViewById(R.id.homeLayout);
        linearLayoutFav      = findViewById(R.id.favLayout);
        linearLayoutMessage  = findViewById(R.id.messageLayout);
        linearLayoutShop     = findViewById(R.id.shopLayout);
        button_User     = findViewById(R.id.btnUser);



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
                replaceCurrentFragmentBy(fragment_Message);
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
                // Write a message to the database

                /*FirebaseHelper helper = new FirebaseHelper(MDATABASE);
                Annonce annonce = new Annonce("maison", "photo.png", "descpription", "position");
                helper.save(annonce);*/
                changeIconFromNavBar("user");

                replaceCurrentFragmentBy(fragment_User);
            }
        });
    }

    private void replaceCurrentFragmentBy(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();

    }
    private void changeIconFromNavBar(String icon){
        findViewById(R.id.textHome).setVisibility(View.INVISIBLE);
        findViewById(R.id.textFav).setVisibility(View.INVISIBLE);
        findViewById(R.id.textMessage).setVisibility(View.INVISIBLE);
        findViewById(R.id.textShop).setVisibility(View.INVISIBLE);

        findViewById(R.id.homeLayout).setBackground(null);
        findViewById(R.id.favLayout).setBackground(null);
        findViewById(R.id.messageLayout).setBackground(null);
        findViewById(R.id.shopLayout).setBackground(null);
        findViewById(R.id.btnUser).setBackground(null);




        switch (icon){
            case "home":
                removeIcon();
                findViewById(R.id.btnHome).setBackgroundResource(R.drawable.ic_home_2_fill);
                findViewById(R.id.textHome).setVisibility(View.VISIBLE);
                findViewById(R.id.homeLayout).setBackground(ContextCompat.getDrawable(this,R.drawable.icon_background));
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_spawn);
                linearLayoutHome.startAnimation(animation1);

                break;
            case "fav":
                removeIcon();
                findViewById(R.id.btnFav).setBackgroundResource(R.drawable.ic_heart_fill);
                findViewById(R.id.textFav).setVisibility(View.VISIBLE);
                findViewById(R.id.favLayout).setBackground(ContextCompat.getDrawable(this,R.drawable.icon_background));
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_spawn);
                linearLayoutFav.startAnimation(animation2);
                break;
            case "message":
                removeIcon();
                findViewById(R.id.btnMessage).setBackgroundResource(R.drawable.ic_message_fill);
                findViewById(R.id.textMessage).setVisibility(View.VISIBLE);
                findViewById(R.id.messageLayout).setBackground(ContextCompat.getDrawable(this,R.drawable.icon_background));
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_spawn);
                linearLayoutMessage.startAnimation(animation3);
                break;
            case "shop":
                removeIcon();
                findViewById(R.id.btnShop).setBackgroundResource(R.drawable.ic_shopping_cart_fill);
                findViewById(R.id.textShop).setVisibility(View.VISIBLE);
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
}