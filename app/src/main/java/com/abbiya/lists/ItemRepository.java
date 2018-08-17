package com.abbiya.lists;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemRepository {

    private ItemDao mItemDao;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    ItemRepository(Application application) {
        Database db = Database.getDatabase(application);
        mItemDao = db.itemDao();
    }

    LiveData<PagedList<Item>> getAllRoots() {
        return new LivePagedListBuilder<>(mItemDao.getAllRoots(), 20).build();
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

    public void insert(final Item item) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                mItemDao.insert(item);
            }
        });
    }

    public void delete(final Item item) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                mItemDao.delete(item);
            }
        });
    }
}
