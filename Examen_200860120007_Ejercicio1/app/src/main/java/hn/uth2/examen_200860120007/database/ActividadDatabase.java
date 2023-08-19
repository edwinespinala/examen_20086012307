package hn.uth2.examen_200860120007.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1, exportSchema = false, entities = {Actividad.class})
public abstract class ActividadDatabase extends RoomDatabase{
    public abstract ActividadDao actividadDao();

    private static volatile ActividadDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //GENERANDO UNA INSTANCIA MEDIANTE PATRÓN DE SOFTWARE SINGLETON
    static ActividadDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ActividadDatabase.class){
                if(INSTANCE == null){

                    Callback miCallback = new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            databaseWriteExecutor.execute(() -> {
                                ActividadDao dao = INSTANCE.actividadDao();
                                dao.deleteAll();
                            });

                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ActividadDatabase.class, "clientes_db").addCallback(miCallback).build();
                }
            }
        }
        return INSTANCE;
    }
}
