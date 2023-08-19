package hn.uth2.examen_200860120007.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ActividadRepository {

    private ActividadDao dao;
    private LiveData<List<Actividad>> dataset;

    public ActividadRepository(Application app) {
        ActividadDatabase db = ActividadDatabase.getDatabase(app);
        this.dao = db.actividadDao();
        this.dataset = dao.getActividad();
    }

    public LiveData<List<Actividad>> getDataset() {
        return dataset;
    }
    public void insert(Actividad nuevo){

        ActividadDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(nuevo);
        });
    }

    public void update(Actividad actualizar){

        ActividadDatabase.databaseWriteExecutor.execute(() -> {
            dao.update(actualizar);
        });
    }

    public void delete(Actividad borrar){

        ActividadDatabase.databaseWriteExecutor.execute(() -> {
            dao.delete(borrar);
        });

    }

    public void deleteAll(){

        ActividadDatabase.databaseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }

}
