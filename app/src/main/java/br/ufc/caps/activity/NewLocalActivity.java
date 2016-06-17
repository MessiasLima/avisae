package br.ufc.caps.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.ufc.caps.R;

public class NewLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ac = getSupportActionBar();
        ac.setTitle("app");
        setContentView(R.layout.activity_new_local);
    }

}
