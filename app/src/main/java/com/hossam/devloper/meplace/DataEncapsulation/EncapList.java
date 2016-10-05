package com.hossam.devloper.meplace.DataEncapsulation;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by devloper on 10/1/16.
 */
public class EncapList {
    private double lat;
    private double lang;
    private String name;
    private Marker marker;

    public String getName() {
        return name;
    }

    public EncapList(double lat, double lang, String name) {
        this.lat = lat;
        this.lang = lang;
        this.name = name;
        this.marker = marker;
    }

    public EncapList(String name) {
        this.name = name;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EncapList() {

    }

    public EncapList(double lat, double lang) {
        this.lat = lat;
        this.lang = lang;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }
}
