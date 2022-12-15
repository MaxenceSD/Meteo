package com.smapps.meteo.service.network;

import com.smapps.meteo.model.dto.MeteoDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiEndpoints {

    @Headers({"Content-Type: application/json"})
    @GET("/data/2.5/weather")
    Call<MeteoDto> getMeteoParCoordonnees(
            @Query("lat") float latitude,
            @Query("lon") float longitude,
            @Query("appid") String apiKey,
            @Query("lang") String langue,
            @Query("units") String unite
    );

    @Headers({"Content-Type: image/png"})
    @GET("http://openweathermap.org/img/w/{code}.png")
    Call<ResponseBody> getIconeMeteoParCode(@Path("code") String code);
}
