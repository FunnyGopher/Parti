package com.github.funnygopher.parti.dao;

import com.github.funnygopher.parti.dao.tasks.CreateEventTask;
import com.github.funnygopher.parti.dao.tasks.DeleteEventTask;
import com.github.funnygopher.parti.dao.tasks.GetEventTask;
import com.github.funnygopher.parti.dao.tasks.UpdateEventTask;
import com.github.funnygopher.parti.model.Event;

/**
 * Created by FunnyGopher
 */
public class EventDao {

    public void create(Event event) {
        CreateEventTask task = new CreateEventTask(event);
        task.execute();
    }

    public void create(Event event, CreateEventTask.OnCreateEventListener callback) {
        CreateEventTask task = new CreateEventTask(event);
        task.setOnCreateEventListener(callback);
        task.execute();
    }

    public void get(Long id) {
        GetEventTask task = new GetEventTask(id);
        task.execute();
    }

    public void get(Long id, GetEventTask.OnGetEventListener callback) {
        GetEventTask task = new GetEventTask(id);
        task.setOnGetEventListener(callback);
        task.execute();
    }

    public void update(Event event) {
        UpdateEventTask task = new UpdateEventTask(event);
        task.execute();
    }

    public void update(Event event, UpdateEventTask.OnUpdateEventListener callback) {
        UpdateEventTask task = new UpdateEventTask(event);
        task.setOnUpdateEventListener(callback);
        task.execute();
    }

    public void delete(Long id) {
        DeleteEventTask task = new DeleteEventTask(id);
        task.execute();
    }

    public void delete(Long id, DeleteEventTask.OnDeleteEventListener callback) {
        DeleteEventTask task = new DeleteEventTask(id);
        task.setOnDeleteEventListener(callback);
        task.execute();
    }
}
