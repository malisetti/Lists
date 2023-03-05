package com.abbiya.lists;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import java.util.Date;

@androidx.room.Database(entities = {Item.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {
    private static Database INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "lists")
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ItemDao itemDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final ItemDao mDao;

        PopulateDbAsync(Database db) {
            mDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Item item = new Item();
            item.setUid(1);
            item.setParentId(null);
            item.setContent("Hello world");
            Date d = new Date();
            item.setCreatedAt(d);
            item.setUpdatedAt(d);

            mDao.insert(item);
            return null;
        }
    }
}
