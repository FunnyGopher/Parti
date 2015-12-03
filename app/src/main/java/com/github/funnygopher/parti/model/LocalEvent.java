package com.github.funnygopher.parti.model;

import com.github.funnygopher.parti.dao.IEntity;

import java.util.Calendar;

/***
 * This class acts as a schema for a bridge table with the Events table on the remote database.
 */
public class LocalEvent implements IEntity {

    private Long _id; // Local id for Cupboard API
    private Long remoteId = -1L; // Remote id on remote database
    private String name;
    private String host;
    private String description;
    private String additionalInfo;

    // TODO: Have the calendars represented as strings or something

    private double longitude;
    private double latitude;

    private int attending;
    private int declined;

    public LocalEvent() {}

    public LocalEvent(Event event) {
        remoteId = event.getId();
        name = event.getName();
        host = event.getHost();
        description = event.getDescription();
        additionalInfo = event.getAdditionalInfo();
        // TODO: Have the calendars represented as strings or something
        longitude = event.getLongitude();
        latitude = event.getLatitude();
        attending = event.getAttending();
        declined = event.getDeclined();
    }

    // TODO: Replace Calendar.getInstance() with conversions to the save dates and times
    public Event toEvent() {
        Event event = new Event(name, host, description, additionalInfo, Calendar.getInstance(), Calendar.getInstance(), longitude, latitude, attending, declined);
        event.setLocalId(_id);
        event.setId(remoteId);
        return event;
    }

    @Override
    public Long getId() {
        return _id;
    }

    @Override
    public void setId(Long id) {
        _id = id;
    }
}
