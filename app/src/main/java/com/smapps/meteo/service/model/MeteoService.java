package com.smapps.meteo.service.model;

import com.smapps.meteo.model.Meteo;
import com.smapps.meteo.model.dto.MeteoDto;

public class MeteoService {
    public Meteo convertirDto(MeteoDto meteoDto) {
        Meteo meteo = new Meteo();

        if (meteoDto.getMeteoGeneraleDto() != null && meteoDto.getMeteoGeneraleDto().size() > 0) {
            String description = meteoDto.getMeteoGeneraleDto().get(0).getDescription();
            meteo.setDescription(description.substring(0, 1).toUpperCase() + description.substring(1));
        }
        if (meteoDto.getDonneesMeteoDto() != null) {
            if (meteoDto.getDonneesMeteoDto().getTemperature() != null) {
                meteo.setTemperature(arrondirDouble(meteoDto.getDonneesMeteoDto().getTemperature()));
            }
            meteo.setHumidite(meteoDto.getDonneesMeteoDto().getHumidite());
            if (meteoDto.getDonneesMeteoDto().getPressionAtmospherique() != null) {
                meteo.setPression(arrondirDouble((double) meteoDto.getDonneesMeteoDto().getPressionAtmospherique() / 100));
            }
        }
        if (meteoDto.getVent() != null && meteoDto.getVent().getVitesse() != null) {
            meteo.setVitesseVent(arrondirDouble(meteoDto.getVent().getVitesse()));
        }
        return meteo;
    }

    private Double arrondirDouble(Double d) {
        int precision = (int) Math.pow(10, 1);
        return (double) Math.round(d * precision) / precision;
    }
}
