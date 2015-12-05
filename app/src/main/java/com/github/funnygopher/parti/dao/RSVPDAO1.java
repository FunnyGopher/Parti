package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.model.RSVP1;

/**
 * Created by FunnyGopher
 */
public class RSVPDAO1 extends AbstractCupboardDAO1<RSVP1> {

    public RSVPDAO1(Context context) {
        super(RSVP1.class, context);
    }

    @Override
    public RSVP1 create(RSVP1 entity) {
        return super.create(entity);
    }

    @Override
    public RSVP1 get(Long id) {
        return super.get(id);
    }

    @Override
    public RSVP1 update(RSVP1 entity) {
        return super.update(entity);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }
}
