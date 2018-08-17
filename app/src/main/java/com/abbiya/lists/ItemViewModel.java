package com.abbiya.lists;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);

        mRepository = new ItemRepository(application);
    }

    LiveData<PagedList<Item>> getRoots() {
        return mRepository.getAllRoots();
    }

    LiveData<PagedList<Item>> getChildren(int i) {
        return mRepository.getChildren(i);
    }

    LiveData<PagedList<Item>> search(Integer i, String content) {
        return mRepository.search(i, content);
    }

    public void insert(Item item) {
        mRepository.insert(item);
    }

    public void delete(Item item) {
        mRepository.delete(item);
    }
}
