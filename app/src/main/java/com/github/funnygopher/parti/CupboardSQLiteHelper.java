package com.github.funnygopher.parti;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.funnygopher.parti.hosting.HostedEvent;
import com.github.funnygopher.parti.invitation.Invitation;
import com.github.funnygopher.parti.rsvp.RSVP;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Kyle on 11/14/2015.
 */
public class CupboardSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "parti.db";
    private static final int DATABASE_VERSION = 1;

    static {
        cupboard().register(RSVP.class);
        cupboard().register(Invitation.class);
        cupboard().register(HostedEvent.class);
    }

    public CupboardSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
