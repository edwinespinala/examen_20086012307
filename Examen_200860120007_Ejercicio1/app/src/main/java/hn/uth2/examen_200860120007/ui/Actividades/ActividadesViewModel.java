package hn.uth2.examen_200860120007.ui.Actividades;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;

import hn.uth2.examen_200860120007.database.Actividad;
import hn.uth2.examen_200860120007.database.ActividadRepository;

public class ActividadesViewModel extends AndroidViewModel {

    private ActividadRepository repository;

    public ActividadesViewModel(@NonNull Application app) {
        super(app);
        this.repository = new ActividadRepository(app);
    }
    public ActividadRepository getRepository() {
        return repository;
    }

    public void insert(Actividad nuevo){
        repository.insert(nuevo);
    }

    public void update(Actividad actualizar){
        repository.update(actualizar);
    }

    public void delete(Actividad eliminar){
        repository.delete(eliminar);
    }
}