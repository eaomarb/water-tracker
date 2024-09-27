package com.eaomarb.watertracker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class MainActivity extends AppCompatActivity {
    // Declaración de variables
    private ProgressBar circleProgressBar; // Para la barra de progreso circular
    private ImageView waterGlassImageView; // Para el vaso de agua
    private ImageButton addGlassButton; // Para el botón de añadir vaso
    private ImageButton removeGlassButton; // Para el botón de restar vaso
    private int currentGlasses = 0; // Contador de vasos actuales
    private final int maxGlasses = 10; // Número máximo de vasos
    private VectorDrawableCompat waterDrawable; // Para el nivel de agua

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar las variables
        circleProgressBar = findViewById(R.id.circleProgressBar);
        waterGlassImageView = findViewById(R.id.waterGlassImageView);
        addGlassButton = findViewById(R.id.addGlassButton);
        removeGlassButton = findViewById(R.id.removeGlassButton);

        // Inicializar la barra de progreso
        circleProgressBar.setMax(maxGlasses);
        circleProgressBar.setProgress(currentGlasses);

        // Configurar el listener para los botones
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        addGlassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentGlasses < maxGlasses) {
                    currentGlasses++;
                    circleProgressBar.setProgress(currentGlasses);
                    updateWaterLevel(); // Método que ajusta el nivel de agua
                }
            }
        });

        removeGlassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentGlasses > 0) {
                    currentGlasses--;
                    circleProgressBar.setProgress(currentGlasses);
                    updateWaterLevel(); // Método que ajusta el nivel de agua
                }
            }
        });
    }
    private void updateWaterLevel() {
        // Creamos un Bitmap donde dibujar el vaso y el agua
        Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Dibuja el vaso
        VectorDrawable vectorDrawable = (VectorDrawable) getResources().getDrawable(R.drawable.water_glass);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);

        // Dibuja el nivel de agua
        Paint paint = new Paint();
        paint.setColor(0xFF57A4FF); // Color del agua
        float waterHeight = (currentGlasses / (float) maxGlasses) * 478.61f; // Altura del agua basada en los vasos consumidos

        // Ajustes para que el agua no toque los bordes
        float leftMargin = 34; // Margen izquierdo
        float rightMargin = 34; // Margen derecho
        float topMargin = 34; // Margen superior

        // Dibuja el agua como un rectángulo
        if (currentGlasses > 0) {
            canvas.drawRect(
                    100 + leftMargin,
                    478.61f - waterHeight + topMargin,  // Ajusta la altura de agua con el margen superior
                    411.83f - rightMargin,
                    478.61f  // Mantén el borde inferior del vaso
                    , paint);
        }

        // Dibuja el borde del vaso
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK); // Color del borde
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4); // Grosor del borde
        // Dibuja el borde del vaso
        canvas.drawRect(
                100,
                0,
                411.83f,
                478.61f,
                borderPaint
        );

        // Asigna el Bitmap al ImageView
        waterGlassImageView.setImageBitmap(bitmap);
    }

}
