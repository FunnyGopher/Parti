package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.rsvp.RSVP;

/**
 * Created by Kyle on 11/21/2015.
 */
public class RSVPDAO extends AbstractCupboardDAO<RSVP> {

    public RSVPDAO(Context context) {
        super(RSVP.class, context);
    }

    @Override
    public RSVP create(RSVP entity) {
        return super.create(entity);
    }

    @Override
    public RSVP get(Long id) {
        return super.get(id);
    }

    @Override
    public RSVP update(RSVP entity) {
        return super.update(entity);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }
}
