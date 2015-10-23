package com.github.funnygopher.parti.event;

import java.util.Calendar;
import java.util.Date;

public class Event {
    private String name;
    private String description;
    private String host;
    private Calendar startTime;
    private Calendar endTime;

    private String requirements;
    private String address;
    private String totalRSVP;

    private boolean rsvp;

    public Event(String name, String host, String description, String requirements, Calendar startTime, Calendar endTime) {
        this.name = name;
        this.description = description;
        this.host = host;
        this.requirements = requirements;
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

    public String getRequirements() {
        return requirements;
    }

    public boolean isRsvp() {
        return rsvp;
    }

    public void rsvp() {
        rsvp = true;
    }

    public void cancelRsvp() {
        rsvp = false;
    }
}
