package com.example.tripa;

import java.sql.Time;
import java.util.Date;

public class Model {
    String tripid;
    String trip_name;
    String trip_desc;
    String trip_from;
    String trip_to;
    String trip_type;
    String trip_date;
    String trip_time;
    String trip_notes;

    public Model(String trip_name, String trip_desc, String trip_from, String trip_to, String trip_type, String trip_date, String trip_time,String tripid,String trip_notes) {
        this.trip_name = trip_name;
        this.trip_desc = trip_desc;
        this.trip_from = trip_from;
        this.trip_to = trip_to;
        this.trip_type = trip_type;
        this.trip_date = trip_date;
        this.trip_time = trip_time;
        this.tripid=tripid;
        this.trip_notes=trip_notes;
    }

    public Model() {
    }

    public String getTrip_notes() {
        return trip_notes;
    }

    public void setTrip_notes(String trip_notes) {
        this.trip_notes = trip_notes;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_desc() {
        return trip_desc;
    }

    public void setTrip_desc(String trip_desc) {
        this.trip_desc = trip_desc;
    }

    public String getTrip_from() {
        return trip_from;
    }

    public void setTrip_from(String trip_from) {
        this.trip_from = trip_from;
    }

    public String getTrip_to() {
        return trip_to;
    }

    public void setTrip_to(String trip_to) {
        this.trip_to = trip_to;
    }

    public String getTrip_type() {
        return trip_type;
    }

    public void setTrip_type(String trip_type) {
        this.trip_type = trip_type;
    }

    public String getTrip_date() {
        return trip_date;
    }

    public void setTrip_date(String trip_date) {
        this.trip_date = trip_date;
    }

    public String getTrip_time() {
        return trip_time;
    }

    public void setTrip_time(String trip_time) {
        this.trip_time = trip_time;
    }

    public String getTripid() {
        return tripid;
    }

    public void setTripid(String tripid) {
        this.tripid = tripid;
    }
}
