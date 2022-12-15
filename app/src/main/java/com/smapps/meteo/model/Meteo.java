package com.smapps.meteo.model;

import android.graphics.Bitmap;

public class Meteo {
    private Bitmap icone;
    private String description;
    private Double temperature;
    private Integer humidite;
    private Double pression;
    private Double vitesseVent;

    public Meteo() {
    }

    public Meteo(Bitmap icone, String description, Double temperature, Integer humidite, Double pression, Double vitesseVent) {
        this.icone = icone;
        this.description = description;
        this.temperature = temperature;
        this.humidite = humidite;
        this.pression = pression;
        this.vitesseVent = vitesseVent;
    }

    public Bitmap getIcone() {
        return icone;
    }

    public void setIcone(Bitmap icone) {
        this.icone = icone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidite() {
        return humidite;
    }

    public void setHumidite(Integer humidite) {
        this.humidite = humidite;
    }

    public Double getPression() {
        return pression;
    }

    public void setPression(Double pression) {
        this.pression = pression;
    }

    public Double getVitesseVent() {
        return vitesseVent;
    }

    public void setVitesseVent(Double vitesseVent) {
        this.vitesseVent = vitesseVent;
    }
}
