package com.abbiya.lists;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.Executors;

public class ItemRepository {

    private ItemDao mItemDao;

    private LiveData<PagedList<Item>> mRoots;

    ItemRepository(Application application) {
        Database db = Database.getDatabase(application);
        mItemDao = db.itemDao();
        mRoots = new LivePagedListBuilder<>(mItemDao.getAllRoots(), 20).build();
    }

    LiveData<PagedList<Item>> getAllRoots() {
        return mRoots;
    }

    LiveData<PagedList<Item>> getChildren(int i) {
        return new LivePagedListBuilder<>(mItemDao.getAllItemsOfParent(i), 20).build();
    }

    LiveData<PagedList<Item>> search(Integer i, String content) {
        if (i == null || i == 0) {
            return new LivePagedListBuilder<>(mItemDao.searchRoots("%"+content+"%"), 20).build();
        }

        return new LivePagedListBuilder<>(mItemDao.searchItemsOfParent(i, "%"+content+"%"), 20).build();
    }

    public void insert(Item item) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                mItemDao.insert(item);
            }
        });
    }
}
