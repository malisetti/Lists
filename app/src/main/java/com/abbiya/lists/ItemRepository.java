package com.abbiya.lists;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ItemRepository {

    private ItemDao mItemDao;

    private LiveData<List<Item>> mRoots;

    ItemRepository(Application application) {
        Database db = Database.getDatabase(application);
        mItemDao = db.itemDao();
        mRoots = mItemDao.getAllRoots();
    }

    LiveData<List<Item>> getAllRoots() {
        return mRoots;
    }

    LiveData<List<Item>> getChildren(int i) {
        return mItemDao.getAllItemsOfParent(i);
    }

    public void insert(Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<Item, Item, Item> {

        private ItemDao itemDao;

        insertAsyncTask(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        @Override
        protected Item doInBackground(Item... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }
}
