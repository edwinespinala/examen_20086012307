package hn.uth2.examen_200860120007.ui.Actividades;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import hn.uth2.examen_200860120007.R;
import hn.uth2.examen_200860120007.database.Actividad;
import hn.uth2.examen_200860120007.databinding.FragmentActividadesBinding;
import hn.uth2.examen_200860120007.entity.GPSLocation;
import hn.uth2.examen_200860120007.ui.home.HomeViewModel;
import hn.uth2.examen_200860120007.ui.Actividades.ActividadesViewModel;

public class ActividadesFragment extends Fragment implements LocationListener {

    private FragmentActividadesBinding binding;
    private Actividad actividadEditar;
    private static final int REQUEST_CODE_GPS = 555;
    private static final int REQUEST_IMAGE_CAPTURE = 600;
    private LocationManager locationManager;

    private ActividadesViewModel actividadesViewModel;
    private GPSLocation ubicacion;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {


         actividadesViewModel = new ViewModelProvider(this).get(ActividadesViewModel.class);
        ubicacion = null;
        binding = FragmentActividadesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.btnCamara.setOnClickListener(v->
                abrirCamara());{
            solicitarPermisosGPS(this.getContext());
        }

        binding.btnGuardar.setOnClickListener(v -> {
            Actividad nuevo = new Actividad(binding.txtActividad.getEditText().getText().toString(),
                    Double.parseDouble(binding.txtLongitud.getText().toString()),
                    Double.parseDouble(binding.txtLatitud.getText().toString()));
            String mensaje = "Actividad fue agragada correctamente";
            if(actividadEditar == null){
                actividadesViewModel.insert(nuevo);
            }else{
                nuevo.setIdActividad(actividadEditar.getIdActividad());
                actividadesViewModel.update(nuevo);
                mensaje = "Actividad fue modificada correctamente";
            }
            Snackbar.make(binding.getRoot(), mensaje, Snackbar.LENGTH_LONG).show();
            limpiarCampos();
            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_home);

        });
        return root;
    }

    private void solicitarPermisosGPS(Context contexto) {
        if(ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //TENGO EL PERMISO, PUEDO UTILIZAR EL GPS
            useFineLocation();
        }else{
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_GPS);
        }
    }



    @SuppressLint({"ServiceCast", "MissingPermission"})
    private void useFineLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GPS) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    useFineLocation();
                } else if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    useCoarseLocation();
                }

            } else {
                Snackbar.make(binding.getRoot(), "No se otorgaron los permisos", Snackbar.LENGTH_LONG).show();

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @SuppressLint({"ServiceCast", "MissingPermission"})
    private void useCoarseLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }


    private void abrirCamara() {
        Intent tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(tomarFotoIntent.resolveActivity(this.getActivity().getPackageManager())!=null){
            startActivityForResult(tomarFotoIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
          Bundle extra = data.getExtras();
           Bitmap imagen = (Bitmap) extra.get("data");
           binding.img.setImageBitmap(imagen);
       }
    }


    public void onLocationChanged(@NonNull Location location) {
        ubicacion = new GPSLocation(location.getLatitude(), location.getLongitude());

        binding.txtLatitud.setText(ubicacion.getLatitudeStr());
        binding.txtLongitud.setText(ubicacion.getLongitudeStr());

        //DETENER ACTUALIZACION DE UBICACION PARA DEJARLO DE UN SOLO USO (SI SE QUIERE SEGUIMIENTO NO HACER ESTA PARTE)
        locationManager.removeUpdates((LocationListener) this);
    }

    private void limpiarCampos() {
        binding.txtActividad.getEditText().setText("");
        binding.txtLatitud.setText("");
        binding.txtLongitud.setText("");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}