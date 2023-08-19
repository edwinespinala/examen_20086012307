package hn.uth2.examen_200860120007.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import hn.uth2.examen_200860120007.OnItemClickListener;
import hn.uth2.examen_200860120007.R;
import hn.uth2.examen_200860120007.database.Actividad;
import hn.uth2.examen_200860120007.databinding.FragmentHomeBinding;
import hn.uth2.examen_200860120007.ui.Actividades.ActividadesAdapter;
import hn.uth2.examen_200860120007.ui.Actividades.ActividadesViewModel;

public class HomeFragment extends Fragment implements OnItemClickListener<Actividad> {

    private FragmentHomeBinding binding;
    private ActividadesAdapter adaptador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        adaptador = new ActividadesAdapter(new ArrayList<Actividad> (), this);

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getActividadDataset().observe(getViewLifecycleOwner(), actividades -> {
            if(actividades.isEmpty()){
                Snackbar.make(binding.clHome,"No hay actividades registradas", Snackbar.LENGTH_LONG).show();
            }else{
                adaptador.setItems(actividades);
            }
        });

        setupRecyclerView();

        return root;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvActividades.setLayoutManager(linearLayoutManager);
        binding.rvActividades.setAdapter(adaptador);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Actividad data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("actividad", data);
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_actividades, bundle);

    }
}