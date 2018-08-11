package com.abbiya.lists;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;

    private LiveData<List<Item>> mRoots;

    public ItemViewModel(@NonNull Application application) {
        super(application);

        mRepository = new ItemRepository(application);
        mRoots = mRepository.getAllRoots();
    }

    LiveData<List<Item>> getRoots() {
        return mRoots;
    }

    public void insert(Item item) {
        mRepository.insert(item);
    }
}
