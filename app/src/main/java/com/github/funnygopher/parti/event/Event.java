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

    public Event(String name, String host, String location, String startTime, String endTime, String totalRSVP, String requirements, String description) {
        this.name = name;
        this.host = host;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalRSVP = totalRSVP;
        this.requirements = requirements;
        this.description = description;
    }
}
