package com.github.funnygopher.parti.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.funnygopher.parti.CupboardSQLiteHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Kyle on 11/21/2015.
 */
public abstract class AbstractCupboardDAO<E extends IEntity> implements IDAO<E> {

    protected Class<E> entityClass;
    protected CupboardSQLiteHelper mHelper;

    public AbstractCupboardDAO(Class<E> entityClass, Context context) {
        this.entityClass = entityClass;
        mHelper = new CupboardSQLiteHelper(context);
    }

    @Override
    public E create(E entity) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        cupboard().withDatabase(db).put(entity);
        return entity;
    }

    @Override
    public E get(Long id) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        E entity = cupboard().withDatabase(db).get(entityClass, id);
        return entity;
    }

    @Override
    public E update(E entity) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        cupboard().withDatabase(db).put(entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        cupboard().withDatabase(db).delete(entityClass);
    }
}
