package com.hossam.devloper.meplace.DataEncapsulation;

/**
 * Created by hossam on 9/21/16.
 */
public class EncapServices {

    private String userId;

    private double userSpeed;
    private double latitude;
    private double longitude;
    private boolean userAlarm;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public EncapServices() {

    }

    public EncapServices(String userId, double longitude, double latitude, double userSpeed) {
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.userSpeed = userSpeed;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getUserSpeed() {
        return userSpeed;
    }

    public void setUserSpeed(double userSpeed) {
        this.userSpeed = userSpeed;
    }

    public boolean isUserAlarm() {
        return userAlarm;
    }

    public void setUserAlarm(boolean userAlarm) {
        this.userAlarm = userAlarm;
    }

    public double getLongitude() {
        return longitude;
    }

}
