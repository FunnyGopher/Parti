package com.github.funnygopher.parti.model;

import android.util.Log;

import com.github.funnygopher.parti.dao.IEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Event implements IEntity {

    public static final String NAME_KEY = "name";
    public static final String HOST_KEY = "host";
    public static final String DESC_KEY = "description";
    public static final String STARTTIME_KEY = "starttime";
    public static final String ENDTIME_KEY = "endtime";
    public static final String ADDITIONAL_INFO_KEY = "additional_info";
    public static final String LONGITUDE_KEY = "longitude";
    public static final String LATITUDE_KEY = "latitude";
    public static final String ATTENDING_KEY = "attending";
    public static final String DECLINED_KEY = "declined";

    private Long _id;
    private String name;
    private String host;
    private String description;

    private Calendar startTime;
    private Calendar endTime;

    private String additionalInfo;
    private double longitude;
    private double latitude;

    private int attending;
    private int declined;

    public Event() {}

    public Event(JSONObject eventJson) throws JSONException {
        setId(eventJson.getLong("id"));
        setName(eventJson.getString(NAME_KEY));
        setDescription(eventJson.getString(DESC_KEY));
        setHost(eventJson.getString(HOST_KEY));
        setAdditionalInfo(eventJson.getString(ADDITIONAL_INFO_KEY));
        setStartTime(Calendar.getInstance());
        setEndTime(Calendar.getInstance());
        setLongitude(eventJson.getDouble(LONGITUDE_KEY));
        setLatitude(eventJson.getDouble(LATITUDE_KEY));
        setAttending(eventJson.getInt(ATTENDING_KEY));
        setDeclined(eventJson.getInt(DECLINED_KEY));
    }

    public Event(String name, String host, String description, Calendar startTime, Calendar endTime, String additionalInfo, double longitude, double latitude, int attending, int declined) {
        this.name = name;
        this.host = host;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.additionalInfo = additionalInfo;
        this.longitude = longitude;
        this.attending = attending;
        this.latitude = latitude;
        this.declined = declined;
    }

    @Override
    public Long getId() {
        return _id;
    }

    @Override
    public void setId(Long id) {
        _id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getAttending() {
        return attending;
    }

    public void setAttending(int attending) {
        this.attending = attending;
    }

    public int getDeclined() {
        return declined;
    }

    public void setDeclined(int declined) {
        this.declined = declined;
    }

    public String toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", _id);
            jsonObject.put(NAME_KEY, name);
            jsonObject.put(HOST_KEY, host);
            jsonObject.put(DESC_KEY, description);
            jsonObject.put(ADDITIONAL_INFO_KEY, additionalInfo);
            jsonObject.put(LONGITUDE_KEY, Double.toString(longitude));
            jsonObject.put(LATITUDE_KEY, Double.toString(latitude));
            jsonObject.put(ATTENDING_KEY, Double.toString(attending));
            jsonObject.put(DECLINED_KEY, Double.toString(declined));
            Log.i("Event#toJSON", jsonObject.toString());
            return jsonObject.toString();
        } catch(JSONException e) {
            e.printStackTrace();
            Log.e("Event#toJSON", e.toString());
        }

        return new JSONObject().toString();
    }
}
