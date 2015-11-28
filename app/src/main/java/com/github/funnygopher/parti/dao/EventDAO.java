package com.github.funnygopher.parti.dao;

import com.github.funnygopher.parti.dao.tasks.CreateEventTask;
import com.github.funnygopher.parti.dao.tasks.DeleteEventTask;
import com.github.funnygopher.parti.dao.tasks.GetEventTask;
import com.github.funnygopher.parti.dao.tasks.UpdateEventTask;
import com.github.funnygopher.parti.model.Event;

/**
 * Created by Kyle on 11/21/2015.
 */
public class EventDAO {

    public void create(Event entity, CreateEventTask.OnCreateEventListener callback) {
        CreateEventTask task = new CreateEventTask(entity);
        task.setOnCreateEventListener(callback);
        task.execute();
    }

    public void get(Long id, GetEventTask.OnGetEventListener callback) {
        GetEventTask task = new GetEventTask(id);
        task.setOnGetEventListener(callback);
        task.execute();
    }

    public void update(Event entity, UpdateEventTask.OnUpdateEventListener callback) {
        UpdateEventTask task = new UpdateEventTask(entity);
        task.setOnUpdateEventListener(callback);
        task.execute();
    }

    public void delete(Long id, DeleteEventTask.OnDeleteEventListener callback) {
        DeleteEventTask task = new DeleteEventTask(id);
        task.setOnDeleteEventListener(callback);
        task.execute();
    }
}
