package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.model.HostedEvent;

import java.util.List;

/**
 * Created by FunnyGopher
 */
public class HostedEventDAO extends AbstractCupboardDAO<HostedEvent> {

    public HostedEventDAO(Context context) {
        super(HostedEvent.class, context);
    }

    @Override
    public HostedEvent create(HostedEvent entity) {
        return super.create(entity);
    }

    @Override
    public HostedEvent get(Long id) {
        return super.get(id);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    public HostedEvent update(HostedEvent entity) {
        return super.update(entity);
    }
}
