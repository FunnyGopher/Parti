package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.model.LocalEvent;

public class LocalEventDAO1 extends AbstractCupboardDAO1<LocalEvent> {

    public LocalEventDAO1(Context context) {
        super(LocalEvent.class, context);
    }
}
