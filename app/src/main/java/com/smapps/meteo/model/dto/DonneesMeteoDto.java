package com.smapps.meteo.model.dto;

import com.google.gson.annotations.SerializedName;

public class DonneesMeteoDto {
    @SerializedName("temp")
    private Double temperature;

    @SerializedName("pressure")
    private Integer pressionAtmospherique;

    @SerializedName("humidity")
    private Integer humidite;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getPressionAtmospherique() {
        return pressionAtmospherique;
    }

    public void setPressionAtmospherique(Integer pressionAtmospherique) {
        this.pressionAtmospherique = pressionAtmospherique;
    }

    public Integer getHumidite() {
        return humidite;
    }

    public void setHumidite(Integer humidite) {
        this.humidite = humidite;
    }
}
