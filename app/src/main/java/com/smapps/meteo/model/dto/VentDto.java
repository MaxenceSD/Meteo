package com.smapps.meteo.model.dto;

import com.google.gson.annotations.SerializedName;

public class VentDto {
    @SerializedName("speed")
    private Double vitesse;

    public Double getVitesse() {
        return vitesse;
    }

    public void setVitesse(Double vitesse) {
        this.vitesse = vitesse;
    }
}
