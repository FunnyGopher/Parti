package com.github.funnygopher.parti.event;

public class Event {
    protected String name;
    protected String host;
    protected String location;
    protected String startTime;
    protected String endTime;
    protected String totalRSVP;
    protected String requirements;
    protected String description;

    public Event(String name, String host) {
        this.name = name;
        this.host = host;
    }
}
