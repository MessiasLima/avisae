package br.ufc.caps.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sergio Marinho on 31/05/2016.
 */
public class NucleoBD extends SQLiteOpenHelper{
    private static final String NOME_BD = "bancoso_gps";
    private static final int VERSAO_BD = 1;

    public NucleoBD(Context ctx){
        super(ctx,NOME_BD,null,VERSAO_BD);//se o banco existir > ele só abre | se nao existir: ele cria > isso se o bd nao existe, chama o metodo onCreate |||||||||||||||| se a versao for outra.. ele chama o metodo onUpgrade
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table local(id integer primary key autoincrement, aviso integer not null, tempo text not null, nome text unique not null, texto text not null, ativo integer not null, favorito integer not null, raio real not null, latitude real not null, longitude real not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table local;");
        onCreate(db);
    }
}
