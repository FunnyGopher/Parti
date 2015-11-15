package com.github.funnygopher.parti;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.funnygopher.parti.event.Event;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by kylhunts on 11/14/2015.
 */
public class CupboardSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "parti.db";
    private static final int DATABASE_VERSION = 1;

    static {
        cupboard().register(Event.class);
    }

    public CupboardSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
