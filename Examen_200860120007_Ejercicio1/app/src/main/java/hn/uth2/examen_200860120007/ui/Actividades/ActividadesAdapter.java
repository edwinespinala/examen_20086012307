package hn.uth2.examen_200860120007.ui.Actividades;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth2.examen_200860120007.OnItemClickListener;
import hn.uth2.examen_200860120007.database.Actividad;
import hn.uth2.examen_200860120007.databinding.ActividadItemBinding;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.ViewHolder> {

    private List<Actividad> dataset;
    private OnItemClickListener<Actividad> manejadorEventoClick;


    public ActividadesAdapter(List<Actividad> dataset, OnItemClickListener<Actividad> manejadorEventoClick) {
        this.dataset = dataset;
        this.manejadorEventoClick = manejadorEventoClick;
    }


    @NonNull
    @Override
    public ActividadesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActividadItemBinding binding = ActividadItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesAdapter.ViewHolder holder, int position) {
        Actividad actividadMostrar = dataset.get(position);
        holder.binding.tvActividad.setText(actividadMostrar.getActividad());
        holder.binding.tvLatitud.setText(actividadMostrar.getLatitud()+" ");
        holder.binding.tvLongitud.setText(actividadMostrar.getLongitud()+" ");

        holder.setOnClickListener(actividadMostrar, manejadorEventoClick);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public void setItems(List<Actividad> actividad){
        this.dataset = actividad;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ActividadItemBinding binding;

        public ViewHolder(@NonNull ActividadItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        public void setOnClickListener(Actividad actividadMostrar, OnItemClickListener<Actividad> listener) {
            this.binding.cvActividad.setOnClickListener(v -> listener.onItemClick(actividadMostrar));
        }

    }

}
