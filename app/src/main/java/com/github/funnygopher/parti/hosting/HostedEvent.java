package com.github.funnygopher.parti.hosting;

/**
 * Created by Kyle on 11/21/2015.
 */

import com.github.funnygopher.parti.dao.IEntity;

/***
 * This class acts as a schema for a bridge table with the Events table.
 */
public class HostedEvent implements IEntity {

    private Long _id;
    private Long eventId;

    public HostedEvent(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
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
