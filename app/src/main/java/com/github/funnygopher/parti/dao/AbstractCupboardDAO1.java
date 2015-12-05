package com.github.funnygopher.parti.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by FunnyGopher
 */
public abstract class AbstractCupboardDAO1<E extends IEntity> implements IDAO1<E> {

    protected Class<E> entityClass;
    protected CupboardSQLiteHelper mHelper;

    public AbstractCupboardDAO1(Class<E> entityClass, Context context) {
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

    public List<E> list() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<E> list = cupboard().withDatabase(db).query(entityClass).list();
        return list;
    }

    public int count() {
        return list().size();
    }

    public E query(String query, String... args) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return cupboard().withDatabase(db).query(entityClass).withSelection(query, args).get();
    }
}
