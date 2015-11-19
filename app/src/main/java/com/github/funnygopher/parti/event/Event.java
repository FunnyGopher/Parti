package com.github.funnygopher.parti.event;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Event {

    public static final String NAME_KEY = "name";
    public static final String DESC_KEY = "description";
    public static final String HOST_KEY = "host";
    public static final String STARTTIME_KEY = "starttime";
    public static final String ENDTIME_KEY = "endtime";
    public static final String ADDITIONAL_INFO_KEY = "additional_info";
    public static final String LONGITUDE_KEY = "longitude";
    public static final String LATITUDE_KEY = "latitude";
    public static final String ACCEPTED_KEY = "accepted";
    public static final String DECLINED_KEY = "declined";

    private String name;
    private String description;
    private String host;

    private Calendar startTime;
    private Calendar endTime;

    private String additionalInfo;
    private double longitude;
    private double latitude;

    private int accepted;
    private int declined;

    public Event(String name, String host, String description, String additionalInfo, Calendar startTime, Calendar endTime) {
        this.name = name;
        this.description = description;
        this.host = host;
        this.additionalInfo = additionalInfo;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHost() {
        return host;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getAccepted() {
        return accepted;
    }

    public int getDeclined() {
        return declined;
    }
    
    public String toJSON() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(NAME_KEY, name);
            jsonObject.put(HOST_KEY, host);
            jsonObject.put(DESC_KEY, description);
            jsonObject.put(ADDITIONAL_INFO_KEY, additionalInfo);
            jsonObject.put(LONGITUDE_KEY, Double.toString(longitude));
            jsonObject.put(LATITUDE_KEY, Double.toString(latitude));
            jsonObject.put(ACCEPTED_KEY, Integer.toString(accepted));
            jsonObject.put(DECLINED_KEY, Integer.toString(declined));
            Log.i("Event#toJSON", jsonObject.toString());
            return jsonObject.toString();
        } catch(JSONException e) {
            e.printStackTrace();
            Log.e("Event#toJSON", e.toString());
        }
        return null;
    }
}
