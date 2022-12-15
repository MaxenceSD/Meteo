package com.smapps.meteo.model;

public class Ville {
    private String libelle;
    private float latitude;
    private float longitude;
    private Meteo meteo;

    public Ville(String libelle, float latitude, float longitude) {
        this(libelle, latitude, longitude, null);
    }

    public Ville(String libelle, float latitude, float longitude, Meteo meteo) {
        this.libelle = libelle;
        this.latitude = latitude;
        this.longitude = longitude;
        this.meteo = meteo;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Meteo getMeteo() {
        return meteo;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }
}
