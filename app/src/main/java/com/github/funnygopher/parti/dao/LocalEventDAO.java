package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.model.LocalEvent;

public class LocalEventDAO extends AbstractCupboardDAO<LocalEvent> {

    public LocalEventDAO(Context context) {
        super(LocalEvent.class, context);
    }
}
