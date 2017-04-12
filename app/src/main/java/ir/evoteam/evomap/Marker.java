package ir.evoteam.evomap;
/**
 * Created by root on 3/28/17.
 */


public class Marker {

    private double latitude;
    private double longitude;
    private String title;

    public Marker(double longitude, double latitude,String title) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.title=title;
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
}

