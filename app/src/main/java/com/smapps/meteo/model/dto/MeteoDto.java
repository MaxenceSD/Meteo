package com.smapps.meteo.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeteoDto {
    @SerializedName("weather")
    private List<MeteoGeneraleDto> meteoGeneraleDto = null;

    @SerializedName("main")
    private DonneesMeteoDto main;

    @SerializedName("wind")
    private VentDto vent;

    public List<MeteoGeneraleDto> getMeteoGeneraleDto() {
        return meteoGeneraleDto;
    }

    public void setMeteoGeneraleDto(List<MeteoGeneraleDto> meteoGeneraleDto) {
        this.meteoGeneraleDto = meteoGeneraleDto;
    }

    public DonneesMeteoDto getDonneesMeteoDto() {
        return main;
    }

    public void setDonneesMeteoDto(DonneesMeteoDto main) {
        this.main = main;
    }

    public VentDto getVent() {
        return vent;
    }

    public void setVent(VentDto ventDto) {
        this.vent = ventDto;
    }
}
