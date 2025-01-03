package com.eaomarb.watertracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class WaterLevelView extends View {

    private Paint waterPaint;
    private float waterPercentage = 0; // Nivel de agua (0% - 100%)
    private float maxHeight = 0; // Altura máxima del vaso
    private float maxWidth = 0;  // Ancho máximo del vaso

    public WaterLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Pintura para el agua
        waterPaint = new Paint();
        waterPaint.setColor(0xFF57A4FF); // Color del agua
        waterPaint.setStyle(Paint.Style.FILL);
    }

    public void setWaterPercentage(float percentage) {
        this.waterPercentage = percentage;
        invalidate(); // Redibuja la vista
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxHeight = h; // Guardamos la altura máxima del vaso
        maxWidth = w;  // Guardamos el ancho máximo del vaso
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Path del vaso (simplificado, ajusta según necesites)
        Path waterPath = new Path();

        // Escalar las coordenadas del path para que se ajusten al tamaño del vaso
        float scaleX = maxWidth / 150f;  // Ajuste a la anchura del vaso (ajusta el valor 400f según tu base)
        float scaleY = maxHeight / 150f; // Ajuste a la altura del vaso (ajusta el valor 400f según tu base)

        // Define el path original
        waterPath.moveTo(4.91f * scaleX, 19.96f * scaleY);
        waterPath.lineTo((4.91f + 4.31f) * scaleX, (19.96f - 3.51f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f) * scaleX, (19.96f - 3.96f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f) * scaleX, (19.96f - 3.96f + 1.3f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f - 0.9f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f + 16.55f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f - 0.9f - 3.01f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f + 16.55f + 18.25f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f - 0.9f - 3.01f - 5.22f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f + 16.55f + 18.25f + 34.4f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f - 0.9f - 3.01f - 5.22f - 5.01f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f + 16.55f + 18.25f + 34.4f + 38.21f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f - 0.9f - 3.01f - 5.22f - 5.01f - 2.51f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f + 16.55f + 18.25f + 34.4f + 38.21f + 14.64f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f - 0.9f - 3.01f - 5.22f - 5.01f - 2.51f - 38.62f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f + 16.55f + 18.25f + 34.4f + 38.21f + 14.64f + 2.31f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f - 0.9f - 3.01f - 5.22f - 5.01f - 2.51f - 38.62f - 20.16f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f + 16.55f + 18.25f + 34.4f + 38.21f + 14.64f + 2.31f - 3.51f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f - 0.9f - 3.01f - 5.22f - 5.01f - 2.51f - 38.62f - 20.16f - 2.61f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f + 16.55f + 18.25f + 34.4f + 38.21f + 14.64f + 2.31f - 3.51f - 20.06f) * scaleY);
        waterPath.lineTo((4.91f + 4.31f + 33.7f + 20.06f + 22.07f + 7.22f + 3.71f - 0.9f - 3.01f - 5.22f - 5.01f - 2.51f - 38.62f - 20.16f - 2.61f - 7.02f) * scaleX, (19.96f - 3.96f + 1.3f + 1.3f + 2.01f + 2.71f + 16.55f + 18.25f + 34.4f + 38.21f + 14.64f + 2.31f - 3.51f - 20.06f - 48.64f) * scaleY);


        // Ajustar el nivel del agua en función del porcentaje
        float waterHeight = maxHeight * (waterPercentage / 100);

        // Recortamos el nivel del agua dentro del vaso
        canvas.save();
        canvas.clipRect(0, maxHeight - waterHeight, maxWidth, maxHeight);
        canvas.drawPath(waterPath, waterPaint); // Dibujamos el agua
        canvas.restore();
    }
}
