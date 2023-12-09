package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Switch;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class NewActivity extends AppCompatActivity {

    private ImageView backgroundImage;
    private final List<TextView> textViews = new ArrayList<>();

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch dayNightSwitch = findViewById(R.id.dayNightSwitch);
        LinearLayout newLayout = findViewById(R.id.newLayout);
        backgroundImage = findViewById(R.id.backgroundImage);

        // TextView list
        textViews.add((TextView) findViewById(R.id.TextView1));
        textViews.add((TextView) findViewById(R.id.TextView2));
        textViews.add((TextView) findViewById(R.id.TextView3));
        textViews.add((TextView) findViewById(R.id.TextView4));
        textViews.add((TextView) findViewById(R.id.TextView5));
        textViews.add((TextView) findViewById(R.id.TextView6));
        textViews.add((TextView) findViewById(R.id.TextView7));
        textViews.add((TextView) findViewById(R.id.TextView8));
        textViews.add((TextView) findViewById(R.id.TextView9));
        textViews.add((TextView) findViewById(R.id.TextView10));
        textViews.add((TextView) findViewById(R.id.TextView11));
        // =============

        newLayout.setBackgroundColor(ContextCompat.getColor(NewActivity.this, R.color.transparent));
        updateTextColor(ContextCompat.getColor(NewActivity.this, R.color.black));
        backgroundImage.setImageResource(R.drawable.day);

        dayNightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    newLayout.setBackgroundColor(ContextCompat.getColor(NewActivity.this, R.color.transparent));
                    updateTextColor(ContextCompat.getColor(NewActivity.this, R.color.white));
                    backgroundImage.setImageResource(R.drawable.night);
                } else {
                    newLayout.setBackgroundColor(ContextCompat.getColor(NewActivity.this, R.color.transparent));
                    updateTextColor(ContextCompat.getColor(NewActivity.this, R.color.black));
                    backgroundImage.setImageResource(R.drawable.day);
                }
            }
        });
    }

    private void updateTextColor(int color) {
        for (TextView textView : textViews) {
            textView.setTextColor(color);
        }
    }
}

