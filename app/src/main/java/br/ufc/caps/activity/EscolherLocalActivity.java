package br.ufc.caps.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.ufc.caps.R;
import br.ufc.caps.geofence.Local;

public class EscolherLocalActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.SnapshotReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    private LatLng localSelecionado;
    private TextView textView;
    private SupportMapFragment mapFragment;
    private Circle circuloRaio;
    private ActionBar ac;
    private SeekBar sb;
    private TextView textoRaio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_local_externo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ac = getSupportActionBar();
        ac.setDisplayHomeAsUpEnabled(true);
        ac.setDisplayUseLogoEnabled(true);
        ac.setDisplayHomeAsUpEnabled(true);
        textView = (TextView) findViewById(R.id.ref);
        sb = (SeekBar)findViewById(R.id.seekbar);
        addEscutadoresSB();
        textoRaio = (TextView)findViewById(R.id.textoRaioDist);
    }

    private void addEscutadoresSB() {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(circuloRaio!=null){
                    if(progress<4){
                        circuloRaio.setRadius(Local.RAIO_MINIMO);
                        textoRaio.setText(Local.RAIO_MINIMO+" m");
                        return;
                    }
                    circuloRaio.setRadius(progress*6);
                    textoRaio.setText(progress*6+" m");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(0,0,0,80);
        if(getIntent().hasExtra(Local.KEY_LOCALIZACAO)){
            Local localRecebido = (Local)getIntent().getSerializableExtra(Local.KEY_LOCALIZACAO);
            if(localRecebido!=null){
                ac.setTitle(R.string.editar_local);
                localSelecionado = new LatLng(localRecebido.getLatitude(), localRecebido.getLongitude());
                marcador = mMap.addMarker(new MarkerOptions().position(localSelecionado));
                sb.setProgress(Math.round(localRecebido.getRaio()/6));
                textoRaio.setText(Math.round(localRecebido.getRaio())+" m");
                circuloRaio = mMap.addCircle(new CircleOptions().center(localSelecionado).radius(localRecebido.getRaio()).strokeColor(Color.BLACK).fillColor(Color.argb(30, 51, 204, 255)).strokeWidth(2));
            }else{
                ac.setTitle(R.string.adicionar_local);
            }
        }else{
            ac.setTitle(R.string.adicionar_local);
        }


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
                if (marcador == null) {
                    marcador = mMap.addMarker(new MarkerOptions().position(latLng));
                    circuloRaio = mMap.addCircle(new CircleOptions().center(localSelecionado).radius(Local.RAIO_PADRAO).strokeColor(Color.BLACK).fillColor(Color.argb(30, 51, 204, 255)).strokeWidth(2));
                    sb.setProgress(Math.round(Local.RAIO_PADRAO/6));
                    textoRaio.setText(Math.round(Local.RAIO_PADRAO)+" m");
                } else {
                    marcador.setPosition(latLng);
                    circuloRaio.setCenter(localSelecionado);
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                /*Snackbar snackbar = Snackbar.make(textView, R.string.local_selecionado, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.confirmar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMap.snapshot(EscolherLocalActivity.this);
                    }
                });
                snackbar.show();*/
            }
        });


        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mMap.getMyLocation() != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationToLatLng(mMap.getMyLocation()), 16));
                }
            }
        }, 1000);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        else if(item.getItemId() == R.id.salvarLocalMapa){
            if(marcador!=null){
                mMap.snapshot(EscolherLocalActivity.this);
            }
            else{
                Snackbar snackbar = Snackbar.make(textView, R.string.selecione_local, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private LatLng locationToLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_escolher_local, menu);
        return true;
    }

    @Override
    public void onSnapshotReady(Bitmap bitmap) {

        bitmap = Bitmap.createBitmap(bitmap,0,(int)bitmap.getHeight()/3,bitmap.getWidth(),200);
        Intent intent = getIntent();
        intent.putExtra(Local.KEY_LOCALIZACAO, localSelecionado);
        intent.putExtra(Local.KEY_THUMBNAIL, bitmap);
        intent.putExtra(Local.KEY_RAIO,circuloRaio.getRadius());
        setResult(RESULT_OK, intent);
        finish();
    }

    /*public void aumentaRaio(View v){
        if(circuloRaio!=null && circuloRaio.getRadius()<Local.RAIO_MAXIMO){
            circuloRaio.setRadius(circuloRaio.getRadius()+20);
        }
    }

    public void diminuiRaio(View v){
        if(circuloRaio!=null && circuloRaio.getRadius()>Local.RAIO_MINIMO){
            circuloRaio.setRadius(circuloRaio.getRadius()-20);
        }
    }*/
}
