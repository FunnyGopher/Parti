package com.github.funnygopher.parti.hosting;

/**
 * Created by Kyle on 11/21/2015.
 */

/***
 * This class acts as a schema for a bridge table with the Events table.
 */
public class HostedEvent {

    private Long _id;
    private Long eventId;

    public HostedEvent(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }
}
