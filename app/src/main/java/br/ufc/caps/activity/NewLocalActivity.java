package br.ufc.caps.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.model.LatLng;

import br.ufc.caps.R;
import br.ufc.caps.database.BD;
import br.ufc.caps.geofence.Local;
import br.ufc.caps.util.FileUtil;

public class NewLocalActivity extends AppCompatActivity {

    private static final int PEGAR_LOCALIZACAO_REQUEST_CODE = 50;

    private ImageButton bt1;
    private ImageButton bt2;
    private ImageButton bt3;
    private ImageButton bt4;
    private Button horarioInicial;
    private Button escolherLocalButton;
    private Button horarioFinal;
    private Switch chave;
    private RadioButton alarme;
    private RadioButton notificacao;
    private EditText tituloCaixa;
    private EditText recadoCaixa;
    private int modoAviso;
    private int imgEscolhida;
    private String inicioEscolhido;
    private String finalEscolhido;
    private Local localAPersistir;
    private CoordinatorLayout clanl;
    private LatLng localizacaoSelecionada;
    private boolean editar = false;
    private ImageView mapThumbnail;
    private Bitmap mapThumbnailBitmap;
    private float raio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_local);
        ActionBar ac = getSupportActionBar();
        ac.setDefaultDisplayHomeAsUpEnabled(true);
        ac.setDisplayUseLogoEnabled(true);
        ac.setDisplayHomeAsUpEnabled(true);

        initComponents();

        if (getIntent().hasExtra(Local.KEY)) {
            localAPersistir = (Local) getIntent().getExtras().getSerializable(Local.KEY);
        }
        if (localAPersistir == null) {
            ac.setTitle(R.string.titulo_activity_local_adicionar);
            inicioEscolhido = "00:00";
            finalEscolhido = "00:00";
            // aqui to colocando alarme como o default
            alarme.setChecked(true);
            modoAviso = Local.ALARME;
            bt2.setColorFilter(Color.argb(225, 255, 255, 255));
            bt3.setColorFilter(Color.argb(225, 255, 255, 255));
            bt4.setColorFilter(Color.argb(225, 255, 255, 255));
            imgEscolhida = 1;
        } else {
            ac.setTitle(R.string.titulo_activity_local_editar);
            editar = true;
            raio=localAPersistir.getRaio();
            //local escolhido
            localizacaoSelecionada = new LatLng(localAPersistir.getLatitude(), localAPersistir.getLongitude());
            //titulo
            tituloCaixa.setText(localAPersistir.getNome());
            //texto resumo
            recadoCaixa.setText(localAPersistir.getTexto());
            iniciaConfiguracoesModoEditar();
        }

        AdView adView = (AdView) findViewById(R.id.new_local_adview);
        AdRequest adRequest = new AdRequest
                .Builder()
                //.addTestDevice("9B9BD4351E83CB69B20A3B4A951F3ADE")
                .build();
        adView.loadAd(adRequest);
    }

    private void initComponents() {
        chave = (Switch) this.findViewById(R.id.diaTodo);
        alarme = (RadioButton) this.findViewById(R.id.radioAlarme);
        notificacao = (RadioButton) this.findViewById(R.id.radioNotificacao);
        horarioInicial = (Button) this.findViewById(R.id.horarioInicial);
        horarioFinal = (Button) this.findViewById(R.id.horarioFinal);
        tituloCaixa = (EditText) this.findViewById(R.id.nomeLocal);
        recadoCaixa = (EditText) this.findViewById(R.id.textoRecado);
        bt1 = (ImageButton) this.findViewById(R.id.botaoIm1);
        bt2 = (ImageButton) this.findViewById(R.id.botaoIm2);
        bt3 = (ImageButton) this.findViewById(R.id.botaoIm3);
        bt4 = (ImageButton) this.findViewById(R.id.botaoIm4);
        mapThumbnail = (ImageView) findViewById(R.id.map_thumbnail);
        escolherLocalButton = (Button) findViewById(R.id.botaoEscolherLugar);
    }

    public void iniciaConfiguracoesModoEditar(){
        //horario
        if (localAPersistir.getTempo().equals("--")) {
            inicioEscolhido = "00:00";
            finalEscolhido = "00:00";
            horarioFinal.setText("00:00");
            horarioInicial.setText("00:00");
            horarioFinal.setEnabled(false);
            horarioInicial.setEnabled(false);
            chave.setChecked(true);
        } else {
            String[] aux = localAPersistir.getTempo().split(";");
            inicioEscolhido = aux[0];
            finalEscolhido = aux[1];
            horarioInicial.setText(inicioEscolhido);
            horarioFinal.setText(finalEscolhido);
        }
        //aviso
        if (Local.ALARME == localAPersistir.getAviso()) {
            alarme.setChecked(true);
            modoAviso = Local.ALARME;
        } else {
            notificacao.setChecked(true);
            modoAviso = Local.NOTIFICACAO;
        }
        //imagens
        switch (localAPersistir.getImagem()) {
            case 1:
                imgEscolhida = 1;
                bt1.setColorFilter(Color.argb(0, 255, 255, 255));
                bt2.setColorFilter(Color.argb(225, 255, 255, 255));
                bt3.setColorFilter(Color.argb(225, 255, 255, 255));
                bt4.setColorFilter(Color.argb(225, 255, 255, 255));
                break;
            case 2:
                imgEscolhida = 2;
                bt1.setColorFilter(Color.argb(215, 255, 255, 255));
                bt2.setColorFilter(Color.argb(0, 255, 255, 255));
                bt3.setColorFilter(Color.argb(215, 255, 255, 255));
                bt4.setColorFilter(Color.argb(215, 255, 255, 255));
                break;
            case 3:
                imgEscolhida = 3;
                bt1.setColorFilter(Color.argb(215, 255, 255, 255));
                bt2.setColorFilter(Color.argb(215, 255, 255, 255));
                bt3.setColorFilter(Color.argb(0, 255, 255, 255));
                bt4.setColorFilter(Color.argb(215, 255, 255, 255));
                break;
            case 4:
                imgEscolhida = 4;
                bt1.setColorFilter(Color.argb(215, 255, 255, 255));
                bt2.setColorFilter(Color.argb(215, 255, 255, 255));
                bt3.setColorFilter(Color.argb(215, 255, 255, 255));
                bt4.setColorFilter(Color.argb(0, 255, 255, 255));
                break;
        }

        //Thubnail do Mapa
        mapThumbnailBitmap = FileUtil.retrieveBitmap(localAPersistir.getNome(),this);
        setMapThumbnail(mapThumbnailBitmap);

    }

    public void clickBtn1(View v) {
        imgEscolhida = 1;
        bt1.setColorFilter(Color.argb(0, 255, 255, 255));
        bt2.setColorFilter(Color.argb(225, 255, 255, 255));
        bt3.setColorFilter(Color.argb(225, 255, 255, 255));
        bt4.setColorFilter(Color.argb(225, 255, 255, 255));
    }

    public void clickBtn2(View v) {
        imgEscolhida = 2;
        bt1.setColorFilter(Color.argb(215, 255, 255, 255));
        bt2.setColorFilter(Color.argb(0, 255, 255, 255));
        bt3.setColorFilter(Color.argb(215, 255, 255, 255));
        bt4.setColorFilter(Color.argb(215, 255, 255, 255));
    }

    public void clickBtn3(View v) {
        imgEscolhida = 3;
        bt1.setColorFilter(Color.argb(215, 255, 255, 255));
        bt2.setColorFilter(Color.argb(215, 255, 255, 255));
        bt3.setColorFilter(Color.argb(0, 255, 255, 255));
        bt4.setColorFilter(Color.argb(215, 255, 255, 255));
    }

    public void clickBtn4(View v) {
        imgEscolhida = 4;
        bt1.setColorFilter(Color.argb(215, 255, 255, 255));
        bt2.setColorFilter(Color.argb(215, 255, 255, 255));
        bt3.setColorFilter(Color.argb(215, 255, 255, 255));
        bt4.setColorFilter(Color.argb(0, 255, 255, 255));
    }

    public void mudaSwitch(View v) {
        if (chave != null) {
            if (chave.isChecked()) {
                horarioInicial.setEnabled(false);
                horarioFinal.setEnabled(false);
            } else {
                horarioInicial.setEnabled(true);
                horarioFinal.setEnabled(true);
            }
        }
    }

    public void mudaForma(View v) {
        if (alarme.isChecked()) {
            modoAviso = Local.ALARME;
            Log.e("mudança na forma", "alarme");
        } else {
            modoAviso = Local.NOTIFICACAO;
            Log.e("mudança na forma", "notificacao");
        }
    }

    public void mudaHorarioFinal(View v) {
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
                String horasStr;
                String minutosStr;
                if (horas < 10) {
                    horasStr = "0" + horas;
                } else {
                    horasStr = "" + horas;
                }
                if (minutos < 10) {
                    minutosStr = "0" + minutos;
                } else {
                    minutosStr = "" + minutos;
                }
                finalEscolhido = horasStr + ":" + minutosStr;
                horarioFinal.setText(finalEscolhido);
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

    public void mudaHorarioInicial(View v) {
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
                String horasStr;
                String minutosStr;
                if (horas < 10) {
                    horasStr = "0" + horas;
                } else {
                    horasStr = "" + horas;
                }
                if (minutos < 10) {
                    minutosStr = "0" + minutos;
                } else {
                    minutosStr = "" + minutos;
                }
                inicioEscolhido = horasStr + ":" + minutosStr;
                horarioInicial.setText(inicioEscolhido);
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

    public void escolherLocal(View v) {
        Intent i = new Intent(this, EscolherLocalActivity.class);
        if(editar){
            i.putExtra(Local.KEY_LOCALIZACAO,localAPersistir);
        }
        startActivityForResult(i, PEGAR_LOCALIZACAO_REQUEST_CODE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_local, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Adicionei a verificação antes de criar qualquer objeto

        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED, getIntent());
            finish();
        }

        if (item.getItemId() == R.id.salvarNovoLocal) {

            if (tituloCaixa.getEditableText().toString().trim().length() == 0 || localizacaoSelecionada == null) {
                Snackbar.make(tituloCaixa, R.string.erro_verifique_campos, Snackbar.LENGTH_LONG).show();
                return false;
            }

            //Sanvando Thubnail do mapa aqui
            if(!FileUtil.saveBitmap(mapThumbnailBitmap,tituloCaixa.getEditableText().toString(),this)){
                Snackbar.make(tituloCaixa, R.string.erro_salvar_thumbnail, Snackbar.LENGTH_LONG).show();
                return false;
            }

            BD bd = new BD(this);
            int id = item.getItemId();
            if (id == R.id.salvarNovoLocal) {
                if (!editar) {
                    localAPersistir = new Local();
                    localAPersistir.setAtivo(Local.VERDADEIRO);
                    localAPersistir.setAviso(modoAviso);
                    localAPersistir.setImagem(imgEscolhida);
                    localAPersistir.setFavorito(Local.VERDADEIRO);
                    localAPersistir.setLatitude(localizacaoSelecionada.latitude);
                    localAPersistir.setLongitude(localizacaoSelecionada.longitude);
                    localAPersistir.setNome(tituloCaixa.getEditableText().toString());

                    if(Float.isNaN(raio) || raio==0){//se por algum motivo tiver NaN, fica o padrao, ta aqui mais por precaucao
                        localAPersistir.setRaio(Local.RAIO_PADRAO);
                    }else{
                        localAPersistir.setRaio(raio);
                    }

                    if (!chave.isChecked()) {
                        localAPersistir.setTempo(inicioEscolhido + ";" + finalEscolhido);
                    } else {
                        localAPersistir.setTempo("--");
                    }
                    localAPersistir.setTexto(recadoCaixa.getEditableText().toString());
                    try {
                        if (tituloCaixa.getEditableText().toString().trim().equals("")) {
                            clanl = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_activity_new_local);
                            Snackbar barra = Snackbar.make(clanl, R.string.erro_nome_nao_valido, Snackbar.LENGTH_LONG);
                            barra.show();
                            return true;
                        }
                        bd.adicionar(localAPersistir);
                        getIntent().putExtra("mensagemPersistencia", "sa");
                        setResult(RESULT_OK, getIntent());
                        finish();
                    } catch (SQLException e) {
                        Snackbar barra = Snackbar.make(tituloCaixa, R.string.erro_persistencia_adicionar, Snackbar.LENGTH_LONG);
                        barra.show();
                    }
                } else {// se é pra editar, e nao adicionar
                    localAPersistir.setAtivo(Local.VERDADEIRO);
                    localAPersistir.setAviso(modoAviso);
                    localAPersistir.setImagem(imgEscolhida);
                    localAPersistir.setFavorito(Local.VERDADEIRO);
                    localAPersistir.setLatitude(localizacaoSelecionada.latitude);
                    localAPersistir.setLongitude(localizacaoSelecionada.longitude);
                    localAPersistir.setNome(tituloCaixa.getEditableText().toString());

                    if(Float.isNaN(raio) || raio==0){//se por algum motivo tiver NaN, fica o padrao, ta aqui mais por precaucao
                        localAPersistir.setRaio(Local.RAIO_PADRAO);
                    }else{
                        localAPersistir.setRaio(raio);
                    }

                    if (!chave.isChecked()) {
                        localAPersistir.setTempo(inicioEscolhido + ";" + finalEscolhido);
                    } else {
                        localAPersistir.setTempo("--");
                    }
                    localAPersistir.setTexto(recadoCaixa.getEditableText().toString());

                    //checando se o titulo ta nulo
                    if (tituloCaixa.getEditableText().toString().replace(" ", "").equals("")) {
                        clanl = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_activity_new_local);
                        Snackbar barra = Snackbar.make(clanl, R.string.erro_nome_nao_valido, Snackbar.LENGTH_LONG);
                        barra.show();
                        return true;
                    }
                    boolean foi = bd.atualizar(localAPersistir);

                    if (foi) {
                        getIntent().putExtra("mensagemPersistencia", "se");
                        setResult(RESULT_OK, getIntent());
                        finish();
                    } else {
                        Snackbar barra = Snackbar.make(clanl, R.string.erro_persistencia_editar, Snackbar.LENGTH_LONG);
                        barra.show();
                    }

                    Log.e("teste: ", bd.buscar(tituloCaixa.getEditableText().toString()).getNome());
                }
            } else {
                setResult(2, getIntent());
                finish();
            }
        }
        return true;
    }

    public void setMapThumbnail(Bitmap b) {
        mapThumbnail.setImageBitmap(b);
        escolherLocalButton.setHeight(200);
        escolherLocalButton.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //comentando aqui para lembrar de testar depois: se usario for editar, dai desistir, a latitude e longitude ficam nulas? bom verificar
        if (requestCode == PEGAR_LOCALIZACAO_REQUEST_CODE && resultCode == RESULT_OK) {
            localizacaoSelecionada = data.getExtras().getParcelable(Local.KEY_LOCALIZACAO);
            mapThumbnailBitmap = data.getExtras().getParcelable(Local.KEY_THUMBNAIL);
            raio = (float)data.getDoubleExtra(Local.KEY_RAIO,Local.RAIO_PADRAO);
            setMapThumbnail(mapThumbnailBitmap);
        }
    }

}