package com.eaomarb.watertracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressBar circleProgressBar;
    private TextView waterTextView;
    private ImageView glassWaterView;
    private Button addWaterButton, removeWaterButton;

    private int currentWaterCount = 0; // Contador de vasos de agua
    private final int MAX_WATER_COUNT = 10; // Máximo de vasos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleProgressBar = findViewById(R.id.circleProgressBar);
        waterTextView = findViewById(R.id.waterTextView);
        glassWaterView = findViewById(R.id.glassWaterView);
        addWaterButton = findViewById(R.id.addWaterButton);
        removeWaterButton = findViewById(R.id.removeWaterButton);

        // Botón para añadir vasos
        addWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentWaterCount < MAX_WATER_COUNT) {
                    currentWaterCount++;
                    updateProgress();
                }
            }
        });

        // Botón para quitar vasos
        removeWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentWaterCount > 0) {
                    currentWaterCount--;
                    updateProgress();
                }
            }
        });
    }

    private void updateProgress() {
        circleProgressBar.setProgress(currentWaterCount);
        waterTextView.setText(currentWaterCount + "/" + MAX_WATER_COUNT);
    }
}