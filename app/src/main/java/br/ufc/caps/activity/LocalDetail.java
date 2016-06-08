package br.ufc.caps.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.ufc.caps.R;
import br.ufc.caps.geofence.Local;

public class LocalDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_detail);
        Local local = (Local) getIntent().getExtras().getSerializable(Local.KEY);
        getSupportActionBar().setTitle(local.getNome());
    }
}
