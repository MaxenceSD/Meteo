package com.smapps.meteo.activity;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smapps.meteo.service.MessageAttenteService;
import com.smapps.meteo.R;
import com.smapps.meteo.model.Ville;
import com.smapps.meteo.model.dto.MeteoDto;
import com.smapps.meteo.service.model.MeteoService;
import com.smapps.meteo.service.network.NetworkCallback;
import com.smapps.meteo.service.network.NetworkService;
import com.smapps.meteo.view.adapter.VilleAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DetailMeteoActivity extends AppCompatActivity {

    private static final String TAG = DetailMeteoActivity.class.getSimpleName();

    private static final int DUREE_TELECHARGEMENT = 60000;
    private static final int DELAI_MESSAGE_ATTENTE = 6000;
    private static final int BASE_SECONDE = 1000;

    private RecyclerView listeVille;
    private Button boutonRecommencer;
    private TextView messageAttente;
    private TextView pourcentage;
    private ProgressBar barreProgression;

    private List<Ville> villesCibles;

    private NetworkService networkService;
    private MessageAttenteService messageAttenteService;
    private MeteoService meteoService;

    private Handler handlerMessageAttente;
    private Runnable runnableMessageAttente;

    static int indexVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meteo);

        this.networkService = new NetworkService();
        this.meteoService = new MeteoService();
        this.messageAttenteService = new MessageAttenteService(
                "Nous téléchargeons les données...",
                "C’est presque fini...",
                "Plus que quelques secondes avant d’avoir le résultat..."
        );

        this.listeVille = findViewById(R.id.recycler_ville);
        this.boutonRecommencer = findViewById(R.id.bouton_recommencer);
        this.messageAttente = findViewById(R.id.message_attente);
        this.pourcentage = findViewById(R.id.pourcentage);
        this.barreProgression = findViewById(R.id.barre_de_progression);

        this.villesCibles = this.getVillesCibles();

        this.demarrerAnimationsChargement();
        this.telechargerToutesLesDonneesMeteo();

        this.boutonRecommencer.setOnClickListener((v) -> {
            for (Ville ville : villesCibles) {
                ville.setMeteo(null);
            }
            this.demarrerAnimationsChargement();
            this.telechargerToutesLesDonneesMeteo();
        });
    }

    private void telechargerToutesLesDonneesMeteo() {
        telechargerMeteoParVille(villesCibles.get(indexVille));
        new CountDownTimer(DUREE_TELECHARGEMENT, BASE_SECONDE) {
            float progression = 0;

            @Override
            public void onTick(long millisRestantes) {
                long secondesRestante = millisRestantes / BASE_SECONDE;

                progression += (100f / 60f);
//                barreProgression.setProgress((int) (progression + 1));
                ObjectAnimator.ofInt(barreProgression, "progress", (int) (progression + 1) * 10)
                        .setDuration(BASE_SECONDE)
                        .start();
                pourcentage.setText(MessageFormat.format("{0}%", (int) (progression + 1)));

                if (secondesRestante % 10 == 0 && indexVille + 1 < villesCibles.size()) {
                    telechargerMeteoParVille(villesCibles.get(++indexVille));
                }
            }

            @Override
            public void onFinish() {
                arreterMessagesAttente();
                afficherDonneesMeteo();
                arreterAnimationsChargement();
            }
        }.start();
    }

    private void telechargerMeteoParVille(Ville ville) {
        Log.d(TAG, MessageFormat.format("telechargerMeteoParVille : {0}", ville.getLibelle()));
        this.networkService.getMeteoParVille(ville, new NetworkCallback<MeteoDto>() {
            @Override
            public void onSuccess(Call<MeteoDto> call, Response<MeteoDto> response) {
                Log.i(TAG, "telechargerMeteoParVille >> onSuccess");
                if (response != null && response.body() != null) {
                    ville.setMeteo(meteoService.convertirDto(response.body()));
                    telechargerIcone(ville, response.body().getMeteoGeneraleDto().get(0).getIcone());
                } else {
                    Log.e(TAG, "telechargerMeteoParVille >> Erreur lors de la récupération des données météo");
                }
            }

            @Override
            public void onFailure(Call<MeteoDto> call, Response<MeteoDto> response) {
                // Si une erreur se produit, la météo reste null pour la ville. Dans ce cas, un affichage particulier est géré
                Log.e(TAG, MessageFormat.format("telechargerMeteoParVille >> onFailure : {0} - {1}", response.code(), response.errorBody()));
            }

            @Override
            public void onNetworkFailure(Call<MeteoDto> call, Throwable t) {
                // Si une erreur se produit, la météo reste null pour la ville. Dans ce cas, un affichage particulier est géré
                Log.e(TAG, MessageFormat.format("telechargerMeteoParVille >> onNetworkFailure : {}", t.getMessage()));
            }
        });
    }

    private void telechargerIcone(Ville ville, String codeIcone) {
        Log.d(TAG, MessageFormat.format("telechargerIcone : {0}", codeIcone));
        this.networkService.getIconeParCode(codeIcone, new NetworkCallback<ResponseBody>() {
            @Override
            public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "telechargerIcone >> onSuccess");

                boolean result = response.body() != null;
                if (result) {
                    Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                    ville.getMeteo().setIcone(bm);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, MessageFormat.format("telechargerIcone >> onFailure : {0} - {1}", response.code(), response.errorBody()));
            }

            @Override
            public void onNetworkFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, MessageFormat.format("telechargerIcone >> onNetworkFailure : {}", t.getMessage()));
            }
        });
    }

    private void afficherDonneesMeteo() {
        VilleAdapter villeAdapter = new VilleAdapter(this.villesCibles);
        this.listeVille.setHasFixedSize(true);
        this.listeVille.setLayoutManager(new LinearLayoutManager(DetailMeteoActivity.this));
        this.listeVille.setAdapter(villeAdapter);
    }

    private List<Ville> getVillesCibles() {
        List<Ville> villes = new ArrayList<>();
        villes.add(new Ville("Rennes", 48.117266f, -1.6777926f));
        villes.add(new Ville("Paris", 48.856614f, 2.3522219f));
        villes.add(new Ville("Bordeaux", 44.837789f, -0.57918f));
        villes.add(new Ville("Nantes", 47.218371f, -1.553621f));
        villes.add(new Ville("Lyon", 45.764043f, 4.835659f));
        return villes;
    }

    private void demarrerAnimationsChargement() {
        indexVille = 0;
        this.barreProgression.setVisibility(View.VISIBLE);
        this.barreProgression.setProgress(0);
        this.messageAttente.setVisibility(View.VISIBLE);
        this.messageAttente.setText(this.messageAttenteService.getMessage());
        this.pourcentage.setVisibility(View.VISIBLE);
        this.pourcentage.setText("0%");
        this.boutonRecommencer.setVisibility(View.GONE);

        this.demarrerMessagesAttente();
    }

    private void arreterAnimationsChargement() {
        this.barreProgression.setVisibility(View.GONE);
        this.messageAttente.setVisibility(View.GONE);
        this.pourcentage.setVisibility(View.GONE);
        this.boutonRecommencer.setVisibility(View.VISIBLE);
    }

    private void demarrerMessagesAttente() {
        this.handlerMessageAttente = new Handler(Looper.getMainLooper());
        this.handlerMessageAttente.postDelayed(
                runnableMessageAttente = () -> {
                    DetailMeteoActivity.this.messageAttente.setText(DetailMeteoActivity.this.messageAttenteService.getMessageSuivant());
                    handlerMessageAttente.postDelayed(runnableMessageAttente, DELAI_MESSAGE_ATTENTE);
                },
                DELAI_MESSAGE_ATTENTE
        );
    }

    private void arreterMessagesAttente() {
        this.handlerMessageAttente.removeCallbacksAndMessages(null);
    }
}