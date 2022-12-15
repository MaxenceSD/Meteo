package com.smapps.meteo.service.network;

import androidx.annotation.NonNull;

import com.smapps.meteo.BuildConfig;
import com.smapps.meteo.model.Ville;
import com.smapps.meteo.model.dto.MeteoDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String TAG = NetworkService.class.getSimpleName();
    private static final String BASE_URL = "https://api.openweathermap.org";

    private Retrofit retrofit;

    public NetworkService() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public void getMeteoParVille(Ville ville, NetworkCallback<MeteoDto> callback) {
        IApiEndpoints endpoint = this.retrofit.create(IApiEndpoints.class);
        Call<MeteoDto> meteoCall = endpoint.getMeteoParCoordonnees(ville.getLatitude(), ville.getLongitude(), BuildConfig.OPENWEATHER_API_KEY, "fr", "metric");
        meteoCall.enqueue(new Callback<MeteoDto>() {
            @Override
            public void onResponse(@NonNull Call<MeteoDto> call, @NonNull Response<MeteoDto> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(call, response);
                } else {
                    callback.onFailure(call, response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MeteoDto> call, @NonNull Throwable t) {
                callback.onNetworkFailure(call, t);
            }
        });
    }

    public void getIconeParCode(String code, NetworkCallback<ResponseBody> callback) {
        IApiEndpoints endpoint = this.retrofit.create(IApiEndpoints.class);
        Call<ResponseBody> call = endpoint.getIconeMeteoParCode(code);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(call, response);
                } else {
                    callback.onFailure(call, response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onNetworkFailure(call, t);
            }
        });
    }
}
