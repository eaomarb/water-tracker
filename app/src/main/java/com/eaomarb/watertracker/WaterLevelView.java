package com.eaomarb.watertracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

public class WaterLevelView extends View {

    private Path glassPath; // Path del vaso
    private Paint paint; // Pintura para dibujar
    private float animatedWaterHeight = 0f;  // Variable para la altura animada del agua

    public WaterLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Inicializar el Path del vaso
        glassPath = new Path();
        glassPath.moveTo(4.91f, 19.96f);
        glassPath.lineTo(9.22f, 16.45f);
        glassPath.lineTo(42.92f, 12.64f);
        glassPath.lineTo(63.98f, 13.94f);
        glassPath.lineTo(86.05f, 15.24f);
        glassPath.lineTo(93.27f, 17.25f);
        glassPath.lineTo(96.98f, 19.96f);
        glassPath.lineTo(96.08f, 36.51f);
        glassPath.lineTo(93.07f, 54.76f);
        glassPath.lineTo(87.85f, 89.16f);
        glassPath.lineTo(82.84f, 127.37f);
        glassPath.lineTo(80.33f, 142.01f);
        glassPath.lineTo(41.71f, 144.32f);
        glassPath.lineTo(21.55f, 140.81f);
        glassPath.lineTo(18.94f, 120.75f);
        glassPath.lineTo(11.92f, 72.11f);
        glassPath.close();

        // Inicializar el Paint
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setWaterLevelPercentage(float percentage) {
        // Limitar el porcentaje entre 0 y 100
        if (percentage < 0) percentage = 0;
        if (percentage > 100) percentage = 100;

        // Si el porcentaje cambia, animar la altura del agua
        ValueAnimator animator = ValueAnimator.ofFloat(animatedWaterHeight, percentage);
        animator.setDuration(600); // Duración de la animación en milisegundos
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            animatedWaterHeight = (float) animation.getAnimatedValue();
            invalidate(); // Redibujar la vista en cada actualización
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Limpiar el canvas
        canvas.drawColor(Color.TRANSPARENT);

        // Dimensiones del canvas
        float canvasWidth = getWidth();
        float canvasHeight = getHeight();

        // Calcular el tamaño original del vaso
        RectF bounds = new RectF();
        glassPath.computeBounds(bounds, true);
        float glassWidth = bounds.width();
        float glassHeight = bounds.height();

        // Escalar el vaso para ajustarlo al canvas, dejando un margen
        float scaleFactor = 3.5f; // Ajuste perfecto
        canvas.scale(scaleFactor, scaleFactor);

        // Centramos el vaso en el Canvas
        float offsetX = (canvasWidth / scaleFactor - glassWidth) / 2 - bounds.left;
        float offsetY = (canvasHeight / scaleFactor - glassHeight) / 2 - bounds.top;
        canvas.translate(offsetX, offsetY);

        // Calcular la altura del agua según el porcentaje animado
        float waterHeight = glassHeight * (animatedWaterHeight / 100f); // Usar el valor animado
        RectF waterRect = new RectF(bounds.left, bounds.bottom - waterHeight, bounds.right, bounds.bottom);

        // Dibujar el agua dentro del vaso (en el fondo)
        Path waterPath = new Path();
        waterPath.addRect(waterRect, Path.Direction.CW);

        // Dibujar el fondo del vaso
        Path backgroundPath = new Path();
        backgroundPath.moveTo(4.61f, 17.25f);
        backgroundPath.lineTo(4.61f + 44.93f, 17.25f - 4.21f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f, 17.25f - 4.21f + 3.41f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f + 3.81f, 17.25f - 4.21f + 3.41f + 4.41f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f + 3.81f - 16.25f, 17.25f - 4.21f + 3.41f + 4.41f + 118.15f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f + 3.81f - 16.25f - 5.56f, 17.25f - 4.21f + 3.41f + 4.41f + 118.15f + 3.47f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f + 3.81f - 16.25f - 5.56f - 8.12f, 17.25f - 4.21f + 3.41f + 4.41f + 118.15f + 3.47f + 1.03f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f + 3.81f - 16.25f - 5.56f - 8.12f - 25.64f, 17.25f - 4.21f + 3.41f + 4.41f + 118.15f + 3.47f + 1.03f - 0.04f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f + 3.81f - 16.25f - 5.56f - 8.12f - 25.64f - 20f, 17.25f - 4.21f + 3.41f + 4.41f + 118.15f + 3.47f + 1.03f - 0.04f - 2.41f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f + 3.81f - 16.25f - 5.56f - 8.12f - 25.64f - 20f - 2.73f, 17.25f - 4.21f + 3.41f + 4.41f + 118.15f + 3.47f + 1.03f - 0.04f - 2.41f - 13.9f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f + 3.81f - 16.25f - 5.56f - 8.12f - 25.64f - 20f - 2.73f - 3.4f, 17.25f - 4.21f + 3.41f + 4.41f + 118.15f + 3.47f + 1.03f - 0.04f - 2.41f - 13.9f - 26.84f);
        backgroundPath.lineTo(4.61f + 44.93f + 43.13f + 3.81f - 16.25f - 5.56f - 8.12f - 25.64f - 20f - 2.73f - 3.4f - 6.45f, 17.25f - 4.21f + 3.41f + 4.41f + 118.15f + 3.47f + 1.03f - 0.04f - 2.41f - 13.9f - 26.84f - 46.21f);
        backgroundPath.close();

        // Dibujar el fondo del vaso
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#d7f4ff"));
        canvas.drawPath(backgroundPath, paint);

        // Dibujar el agua recortada por el vaso
        canvas.save();
        canvas.clipPath(glassPath); // El agua solo se verá dentro del vaso
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#00b2ff"));
        canvas.drawPath(waterPath, paint);
        canvas.restore();

        // Dibujar los bordes del vaso con los nuevos paths
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4); // Grosor de trazo ajustado a 4
        paint.setStrokeJoin(Paint.Join.ROUND);  // Borde suave en las uniones
        paint.setStrokeCap(Paint.Cap.ROUND);    // Borde redondeado en las terminaciones

        // Path 1 (Borde superior del vaso)
        Path path1 = new Path();
        path1.moveTo(4.35f, 20.4f);
        path1.cubicTo(3.06f, 10.49f, 97.82f, 11.1f, 96.45f, 21f);
        path1.lineTo(80f, 140f);
        path1.cubicTo(79.32f, 144.95f, 20.65f, 144.96f, 20f, 140f);
        path1.close();

        // Path 2 (Borde inferior del vaso)
        Path path2 = new Path();
        path2.moveTo(3.75f, 19.66f);
        path2.cubicTo(29.14f, 27.13f, 70.98f, 28.53f, 95.96f, 19.52f);

        // Dibujar los paths de los bordes con suavizado
        canvas.drawPath(path1, paint);
        canvas.drawPath(path2, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Aumentar la resolución del Canvas
        setLayerType(LAYER_TYPE_HARDWARE, null);
    }
}
