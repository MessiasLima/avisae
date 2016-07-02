package br.ufc.caps.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.ufc.caps.R;
import br.ufc.caps.geofence.Local;

public class LocalDetail extends AppCompatActivity {
    private TextView recadoLembrete;
    private TextView tituloLembrete;
    private TextView horarioLembrete;
    private ImageView imagemCapaLembrete;
    private LinearLayout fundoCapaLembrete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_detail);
        Bundle extras = getIntent().getExtras();
        Local local = (Local) extras.getSerializable(Local.KEY);
        Toolbar tb = (Toolbar)findViewById(R.id.barraLembrete);
        setSupportActionBar(tb);
        ActionBar ac = getSupportActionBar();
        ac = getSupportActionBar();
        ac.setTitle("");
        ac.setDefaultDisplayHomeAsUpEnabled(true);
        ac.setDisplayUseLogoEnabled(true);
        ac.setBackgroundDrawable(new ColorDrawable(Color.argb(70, 0, 0, 0)));
        initScreen(local);

    }

    private void initScreen(Local local) {
        imagemCapaLembrete = (ImageView) findViewById(R.id.imagemCapaLembrete);
        recadoLembrete = (TextView) findViewById(R.id.recadoLembrete);
        tituloLembrete = (TextView) findViewById(R.id.tituloLembrete);
        horarioLembrete = (TextView) findViewById(R.id.horarioLembrete);
        fundoCapaLembrete = (LinearLayout) findViewById(R.id.fundoCapaLembrete);
        tituloLembrete.setText(local.getNome());
        recadoLembrete.setText(local.getTexto());
        if(local.getTempo().equals("--")){
            horarioLembrete.setText(R.string.dia_todo);
        }
        else{
            horarioLembrete.setText(local.getTempo().replace(";"," - "));
        }

        switch (local.getImagem()){
            case 1:
                if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                    fundoCapaLembrete.setBackgroundColor(Color.argb(255, 106, 240, 174));
                }
                imagemCapaLembrete.setImageResource(R.drawable.ic_1);
                break;
            case 2:
                if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                    fundoCapaLembrete.setBackgroundColor(Color.argb(255,255,139,129));
                }
                imagemCapaLembrete.setImageResource(R.drawable.ic_2);
                break;
            case 3:
                if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                    fundoCapaLembrete.setBackgroundColor(Color.argb(255, 179, 137, 255));
                }
                imagemCapaLembrete.setImageResource(R.drawable.ic_3);
                break;
            case 4:
                if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                    fundoCapaLembrete.setBackgroundColor(Color.argb(255, 255, 255, 142));
                }
                imagemCapaLembrete.setImageResource(R.drawable.ic_4);
                break;
            default:
                Log.e("erro", "uma imagem que nao possui id valido foi lançada");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_local_detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.fecharLembrete){
            //aqui será chamado o metodo de desabilitar o local, ou nao?
            finish();
        }
        return true;
    }
}
