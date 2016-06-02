package br.ufc.caps.geofence;

import com.google.android.gms.location.Geofence;

/**
 * Created by Sergio Marinho on 31/05/2016.
 */
public class Local {
    public static final int FALSO = 0;
    public static final int VERDADEIRO = 1;
    public static final int NOTIFICACAO = 2;
    public static final int ALARME = 3;

    private int id;
    private int aviso;
    private String nome;
    private String tempo;
    private String texto;
    private int favorito;// nao tem boolean no SQLite
    private int ativo;
    private float raio;
    private double latitude;
    private double longitude;

    public Local(int id, int aviso, String nome, String tempo, String texto, int favorito, int ativo, float raio, double latitude, double longitude) {
        this.id = id;
        this.aviso = aviso;
        this.nome = nome;
        this.tempo = tempo;
        this.texto = texto;
        this.favorito = favorito;
        this.ativo = ativo;
        this.raio = raio;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Local(int aviso, String nome, String tempo, String texto, int favorito, int ativo, float raio, double latitude, double longitude) {
        this.aviso = aviso;
        this.nome = nome;
        this.tempo = tempo;
        this.texto = texto;
        this.favorito = favorito;
        this.ativo = ativo;
        this.raio = raio;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAviso() {
        return aviso;
    }

    public void setAviso(int aviso) {
        this.aviso = aviso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public float getRaio() {
        return raio;
    }

    public void setRaio(float raio) {
        this.raio = raio;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Cria uma nova instancia de um objeto Geofence
     */
    public Geofence getGeofence() {
        return new Geofence.Builder()
                .setRequestId(nome)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setCircularRegion(latitude, longitude, raio)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }
}
