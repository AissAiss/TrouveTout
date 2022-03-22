package com.example.trouvetout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    // Les 5 fragments de l'applications
    private Fragment fragment_Fav;
    private Fragment fragment_Home;
    private Fragment fragment_Message;
    private Fragment fragment_Shop;
    private Fragment fragment_User;

    // Les 5 boutons de l'application
    private ImageButton button_Fav;
    private ImageButton button_Home;
    private ImageButton button_Message;
    private ImageButton button_Shop;
    private ImageButton button_User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_Fav        = new FavFragment();
        fragment_Home       = new HomeFragment();
        fragment_Message    = new MessageFragment();
        fragment_Shop       = new ShopFragment();
        fragment_User       = new UserFragment();

        button_Fav      = findViewById(R.id.btnFav);
        button_Home     = findViewById(R.id.btnHome);
        button_Message  = findViewById(R.id.btnMessage);
        button_Shop     = findViewById(R.id.btnShop);
        button_User     = findViewById(R.id.btnUser);


        button_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceCurrentFragmentBy(fragment_Fav);
            }

        });

        button_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceCurrentFragmentBy(fragment_Home);
            }
        });

        button_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceCurrentFragmentBy(fragment_Message);
            }
        });

        button_Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceCurrentFragmentBy(fragment_Shop);
            }
        });

        button_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceCurrentFragmentBy(fragment_User);
            }
        });
    }

    private void replaceCurrentFragmentBy(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }
}