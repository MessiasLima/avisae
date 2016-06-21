package br.ufc.caps.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.ufc.caps.R;
import br.ufc.caps.geofence.Local;

public class EscolherLocalActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    private LatLng localSelecionado;
    private TextView textView;
private  SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_local_externo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = (TextView) findViewById(R.id.ref);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera


        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                localSelecionado = latLng;
                if (marcador == null){
                    marcador = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
                }else {
                    marcador.setPosition(latLng);
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                Snackbar snackbar =  Snackbar.make(textView,R.string.local_selecionado,Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.confirmar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        intent.putExtra(Local.KEY_LOCALIZACAO,localSelecionado);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
                snackbar.show();
            }
        });


        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mMap.getMyLocation() != null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationToLatLng(mMap.getMyLocation()),16));
                }
            }
        },1000);

    }

    private LatLng locationToLatLng(Location location){
        return new LatLng(location.getLatitude(),location.getLongitude());
    }
}
