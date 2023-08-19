package hn.uth2.examen_200860120007.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Actividad_table")
public class Actividad implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer idActividad;

    @NonNull
    @ColumnInfo(name = "actividad")
    private String actividad;

    @NonNull
    @ColumnInfo(name = "longitud")
    private double longitud;

    @NonNull
    @ColumnInfo(name = "latitud")
    private double latitud;

    public Actividad(@NonNull String actividad, double longitud, double latitud) {
        this.actividad = actividad;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    @NonNull
    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(@NonNull Integer idActividad) {
        this.idActividad = idActividad;
    }

    @NonNull
    public String getActividad() {
        return actividad;
    }

    public void setActividad(@NonNull String actividad) {
        this.actividad = actividad;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
