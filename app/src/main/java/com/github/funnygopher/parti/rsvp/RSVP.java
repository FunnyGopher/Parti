package com.github.funnygopher.parti.rsvp;

/**
 * Created by Kyle on 11/21/2015.
 */

/***
 * This class acts as a schema for a bridge table with the Events table.
 */
public class RSVP {

    private Long _id;
    private Long eventId;
    private boolean attending;

    public RSVP(Long eventId, boolean attending) {
        this.eventId = eventId;
        this.attending = attending;
    }

    public Long getEventId() {
        return eventId;
    }

    public boolean isAttending() {
        return attending;
    }
}
