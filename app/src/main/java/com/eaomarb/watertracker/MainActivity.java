package com.eaomarb.watertracker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int totalGlasses = 10; // Total de vasos que se pueden consumir
    private int currentGlasses = 0; // Vasos consumidos
    private ImageView waterGlassImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waterGlassImageView = findViewById(R.id.waterGlassImageView);
        Button addGlassButton = findViewById(R.id.addGlassButton);
        Button removeGlassButton = findViewById(R.id.removeGlassButton);

        addGlassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentGlasses < totalGlasses) {
                    currentGlasses++;
                    updateWaterLevel();
                }
            }
        });

        removeGlassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentGlasses > 0) {
                    currentGlasses--;
                    updateWaterLevel();
                }
            }
        });

        // Inicializa el nivel de agua al inicio
        updateWaterLevel();
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
        float waterHeight = (currentGlasses / (float) totalGlasses) * 478.61f; // Altura del agua basada en los vasos consumidos

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
