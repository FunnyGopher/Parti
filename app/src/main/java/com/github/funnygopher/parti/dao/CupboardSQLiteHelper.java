package com.github.funnygopher.parti.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.funnygopher.parti.model.HostedEvent;
import com.github.funnygopher.parti.model.Invitation;
import com.github.funnygopher.parti.model.LocalEvent;
import com.github.funnygopher.parti.model.RSVP1;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by FunnyGopher
 */
public class CupboardSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "parti.db";
    private static final int DATABASE_VERSION = 1;

    static {
        cupboard().register(LocalEvent.class);
        cupboard().register(RSVP1.class);
        cupboard().register(Invitation.class);
        cupboard().register(HostedEvent.class);
    }

    public CupboardSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }
}
