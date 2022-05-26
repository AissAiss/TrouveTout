package com.example.trouvetout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class ChooseCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        findViewById(R.id.buttonChooseCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddAnnonceActivity.class);
                if(((RadioButton)  findViewById(R.id.rbCar)).isChecked()){
                    intent.putExtra("Category","Car");
                }else if(((RadioButton)  findViewById(R.id.rbHouse)).isChecked()){
                    intent.putExtra("Category","House");
                }else if(((RadioButton)  findViewById(R.id.rbOther)).isChecked()){
                    intent.putExtra("Category","Other");
                }
                startActivity(intent);
                finish();
            }
        });
    }
}