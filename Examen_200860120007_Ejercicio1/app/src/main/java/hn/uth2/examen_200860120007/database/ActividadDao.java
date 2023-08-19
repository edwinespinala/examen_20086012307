package hn.uth2.examen_200860120007.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ActividadDao {
    @Insert
    void insert(Actividad nuevo);

    @Update
    void update(Actividad actualizar);

    @Query("DELETE FROM Actividad_table")
    void deleteAll();

    @Delete
    void delete(Actividad eliminar);

    @Query("select * from Actividad_table order by actividad")
    LiveData<List<Actividad>> getActividad();
}
