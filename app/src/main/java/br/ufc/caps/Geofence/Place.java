package br.ufc.caps.Geofence;

import com.google.android.gms.location.Geofence;

/**
 * Created by messias on 6/1/16.
 *
 * @author Messias Lima
 */
public class Place {

    public static final int FALSE = 0;
    public static final int TRUE = 1;
    public static final int TYPE_NOTIFICATION = 2;
    public static final int TYPE_ALARM = 3;

    private int id;
    private int warningType;
    private String name;
    private String tempo;
    private String text;
    private int favorite;
    private int enabled;// nao tem boolean no SQLite
    private float radius;
    private double latitude;
    private double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWarningType() {
        return warningType;
    }

    public void setWarningType(int warningType) {
        this.warningType = warningType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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
                .setRequestId(name)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setCircularRegion(latitude, longitude, radius)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }
}
