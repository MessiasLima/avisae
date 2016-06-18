package br.ufc.caps.activity;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import br.ufc.caps.R;

public class NewLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ac = getSupportActionBar();
        ac.setTitle("app");
        setContentView(R.layout.activity_new_local);
        ImageButton bt2 = (ImageButton) this.findViewById(R.id.botaoIm2);
        bt2.setColorFilter(Color.argb(150, 255, 255, 255));
        ImageButton bt3 = (ImageButton) this.findViewById(R.id.botaoIm3);
        bt3.setColorFilter(Color.argb(200, 255, 255, 255));
        ImageButton bt4 = (ImageButton) this.findViewById(R.id.botaoIm3);
        bt4.setColorFilter(Color.argb(200, 255, 255, 255));
    }

}
