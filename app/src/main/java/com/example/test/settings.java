package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Currency;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class settings extends AppCompatActivity {
    FloatingActionButton fabMainSettings, homeFabSettings, calFabSettings;

    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fabMainSettings = findViewById(R.id.fabMainSettings);
        homeFabSettings = findViewById(R.id.homeFabSettings);
        calFabSettings = findViewById(R.id.calFabSettings);

        homeFabSettings.setVisibility(View.GONE);
        calFabSettings.setVisibility(View.GONE);

        isAllFabsVisible = false;
        fabMainSettings.show();
        fabMainSettings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            homeFabSettings.show();
                            calFabSettings.show();

                            fabMainSettings.isExpanded();

                            isAllFabsVisible = true;
                        } else {

                            homeFabSettings.hide();
                            calFabSettings.hide();

                            fabMainSettings.show();

                            isAllFabsVisible = false;
                        }
                    }
                });
        // FAB - go back to main page (front)
        homeFabSettings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent homePage = new Intent(settings.this, front.class);
                        startActivity(homePage);
                    }
                }
        );
        // FAB - go back to calculator page (main)
        calFabSettings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent homePage = new Intent(settings.this, MainActivity.class);
                        startActivity(homePage);
                    }
                }
        );
        Switch darkMode = (Switch) findViewById(R.id.darkMode);

        Boolean isNightModeOn = darkMode.isChecked();
        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }

}
