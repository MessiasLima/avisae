package br.ufc.caps.activity;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TimePicker;
import br.ufc.caps.R;
import br.ufc.caps.database.BD;
import br.ufc.caps.geofence.Local;

public class NewLocalActivity extends AppCompatActivity {
    private ImageButton bt1;
    private ImageButton bt2;
    private ImageButton bt3;
    private ImageButton bt4;
    private Button horarioInicial;
    private Button horarioFinal;
    private Switch chave;
    private RadioButton alarme;
    private RadioButton notificacao;
    private EditText tituloCaixa;
    private EditText recadoCaixa;
    private int modoAviso;
    private int imgEscolhida;
    private String InicioEscolhido;
    private String FinalEscolhido;
    private Local localAPersistir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ac = getSupportActionBar();
        if(getIntent().getSerializableExtra("local")==null){
            Log.e("ta nulo","nuloo");
            ac.setTitle(R.string.titulo_activity_local_adicionar);
            ac.setDefaultDisplayHomeAsUpEnabled(true);
            ac.setDisplayUseLogoEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            setContentView(R.layout.activity_new_local);
            chave = (Switch) this.findViewById(R.id.diaTodo);
            alarme = (RadioButton)this.findViewById(R.id.radioAlarme);
            notificacao = (RadioButton)this.findViewById(R.id.radioNotificacao);
            horarioInicial = (Button)this.findViewById(R.id.horarioInicial);
            horarioFinal = (Button)this.findViewById(R.id.horarioFinal);
            tituloCaixa = (EditText)this.findViewById(R.id.nomeLocal);
            recadoCaixa = (EditText)this.findViewById(R.id.textoRecado);
            bt1 = (ImageButton) this.findViewById(R.id.botaoIm1);
            bt2 = (ImageButton) this.findViewById(R.id.botaoIm2);
            bt3 = (ImageButton) this.findViewById(R.id.botaoIm3);
            bt4 = (ImageButton) this.findViewById(R.id.botaoIm4);

            // aqui to colocando alarme como o default
            alarme.setChecked(true);
            modoAviso = Local.ALARME;

            bt2.setColorFilter(Color.argb(225, 255, 255, 255));
            bt3.setColorFilter(Color.argb(225, 255, 255, 255));
            bt4.setColorFilter(Color.argb(225, 255, 255, 255));
            imgEscolhida=1;
        }
        else{
            ac.setTitle(R.string.titulo_activity_local_editar);
            ac.setDefaultDisplayHomeAsUpEnabled(true);
            ac.setDisplayUseLogoEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            setContentView(R.layout.activity_new_local);
            chave = (Switch) this.findViewById(R.id.diaTodo);
            alarme = (RadioButton)this.findViewById(R.id.radioAlarme);
            notificacao = (RadioButton)this.findViewById(R.id.radioNotificacao);
            horarioInicial = (Button)this.findViewById(R.id.horarioInicial);
            horarioFinal = (Button)this.findViewById(R.id.horarioFinal);
            tituloCaixa = (EditText)this.findViewById(R.id.nomeLocal);
            recadoCaixa = (EditText)this.findViewById(R.id.textoRecado);
            bt1 = (ImageButton) this.findViewById(R.id.botaoIm1);
            bt2 = (ImageButton) this.findViewById(R.id.botaoIm2);
            bt3 = (ImageButton) this.findViewById(R.id.botaoIm3);
            bt4 = (ImageButton) this.findViewById(R.id.botaoIm4);
            localAPersistir = (Local)getIntent().getSerializableExtra("local");
            tituloCaixa.setText(localAPersistir.getNome());
            recadoCaixa.setText(localAPersistir.getTexto());
            //so fiz o titulo, o resumo e o botao.. inda falta colocar o resto na interface para o usuario ver
            switch (localAPersistir.getImagem()){
                case 1:
                    imgEscolhida=1;
                    bt1.setColorFilter(Color.argb(0, 255, 255, 255));
                    bt2.setColorFilter(Color.argb(225, 255, 255, 255));
                    bt3.setColorFilter(Color.argb(225, 255, 255, 255));
                    bt4.setColorFilter(Color.argb(225, 255, 255, 255));
                    break;
                case 2:
                    imgEscolhida=2;
                    bt1.setColorFilter(Color.argb(215, 255, 255, 255));
                    bt2.setColorFilter(Color.argb(0, 255, 255, 255));
                    bt3.setColorFilter(Color.argb(215, 255, 255, 255));
                    bt4.setColorFilter(Color.argb(215, 255, 255, 255));
                    break;
                case 3:
                    imgEscolhida=3;
                    bt1.setColorFilter(Color.argb(215, 255, 255, 255));
                    bt2.setColorFilter(Color.argb(215, 255, 255, 255));
                    bt3.setColorFilter(Color.argb(0, 255, 255, 255));
                    bt4.setColorFilter(Color.argb(215, 255, 255, 255));
                    break;
                case 4:
                    imgEscolhida=4;
                    bt1.setColorFilter(Color.argb(215, 255, 255, 255));
                    bt2.setColorFilter(Color.argb(215, 255, 255, 255));
                    bt3.setColorFilter(Color.argb(215, 255, 255, 255));
                    bt4.setColorFilter(Color.argb(0, 255, 255, 255));
                    break;
            }
        }
    }

    public void clickBtn1(View v){
        imgEscolhida=1;
        bt1.setColorFilter(Color.argb(0, 255, 255, 255));
        bt2.setColorFilter(Color.argb(225, 255, 255, 255));
        bt3.setColorFilter(Color.argb(225, 255, 255, 255));
        bt4.setColorFilter(Color.argb(225, 255, 255, 255));
    }
    public void clickBtn2(View v){
        imgEscolhida=2;
        bt1.setColorFilter(Color.argb(215, 255, 255, 255));
        bt2.setColorFilter(Color.argb(0, 255, 255, 255));
        bt3.setColorFilter(Color.argb(215, 255, 255, 255));
        bt4.setColorFilter(Color.argb(215, 255, 255, 255));
    }
    public void clickBtn3(View v){
        imgEscolhida=3;
        bt1.setColorFilter(Color.argb(215, 255, 255, 255));
        bt2.setColorFilter(Color.argb(215, 255, 255, 255));
        bt3.setColorFilter(Color.argb(0, 255, 255, 255));
        bt4.setColorFilter(Color.argb(215, 255, 255, 255));
    }
    public void clickBtn4(View v){
        imgEscolhida=4;
        bt1.setColorFilter(Color.argb(215, 255, 255, 255));
        bt2.setColorFilter(Color.argb(215, 255, 255, 255));
        bt3.setColorFilter(Color.argb(215, 255, 255, 255));
        bt4.setColorFilter(Color.argb(0, 255, 255, 255));
    }
    public void mudaSwitch(View v){
        if(chave!=null){
            if(chave.isChecked()){
                horarioInicial.setEnabled(false);
                horarioFinal.setEnabled(false);
            }
            else{
                horarioInicial.setEnabled(true);
                horarioFinal.setEnabled(true);
            }
        }
    }
    public void mudaForma(View v){
        if(alarme.isChecked()){
            modoAviso = Local.ALARME;
            Log.e("mudança na forma","alarme");
        }
        else{
            modoAviso = Local.NOTIFICACAO;
            Log.e("mudança na forma","notificacao");
        }
    }

    public void mudaHorarioFinal(View v){
        AlertDialog.Builder construtorDaTela = new AlertDialog.Builder(this);
        construtorDaTela.setTitle("Horário de Término");
        final TimePicker seletorDeTempo = new TimePicker(this);
        seletorDeTempo.setIs24HourView(true);
        construtorDaTela.setView(seletorDeTempo);
        construtorDaTela.setCancelable(false);

        construtorDaTela.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //to usando esses pq se nao a api 15 nao aceita, se nao seriam getHour e getMinute();
                int horas = seletorDeTempo.getCurrentHour();
                int minutos = seletorDeTempo.getCurrentMinute();
                FinalEscolhido = horas+":"+minutos;
                horarioFinal.setText(FinalEscolhido);
                dialog.cancel();
            }
        });

        construtorDaTela.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = construtorDaTela.create();
        alertDialog.show();
    }

    public void mudaHorarioInicial(View v){
        AlertDialog.Builder construtorDaTela = new AlertDialog.Builder(this);
        construtorDaTela.setTitle("Horário de Início");
        final TimePicker seletorDeTempoInicial = new TimePicker(this);
        seletorDeTempoInicial.setIs24HourView(true);
        construtorDaTela.setView(seletorDeTempoInicial);
        construtorDaTela.setCancelable(false);

        construtorDaTela.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //to usando esses pq se nao a api 15 nao aceita, se nao seriam getHour e getMinute();
                int horas = seletorDeTempoInicial.getCurrentHour();
                int minutos = seletorDeTempoInicial.getCurrentMinute();
                InicioEscolhido = horas+":"+minutos;
                horarioInicial.setText(InicioEscolhido);
                dialog.cancel();
            }
        });

        construtorDaTela.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = construtorDaTela.create();
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_local, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BD bd = new BD(this);
        int id = item.getItemId();
        if (id == R.id.salvarNovoLocal) {
            Log.e("clicou no menuzim","haha");
            if(getIntent().getSerializableExtra("local")==null){
                localAPersistir = new Local();
                localAPersistir.setAtivo(Local.VERDADEIRO);
                localAPersistir.setAviso(modoAviso);
                localAPersistir.setImagem(imgEscolhida);
                localAPersistir.setFavorito(Local.VERDADEIRO);
                localAPersistir.setLatitude(4151);
                localAPersistir.setLongitude(4523);
                localAPersistir.setNome(tituloCaixa.getEditableText().toString());
                localAPersistir.setRaio(412);
                if(!chave.isChecked()){
                    localAPersistir.setTempo(InicioEscolhido+";"+FinalEscolhido);
                }
                else{
                    localAPersistir.setTempo("--");
                }
                localAPersistir.setTexto(recadoCaixa.getEditableText().toString());
                try {
                    bd.adicionar(localAPersistir);
                    getIntent().putExtra("mensagemPersistencia", "sa");
                    setResult(2,getIntent());
                    finish();
                }
                catch (SQLException e){
                    CoordinatorLayout clanl = (CoordinatorLayout)findViewById(R.id.coordinatorLayout_activity_new_local);
                    Snackbar barra = Snackbar.make(clanl, R.string.erro_persistencia_adicionar, Snackbar.LENGTH_LONG);
                    barra.show();
                }
            }
            else{
                Log.e("obs:","se nao ta nulo, é pq vai ser editado aqui ne amigao");
                localAPersistir = (Local)getIntent().getSerializableExtra("local");
                //colocar aqui bascimanete a mesma coisa la de cima, a diferenca eh que o item que veio pra cá ja tem um id pra persistir no BD
                localAPersistir.setAtivo(Local.VERDADEIRO);
                localAPersistir.setAviso(modoAviso);
                localAPersistir.setImagem(imgEscolhida);
                localAPersistir.setFavorito(Local.VERDADEIRO);
                localAPersistir.setLatitude(4151);
                localAPersistir.setLongitude(4523);
                localAPersistir.setNome(tituloCaixa.getEditableText().toString());
                localAPersistir.setRaio(412);
                if(!chave.isChecked()){
                    localAPersistir.setTempo(InicioEscolhido+";"+FinalEscolhido);
                }
                else{
                    localAPersistir.setTempo("--");
                }
                localAPersistir.setTexto(recadoCaixa.getEditableText().toString());

                boolean foi = bd.atualizar(localAPersistir);

                if(foi){
                    getIntent().putExtra("mensagemPersistencia", "se");
                    setResult(2,getIntent());
                    finish();
                }else{
                    CoordinatorLayout clanl = (CoordinatorLayout)findViewById(R.id.coordinatorLayout_activity_new_local);
                    Snackbar barra = Snackbar.make(clanl, R.string.erro_persistencia_editar, Snackbar.LENGTH_LONG);
                    barra.show();
                }

                Log.e("teste: ", bd.buscar(tituloCaixa.getEditableText().toString()).getNome());
            }
        } else {
            finish();
        }
        return true;
    }
}
