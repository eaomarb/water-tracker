package com.eaomarb.watertracker;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private final int maxGlasses = 10; // Número máximo de vasos
    private final int maxProgress = 10; // Máximo de vasos
    // Declaración de variables
    private ProgressBar circleProgressBar; // Para la barra de progreso circular
    private ImageView waterGlassImageView; // Para el vaso de agua
    private ImageView addGlassButton; // Para el botón de añadir vaso
    private ImageView removeGlassButton; // Para el botón de restar vaso
    private int currentGlasses = 0; // Contador de vasos actuales
    private VectorDrawableCompat waterDrawable; // Para el nivel de agua
    private FrameLayout adContainerView;
    private WaterLevelView waterLevelView;
    private int waterLevel = 0; // Nivel de agua actual (0 a 100)
    private int currentProgress = 0; // Inicialmente a 0
    private TextView percentageText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(
                () -> {
                    // Initialize the Google Mobile Ads SDK on a background thread.
                    MobileAds.initialize(this, initializationStatus -> {
                    });
                })
                .start();

        // Inicializar el contenedor del anuncio
        adContainerView = findViewById(R.id.adContainerView);

        // Inicializar Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {
        });

        // Configurar y cargar el anuncio
        loadBannerAd();
        // Inicializar las variables
        circleProgressBar = findViewById(R.id.circleProgressBar);
        waterGlassImageView = findViewById(R.id.waterGlassImageView);
        addGlassButton = findViewById(R.id.addGlassButton);
        removeGlassButton = findViewById(R.id.removeGlassButton);
        percentageText = findViewById(R.id.percentageText);

        waterLevelView = findViewById(R.id.waterLevelView);


        // Inicializar la barra de progreso
        circleProgressBar.setMax(maxGlasses);
        circleProgressBar.setProgress(currentGlasses);

        // Configurar el listener para los botones
        circleProgressBar.setMax(maxProgress);

        // Listener para añadir un vaso
        addGlassButton.setOnClickListener(v -> {
            if (currentProgress < maxProgress) {
                currentProgress++;
                updateUI();
            }
        });

        // Listener para quitar un vaso
        removeGlassButton.setOnClickListener(v -> {
            if (currentProgress > 0) {
                currentProgress--;
                updateUI();
            }
        });
    }


    private void updateUI() {
        circleProgressBar.setProgress(currentProgress);
        waterLevelView.setWaterPercentage((currentProgress * 100f) / maxProgress);
        percentageText.setText((int) ((currentProgress * 100f) / maxProgress) + "%");
    }


    // Get the ad size with screen width.
    public AdSize getAdSize() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int adWidthPixels = displayMetrics.widthPixels;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
            adWidthPixels = windowMetrics.getBounds().width();
        }

        float density = displayMetrics.density;
        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }


    private void loadBannerAd() {
        // Crear un nuevo AdView
        AdView adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-3940256099942544/9214589741"); // ID de prueba
        adView.setAdSize(getAdSize());

        // Reemplazar el contenedor con el nuevo AdView
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        // Cargar el anuncio
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("AdMob", "Error al cargar el anuncio: " + adError.getMessage());
            }

            @Override
            public void onAdLoaded() {
                Log.d("AdMob", "Anuncio cargado correctamente.");
            }
        });

    }
}
