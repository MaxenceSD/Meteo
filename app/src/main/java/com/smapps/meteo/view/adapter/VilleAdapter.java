package com.smapps.meteo.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smapps.meteo.R;
import com.smapps.meteo.model.Ville;

import java.text.MessageFormat;
import java.util.List;

public class VilleAdapter extends RecyclerView.Adapter<VilleAdapter.ViewHolderVille> {
    protected static class ViewHolderVille extends RecyclerView.ViewHolder {
        private final ImageView iconeMeteo;
        private final ImageView iconeVent;
        private final ImageView iconePression;
        private final ImageView iconeHumidite;
        private final TextView nomVille;
        private final TextView descriptionMeteo;
        private final TextView temperature;
        private final TextView humidite;
        private final TextView pression;
        private final TextView vitesseVent;

        public ViewHolderVille(@NonNull View itemView/*, ImageView iconeMeteo, TextView nomVille, TextView descriptionMeteo, TextView temperature, TextView humidite, TextView pression, TextView vitesseVent*/) {
            super(itemView);

            this.iconeMeteo = itemView.findViewById(R.id.icone_meteo);
            this.iconeVent = itemView.findViewById(R.id.vent_icone);
            this.iconePression = itemView.findViewById(R.id.pression_icone);
            this.iconeHumidite = itemView.findViewById(R.id.humidite_icon);
            this.nomVille = itemView.findViewById(R.id.nom_ville);
            this.descriptionMeteo = itemView.findViewById(R.id.description_meteo);
            this.temperature = itemView.findViewById(R.id.temperature);
            this.humidite = itemView.findViewById(R.id.humidite);
            this.pression = itemView.findViewById(R.id.pression);
            this.vitesseVent = itemView.findViewById(R.id.vitesse_vent);
        }
    }

    private List<Ville> listeVilles;

    public VilleAdapter(List<Ville> listeVilles) {
        this.listeVilles = listeVilles;
    }

    @NonNull
    @Override
    public ViewHolderVille onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_ville, parent, false);
        return new ViewHolderVille(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVille viewHolder, int position) {
        Ville villeCourante = this.listeVilles.get(position);

        if (!TextUtils.isEmpty(villeCourante.getLibelle())) {
            viewHolder.nomVille.setText(villeCourante.getLibelle());
        }

        if (villeCourante.getMeteo() != null) {
            viewHolder.iconeVent.setVisibility(View.VISIBLE);
            viewHolder.iconeHumidite.setVisibility(View.VISIBLE);
            viewHolder.iconePression.setVisibility(View.VISIBLE);
            viewHolder.iconeMeteo.setImageBitmap(villeCourante.getMeteo().getIcone());
            viewHolder.temperature.setText(MessageFormat.format("{0}°c", villeCourante.getMeteo().getTemperature().toString()));
            viewHolder.descriptionMeteo.setText(villeCourante.getMeteo().getDescription());
            viewHolder.humidite.setText(MessageFormat.format("{0}%", villeCourante.getMeteo().getHumidite().toString()));
            viewHolder.pression.setText(MessageFormat.format("{0} hPa", villeCourante.getMeteo().getPression().toString()));
            viewHolder.vitesseVent.setText(MessageFormat.format("{0} Km/h", villeCourante.getMeteo().getVitesseVent().toString()));
        } else {
            viewHolder.descriptionMeteo.setText("Données météo indisponibles");
            viewHolder.iconeVent.setVisibility(View.GONE);
            viewHolder.iconeHumidite.setVisibility(View.GONE);
            viewHolder.iconePression.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.listeVilles.size();
    }
}
