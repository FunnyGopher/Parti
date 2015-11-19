package com.github.funnygopher.parti.invitation;

/**
 * Created by Kyle on 11/19/2015.
 */

/***
 * This class acts as a schema for a bridge table with the Events table.
 */
public class Invitation {

    private Long _id;
    private Long eventId;

    public Invitation(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }
}
