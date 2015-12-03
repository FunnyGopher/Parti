package com.github.funnygopher.parti.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.github.funnygopher.parti.dao.IEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Event implements IEntity, Parcelable {

    public static final String EVENT = "event";

    public static final String REMOTE_ID_KEY = "id";
    public static final String LOCAL_ID_KEY = "local_id";
    private static final String NAME_KEY = "name";
    private static final String HOST_KEY = "host";
    private static final String DESC_KEY = "description";
    private static final String STARTTIME_KEY = "starttime";
    private static final String ENDTIME_KEY = "endtime";
    private static final String ADDITIONAL_INFO_KEY = "additional_info";
    private static final String LONGITUDE_KEY = "longitude";
    private static final String LATITUDE_KEY = "latitude";
    private static final String ATTENDING_KEY = "attending";
    private static final String DECLINED_KEY = "declined";

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {

        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private Long remoteId = -1L; // Remote id on remote database
    private Long _id = -1L; // Local id for Cupboard API
    private String name;
    private String host;
    private String description;
    private String additionalInfo;

    private Calendar startTime;
    private Calendar endTime;

    private double longitude;
    private double latitude;

    private int attending;
    private int declined;

    public Event() {}

    public Event(String name, String host, String description, String additionalInfo, Calendar startTime, Calendar endTime, double longitude, double latitude, int attending, int declined) {
        this.name = name;
        this.host = host;
        this.description = description;
        this.additionalInfo = additionalInfo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.attending = attending;
        this.declined = declined;
    }

    // Create and Event from a json file
    public Event(JSONObject json) throws JSONException {
        remoteId = json.getLong(REMOTE_ID_KEY);
        name = json.getString(NAME_KEY);
        host = json.getString(HOST_KEY);
        description = json.getString(DESC_KEY);
        additionalInfo = json.getString(ADDITIONAL_INFO_KEY);
        startTime = Calendar.getInstance(); // TODO: Read startTime from JSON
        endTime = Calendar.getInstance(); // TODO: Read endTime from JSON
        longitude = json.getDouble(LONGITUDE_KEY);
        latitude = json.getDouble(LATITUDE_KEY);
        attending = json.getInt(ATTENDING_KEY);
        declined = json.getInt(DECLINED_KEY);
    }

    // Create an event from a parcel
    public Event(Parcel parcel) {
        remoteId = parcel.readLong();
        _id = parcel.readLong();
        name = parcel.readString();
        host = parcel.readString();
        description = parcel.readString();
        additionalInfo = parcel.readString();
        startTime = Calendar.getInstance(); // TODO: Read startTime from JSON
        endTime = Calendar.getInstance(); // TODO: Read endTime from JSON
        longitude = parcel.readDouble();
        latitude = parcel.readDouble();
        attending = parcel.readInt();
        declined = parcel.readInt();
    }

    @Override
    public Long getId() {
        return remoteId;
    }

    @Override
    public void setId(Long id) {
        remoteId = id;
    }

    public Long getLocalId() {
        return _id;
    }

    public void setLocalId(Long id) {
        _id = remoteId;
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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
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
            jsonObject.put(REMOTE_ID_KEY, remoteId);
            jsonObject.put(LOCAL_ID_KEY, _id);
            jsonObject.put(NAME_KEY, name);
            jsonObject.put(HOST_KEY, host);
            jsonObject.put(DESC_KEY, description);
            jsonObject.put(ADDITIONAL_INFO_KEY, additionalInfo);
            // TODO: Add startTime to JSON
            // TODO: Add endTime to JSON
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(remoteId);
        dest.writeLong(_id);
        dest.writeString(name);
        dest.writeString(host);
        dest.writeString(description);
        dest.writeString(additionalInfo);
        // TODO: Add startTime to Parcel
        // TODO: Add endTime to Parcel
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeInt(attending);
        dest.writeInt(declined);
    }
}
