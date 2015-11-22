package com.github.funnygopher.parti.dao;

import android.util.Log;

import com.github.funnygopher.parti.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Kyle on 11/21/2015.
 */
public class EventDAO implements IDAO<Event> {
    @Override
    public Event create(final Event entity) {
        CreateEventTask task = new CreateEventTask(entity);
        task.setOnResponseListener(new CreateEventTask.OnCreateEvent() {
            @Override
            public void onCreateEvent(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Long id = jsonObject.getLong("id");
                    entity.setId(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("EventDAO#create", e.toString());
                }
            }
        });
        task.execute();

        return entity;
    }

    @Override
    public Event get(Long id) {
        final Event event = new Event();

        GetEventTask task = new GetEventTask(id);
        task.setOnResponseListener(new GetEventTask.OnGetEvent() {
            @Override
            public void onGetEvent(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray result = json.getJSONArray("result");
                    JSONObject eventJson = result.getJSONObject(0);

                    event.setId(eventJson.getLong("id"));
                    event.setName(eventJson.getString(Event.NAME_KEY));
                    event.setDescription(eventJson.getString(Event.DESC_KEY));
                    event.setHost(eventJson.getString(Event.HOST_KEY));
                    event.setAdditionalInfo(eventJson.getString(Event.ADDITIONAL_INFO_KEY));
                    event.setStartTime(Calendar.getInstance());
                    event.setEndTime(Calendar.getInstance());
                    event.setLongitude(eventJson.getDouble(Event.LONGITUDE_KEY));
                    event.setLatitude(eventJson.getDouble(Event.LATITUDE_KEY));
                    event.setAttending(eventJson.getInt(Event.ATTENDING_KEY));
                    event.setDeclined(eventJson.getInt(Event.DECLINED_KEY));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("EventDAO#get", e.toString());
                }
            }
        });
        task.execute();

        return event;
    }

    @Override
    public Event update(Event entity) {
        UpdateEventTask task = new UpdateEventTask(entity);
        task.setOnResponseListener(new UpdateEventTask.OnUpdateEvent() {
            @Override
            public void onUpdateEvent(String response) {
                Log.i("EventDAO#update", response);
            }
        });
        task.execute();

        return entity;
    }

    @Override
    public void delete(Long id) {
        DeleteEventTask task = new DeleteEventTask(id);
        task.setOnResponseListener(new DeleteEventTask.OnDeleteEvent() {
            @Override
            public void onDeleteEvent(String response) {
                Log.i("EventDAO#delete", response);
            }
        });
        task.execute();
    }
}
