package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.model.Rsvp;

/**
 * Created by FunnyGopher
 */
public class RsvpDao extends AbstractCupboardDao<Rsvp> {

    public RsvpDao(Context context) {
        super(Rsvp.class, context);
    }

    @Override
    public Rsvp create(Rsvp entity) {
        return super.create(entity);
    }

    @Override
    public Rsvp get(Long id) {
        return super.get(id);
    }

    @Override
    public Rsvp update(Rsvp entity) {
        return super.update(entity);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }
}
