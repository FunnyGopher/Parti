package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.model.Invitation;

/**
 * Created by FunnyGopher
 */
public class InvitationDao extends AbstractCupboardDao<Invitation> {

    public InvitationDao(Context context) {
        super(Invitation.class, context);
    }

    @Override
    public Invitation create(Invitation entity) {
        return super.create(entity);
    }

    @Override
    public Invitation get(Long id) {
        return super.get(id);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    public Invitation update(Invitation entity) {
        return super.update(entity);
    }
}
