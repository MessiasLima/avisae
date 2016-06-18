package br.ufc.caps.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

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

    public void escolherLugar(View v){
        Log.e("ei", "veio");
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), 1);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

}
