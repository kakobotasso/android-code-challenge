package br.com.kakobotasso.elcodechallenge;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.github.jksiezni.permissive.Permissive;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean usaLocalizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        usaLocalizacao = getIntent().getBooleanExtra("localizacao", false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if( usaLocalizacao ){
            LatLng posicaoAtual = getGPS();
            mMap.addMarker(new MarkerOptions().position(posicaoAtual).title("Posição Atual"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(posicaoAtual));
        }else{
            // Add a marker in Sydney and move the camera
            LatLng centro = new LatLng(0, 0);
            mMap.addMarker(new MarkerOptions().position(centro).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(centro));
        }
    }

    private LatLng getGPS() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        LatLng retorno = null;

/* Loop over the array backwards, and if you get an accurate location, then break                 out the loop*/
        Location l = null;

        for (int i=providers.size()-1; i>=0; i--) {
            if(Permissive.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                l = lm.getLastKnownLocation(providers.get(i));
            }

            if (l != null) break;
        }

        double[] gps = new double[2];
        if (l != null) {
            retorno = new LatLng(l.getLatitude(), l.getLongitude());
        }
        return retorno;
    }
}
