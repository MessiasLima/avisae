package br.ufc.caps.activity;

import android.support.design.widget.CoordinatorLayout;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import br.ufc.caps.R;
import br.ufc.caps.database.BD;
import br.ufc.caps.geofence.Local;
import br.ufc.caps.recyclerView.LocalCustomAdapter;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CoordinatorLayout cl;
    private BD dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cl= (CoordinatorLayout)findViewById(R.id.coordinatorLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivityForResult(new Intent(MainActivity.this,NewLocalActivity.class),2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            String mensagem=data.getStringExtra("mensagemPersistencia");
            if(mensagem!=null){
                if(mensagem.equals("sa")){
                    Snackbar barra = Snackbar.make(cl, R.string.sucesso_persistencia_adicionar, Snackbar.LENGTH_LONG);
                    barra.show();
                }
                else if(mensagem.equals("se")){
                    Snackbar barra = Snackbar.make(cl, R.string.sucesso_persistencia_editar, Snackbar.LENGTH_LONG);
                    barra.show();
                }
                //nao tem else, vai cair num dos dois, coloquei a comparacao aqui pra o codigo ficar mais claro
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (recyclerView ==null){
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        }

        if(dataBase ==null){
            dataBase = new BD(this);
        }
        // Estou adicionando esse dois lugarem manualmente só pra fins de teste
        // Quando terminar o periodo de desenvolvimento, apagar essas linhas

        Local local = new Local();
        local.setAviso(Local.ALARME);
        local.setTempo("15;17");
        local.setNome("Residencia Universitaria");
        local.setTexto("Testing... 123");
        local.setFavorito(Local.FALSO);
        local.setAtivo(Local.VERDADEIRO);
        local.setRaio(60f);
        local.setLatitude(-3.739984d);
        local.setLongitude(-38.569949d);
        local.setImagem(1);

        Local local2 = new Local();
        local2.setAviso(Local.ALARME);
        local2.setTempo("15;17");
        local2.setNome("Seara da Ciencia");
        local2.setTexto("Testing... 321");
        local2.setFavorito(Local.FALSO);
        local2.setAtivo(Local.VERDADEIRO);
        local2.setRaio(60f);
        local2.setLatitude(-3.739984d);
        local2.setLongitude(-38.569949d);
        local2.setImagem(2);

        mostraCardsNaTela();
    }

    public void mostraCardsNaTela(){
        List<Local> locals =  dataBase.buscar();
        LocalCustomAdapter customAdapter = new LocalCustomAdapter(this,locals);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
