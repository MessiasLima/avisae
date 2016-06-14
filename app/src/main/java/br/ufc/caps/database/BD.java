package br.ufc.caps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import br.ufc.caps.geofence.Local;

/**
 * Created by Sergio Marinho on 31/05/2016.
 * @author Sergio Marinho
 */
public class BD implements Serializable{
    private SQLiteDatabase bd;
    private Context ctx;
    public BD(Context ctx) {
        this.ctx = ctx;
    }
    private void abreConexao(){
        NucleoBD nbd = new NucleoBD(ctx);
        bd = nbd.getWritableDatabase();
    }
    private void fechaConexao(){
        bd.close();
    }
    public void adicionar(Local l){
        abreConexao();
        ContentValues linha = new ContentValues();
        linha.put("aviso",l.getAviso());//aviso, tempo, nome, texto, ativo, favorito, raio, latitude, longitude
        linha.put("tempo",l.getTempo());
        linha.put("nome",l.getNome());
        linha.put("texto",l.getTexto());
        linha.put("ativo",l.getAtivo());
        linha.put("favorito",l.getFavorito());
        linha.put("raio",l.getRaio());
        linha.put("latitude",l.getLatitude());
        linha.put("longitude",l.getLongitude());
        linha.put("imagem",l.getImagem());
        bd.insert("local",null, linha);// nome da tabela, uma variavel ai para caso especifico de insercao com todas as colunas nulas> vo ver isso melhor ,valores para a coluna
        fechaConexao();
    }
    public boolean atualizar(Local l){
        try{
            abreConexao();
            int alterados=0;
            ContentValues linha = new ContentValues();
            linha.put("aviso",l.getAviso());
            linha.put("nome",l.getNome());
            linha.put("tempo",l.getTempo());
            linha.put("texto",l.getTexto());
            linha.put("ativo",l.getAtivo());
            linha.put("favorito",l.getFavorito());
            linha.put("raio",l.getRaio());
            linha.put("latitude",l.getLatitude());
            linha.put("longitude",l.getLongitude());
            linha.put("imagem",l.getImagem());
            alterados=bd.update("local",linha,"id="+l.getId(),null);//tabela,valores a trocar,condicao, valores das condicoes
            fechaConexao();
            if(alterados!=0){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
    public boolean excluir(int id){
        try{
            abreConexao();
            int alterados=0;
            String strId=id+"";
            alterados = bd.delete("local","id=?",new String[]{(strId)});
            fechaConexao();
            if(alterados!=0){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
    public boolean excluir(Local l){
        try{
            abreConexao();
            int alterados=0;
            String strId=l.getId()+"";
            alterados = bd.delete("local","id=?",new String[]{(strId)});
            fechaConexao();
            if(alterados!=0){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
    public ArrayList<Local> buscar(){//esse é só exemplo, to retornando só o primeiro
        abreConexao();
        ArrayList<Local> retorno= new ArrayList<Local>();
        String[] colunas = new String[]{"id","aviso","tempo","nome","texto","ativo","favorito","raio","latitude","longitude","imagem"};
        Cursor cursor = bd.query("local", colunas,null,null,null,null,null);//quem estudou bd sabe oq sao esses null || esse cursor é tipo um resultset
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            Local l;
            //id, aviso, tempo, nome, texto, ativo, favorito, raio, latitude, longitude
            int id, aviso, imagem;
            String nome, tempo, texto;
            int favorito, ativo;
            float raio;
            double latitude, longitude;
            do{
                id = cursor.getInt(0);
                aviso = cursor.getInt(1);
                tempo = cursor.getString(2);
                nome = cursor.getString(3);
                texto = cursor.getString(4);
                ativo = cursor.getInt(5);
                favorito = cursor.getInt(6);
                raio = cursor.getFloat(7);
                latitude = cursor.getDouble(8);
                longitude = cursor.getDouble(9);
                imagem = cursor.getInt(10);
                l = new Local(id,aviso, nome, tempo, texto,favorito,ativo,raio,latitude,longitude,imagem);
                retorno.add(l);
            }while(cursor.moveToNext());
        }
        fechaConexao();
        return retorno;
    }
    public Local buscar(String nome){
        ArrayList<Local> todos = buscar();
        if(!todos.isEmpty()){
            for(Local l:todos){
                if(l.getNome().equals(nome)){
                    return l;
                }
            }
        }
        return null;
    }
    public Local buscar(int id){
        ArrayList<Local> todos = buscar();
        if(!todos.isEmpty()){
            for(Local l:todos){
                if(l.getId()==id){
                    return l;
                }
            }
        }
        return null;
    }
}