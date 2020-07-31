package co.desofsi.tiendavirtual.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import co.desofsi.tiendavirtual.R;


public class MapHomeFragment extends Fragment implements OnMapReadyCallback {
    private View view;
    private GoogleMap mMap;
    private MapView mapView;
    LocationManager localizar;
    Location llocaliza;

    public MapHomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_treatment, container, false);
        init();
        return view;
    }

    public void init() {
        getPositionUser();
    }

    public void getPositionUser() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(getContext(), "no hay permiso", Toast.LENGTH_LONG).show();
        else {
            localizar = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            llocaliza = localizar.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (llocaliza != null) {
                //  txt_lon.setText(String.valueOf(llocaliza.getLongitude()));
                // txt_lati.setText(String.valueOf(llocaliza.getLatitude()));
                Toast.makeText(getContext(), " ubicacion  " + llocaliza.getLatitude() + " , " + llocaliza.getLongitude(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "no hay ubicacion", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.fragment_map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(llocaliza.getLatitude(), llocaliza.getLongitude()), 12));
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(llocaliza.getLatitude(), llocaliza.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}
