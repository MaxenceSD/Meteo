package com.smapps.meteo.model.dto;

import com.google.gson.annotations.SerializedName;

public class MeteoGeneraleDto {
    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icone;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
