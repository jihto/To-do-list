package com.example.final_project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.final_project.R;

public class GetStart extends AppCompatActivity {

    private static final String CHANNEL_ID = "chanel";
    private Button btnGetStart ;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);

        btnGetStart = findViewById(R.id.btnGetStart);

        cardView = findViewById(R.id.cardView1);
        int transparentWhite = Color.argb(220, 255, 255, 255);  // 128 is the alpha value (0-255)
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(transparentWhite);

        // Set corner radii
        float[] radii = {30, 30, 30, 30, 0, 0, 0, 0};
        gradientDrawable.setCornerRadii(radii);
        cardView.setBackground(gradientDrawable);

        btnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStart.this, Home.class);
                startActivity(intent);
            }
        });
    }

}