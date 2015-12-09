package com.github.funnygopher.parti.model;

import com.github.funnygopher.parti.dao.IEntity;
import com.github.funnygopher.parti.util.DateUtil;

/***
 * This class acts as a schema for a bridge table with the Events table on the remote database.
 */
public class LocalEvent implements IEntity {

    private Long _id; // Local id for Cupboard API
    private Long remoteId; // Remote id on remote database
    private String name;
    private String host;
    private String description;
    private String additionalInfo;

    private String startTime;
    private String endTime;

    private String address;

    private int attending;
    private int declined;

    public LocalEvent() {}

    public LocalEvent(Event event) {
        remoteId = event.getId();
        name = event.getName();
        host = event.getHost();
        description = event.getDescription();
        additionalInfo = event.getAdditionalInfo();
        startTime = event.getStartTimeString();
        endTime = event.getEndTimeString();
        address = event.getAddress();
        attending = event.getAttending();
        declined = event.getDeclined();
    }

    public void copy(Event event) {
        remoteId = event.getId();
        name = event.getName();
        host = event.getHost();
        description = event.getDescription();
        additionalInfo = event.getAdditionalInfo();
        startTime = event.getStartTimeString();
        endTime = event.getEndTimeString();
        address = event.getAddress();
        attending = event.getAttending();
        declined = event.getDeclined();
    }

    public Event toEvent() {
        Event event = new Event(name, host, description, additionalInfo,
                DateUtil.stringToCalendar(startTime), DateUtil.stringToCalendar(endTime),
                address, attending, declined);
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
