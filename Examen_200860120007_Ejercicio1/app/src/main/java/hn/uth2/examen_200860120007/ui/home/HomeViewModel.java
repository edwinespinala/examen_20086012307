package hn.uth2.examen_200860120007.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import hn.uth2.examen_200860120007.database.Actividad;
import hn.uth2.examen_200860120007.database.ActividadRepository;

public class HomeViewModel extends AndroidViewModel {

    private ActividadRepository repository;
    private final LiveData<List<Actividad>> actividadDataset;


    public HomeViewModel(@NonNull Application app) {
        super(app);
        this.repository = new ActividadRepository(app);
        this.actividadDataset = repository.getDataset();
    }
    public ActividadRepository getRepository() {
        return repository;
    }

    public LiveData<List<Actividad>> getActividadDataset() {
        return actividadDataset;
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

    public void deleteAll(){
        repository.deleteAll();
    }
}