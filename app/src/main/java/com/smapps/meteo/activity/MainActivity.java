package com.smapps.meteo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.smapps.meteo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boutonDemarrer = findViewById(R.id.bouton_demarrer);
        boutonDemarrer.setOnClickListener((v) -> {
            Intent detailMeteoIntent = new Intent(MainActivity.this, DetailMeteoActivity.class);
            startActivity(detailMeteoIntent);
        });
    }
}