package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.model.LocalEvent;

public class LocalEventDao extends AbstractCupboardDao<LocalEvent> {

    public LocalEventDao(Context context) {
        super(LocalEvent.class, context);
    }
}
