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

import br.com.kakobotasso.elcodechallenge.utils.Constantes;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean usaLocalizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        usaLocalizacao = getIntent().getBooleanExtra(Constantes.CHAVE_LOCALIZACAO, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if( usaLocalizacao && Permissive.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ){
//            LatLng posicaoAtual = pegaPosicaoAtual();
            mMap.setMyLocationEnabled(true);
        }
    }

    private LatLng pegaPosicaoAtual() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        LatLng retorno = null;
        Location localizacao = null;

        for( String provider : providers ){
            if (localizacao != null) break;

            if(Permissive.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                localizacao = locationManager.getLastKnownLocation(provider);
            }
        }

        if (localizacao != null) {
            retorno = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
        }
        return retorno;
    }
}
