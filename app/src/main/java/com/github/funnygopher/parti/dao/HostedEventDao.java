package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.model.HostedEvent;

/**
 * Created by FunnyGopher
 */
public class HostedEventDao extends AbstractCupboardDao<HostedEvent> {

    public HostedEventDao(Context context) {
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
