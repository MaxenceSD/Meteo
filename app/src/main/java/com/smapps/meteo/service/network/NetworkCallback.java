package com.smapps.meteo.service.network;

import retrofit2.Call;
import retrofit2.Response;

public interface NetworkCallback<T> {
    void onSuccess(Call<T> call, Response<T> response);

    void onFailure(Call<T> call, Response<T> response);

    void onNetworkFailure(Call<T> call, Throwable t);
}
