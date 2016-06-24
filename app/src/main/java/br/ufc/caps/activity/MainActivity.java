package br.ufc.caps.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import br.ufc.caps.R;
import br.ufc.caps.database.BD;
import br.ufc.caps.geofence.GeofencingManager;
import br.ufc.caps.geofence.Local;
import br.ufc.caps.recyclerView.LocalCustomAdapter;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MAIN ACTIVITY";
    public static final int NEW_LOCAL_REQUEST_CODE = 2;

    private RecyclerView recyclerView;
    private CoordinatorLayout cl;
    private BD dataBase;

    public ProgressDialog progressDialog;
    GeofencingManager geofencingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setMessage(getString(R.string.carregando));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Intent i = new Intent(this,LocalDetail.class);
        Local li = new Local();
        li.setTempo("07:30;21:45");
        li.setNome("Comprar Varios Leites");
        li.setTexto("Lembrar de limpar a bunda após arriar a massa");
        li.setImagem(2);
        i.putExtra(Local.KEY,li);
        startActivity(i);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        googleApiClient.connect();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cl = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, NewLocalActivity.class), NEW_LOCAL_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mostraCardsNaTela();
        if (requestCode == 2 && resultCode == RESULT_OK) {
            String mensagem = data.getStringExtra("mensagemPersistencia");
            if (mensagem != null) {
                if (mensagem.equals("sa")) {
                    Snackbar barra = Snackbar.make(cl, R.string.sucesso_persistencia_adicionar, Snackbar.LENGTH_LONG);
                    barra.show();

                } else if (mensagem.equals("se")) {
                    Snackbar barra = Snackbar.make(cl, R.string.sucesso_persistencia_editar, Snackbar.LENGTH_LONG);
                    barra.show();
                }
                //nao tem else, vai cair num dos dois, coloquei a comparacao aqui pra o codigo ficar mais claro
            }
        }
    }

    @Override
    protected void onResume() {
        if (recyclerView == null) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        }
        if (dataBase == null) {
            dataBase = new BD(this);
        }
        super.onResume();
    }

    public void mostraCardsNaTela() {
        List<Local> locals = dataBase.buscar();
        LocalCustomAdapter customAdapter = new LocalCustomAdapter(this, locals);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        atualizarGeofences(locals);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        mostraCardsNaTela();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // If the error has a resolution, start a Google Play services activity to resolve it.
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Exception while resolving connection error.", e);
            }
        } else {
            int errorCode = connectionResult.getErrorCode();
            Log.e(TAG, "Connection to Google Play services failed with error code " + errorCode);
        }
    }

    public void atualizarGeofences(List<Local> locals) {
        geofencingManager = GeofencingManager.getInstance(this, googleApiClient);
        for (Local local : locals) {
            Geofence localGeofence = local.getGeofence();
            geofencingManager.addGeofence(localGeofence);
        }
        geofencingManager.registerGeofences();
    }

    public void retirarGeofence(String id) {
        if (geofencingManager != null) {
            geofencingManager.removeGeofence(id);
        }
    }
}
