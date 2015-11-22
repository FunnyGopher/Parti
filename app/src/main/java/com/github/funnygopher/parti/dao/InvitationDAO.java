package com.github.funnygopher.parti.dao;

import android.content.Context;

import com.github.funnygopher.parti.invitation.Invitation;

/**
 * Created by kylhunts on 11/21/2015.
 */
public class InvitationDAO extends AbstractCupboardDAO<Invitation> {

    public InvitationDAO(Context context) {
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
