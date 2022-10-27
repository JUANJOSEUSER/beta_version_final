package com.example.beta_version_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

public class act_juego extends AppCompatActivity {
Button bon[][];
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_juego);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bon[0][0]=findViewById(R.id.button);
        bon[0][1]=findViewById(R.id.button);
        bon[0][2]=findViewById(R.id.button);
        bon[1][0]=findViewById(R.id.button);
        bon[1][1]=findViewById(R.id.button);
        bon[1][2]=findViewById(R.id.button);
        bon[2][0]=findViewById(R.id.button);
        bon[2][1]=findViewById(R.id.button);
        bon[2][2]=findViewById(R.id.button);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}