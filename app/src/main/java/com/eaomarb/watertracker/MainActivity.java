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
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import androidx.annotation.NonNull;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.AdRequest;



public class MainActivity extends AppCompatActivity {
    private final int maxGlasses = 10; // Número máximo de vasos
    private final int maxProgress = 10; // Máximo de vasos
    // Declaración de variables
    private ProgressBar circleProgressBar; // Para la barra de progreso circular
    private ImageView addGlassButton; // Para el botón de añadir vaso
    private ImageView removeGlassButton; // Para el botón de restar vaso
    private VectorDrawableCompat waterDrawable; // Para el nivel de agua
    private FrameLayout adContainerView;
    private WaterLevelView waterLevelView;
    private int currentProgress = 0; // Nivel inicial de agua es 0
    private TextView percentageText;
    private TextView glassCount;
    private InterstitialAd mInterstitialAd;
    private boolean isLoadingAd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(() -> {
            // Inicializar el SDK de Google Mobile Ads en un hilo en segundo plano.
            MobileAds.initialize(this, initializationStatus -> {
            });
        }).start();

        // Inicializar el contenedor del anuncio
        adContainerView = findViewById(R.id.adContainerView);

        // Inicializar Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {
        });

        // Cargar el anuncio
        loadBannerAd();
        loadInterstitialAd();

        // Inicializar las variables
        circleProgressBar = findViewById(R.id.circleProgressBar);
        addGlassButton = findViewById(R.id.addGlassButton);
        removeGlassButton = findViewById(R.id.removeGlassButton);
        percentageText = findViewById(R.id.percentageText);
        glassCount = findViewById(R.id.glassCount);
        waterLevelView = findViewById(R.id.waterLevelView);


        // Inicializar la barra de progreso
        circleProgressBar.setMax(maxGlasses);
        circleProgressBar.setProgress(currentProgress);

        // Configurar los listeners para los botones
        circleProgressBar.setMax(maxProgress);

        percentageText.bringToFront();

        // Listener para añadir un vaso
        addGlassButton.setOnClickListener(v -> {
            if (currentProgress < maxProgress) {
                currentProgress++;
                updateUI(); // Actualizar la UI después de añadir un vaso
            }
        });

        // Listener para quitar un vaso
        removeGlassButton.setOnClickListener(v -> {
            if (currentProgress > 0) {
                currentProgress--;
                updateUI(); // Actualizar la UI después de restar un vaso
            }
        });

        // Inicializar la interfaz de usuario con el nivel de agua al 0
        updateUI();
    }

    private void updateUI() {
        circleProgressBar.setProgress(currentProgress);
        float waterPercentage = (currentProgress / (float) maxProgress) * 100;

        waterLevelView.setWaterLevelPercentage(waterPercentage);
        percentageText.setText((int) waterPercentage + "%");
        glassCount.setText(currentProgress + "/10");

        // Mostrar anuncio intersticial si llega a 10 vasos
        if (currentProgress == maxProgress && mInterstitialAd != null) {
            mInterstitialAd.show(this);
        }
    }



    // Obtener el tamaño del anuncio con el ancho de la pantalla.
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

    private void setInterstitialCallbacks() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // El usuario cerró el anuncio
                    Log.d("AdMob", "Anuncio cerrado.");
                    mInterstitialAd = null;
                    loadInterstitialAd(); // Cargar un nuevo anuncio
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                    Log.e("AdMob", "Error al mostrar el anuncio: " + adError.getMessage());
                    mInterstitialAd = null;
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.d("AdMob", "Anuncio mostrado.");
                }
            });
        }
    }


    private void loadInterstitialAd() {
        if (mInterstitialAd != null) {
            return; // Ya existe un anuncio cargado
        }

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.d("AdMob", "Anuncio intersticial cargado.");
                        setInterstitialCallbacks();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                        Log.e("AdMob", "Error al cargar el anuncio: " + loadAdError.getMessage());
                    }
                });
    }


}
