package com.github.funnygopher.parti.model;

/**
 * Created by FunnyGopher
 */

import com.github.funnygopher.parti.dao.IEntity;

/***
 * This class acts as a schema for a bridge table with the Events table.
 */
public class Rsvp implements IEntity {

    private Long _id;
    private Long eventId;
    private boolean attending;

    public Rsvp() {}

    public Rsvp(Long eventId, boolean attending) {
        this.eventId = eventId;
        this.attending = attending;
    }

    public Long getEventId() {
        return eventId;
    }

    public boolean isAttending() {
        return attending;
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
