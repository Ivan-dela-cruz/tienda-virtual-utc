package co.desofsi.tiendavirtual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import co.desofsi.tiendavirtual.R;
import co.desofsi.tiendavirtual.maps.MapsActivity;


public class OrderFragment extends Fragment {
    private View view;
    public Button btn_mapa;

    public OrderFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        //init();
        btn_mapa = view.findViewById(R.id.btn_mapa);

        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MapsActivity.class));
            }
        });
        return view;
    }
}
