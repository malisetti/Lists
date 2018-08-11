package com.abbiya.lists;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    // get all items
    @Query("SELECT * FROM items ORDER BY updated_at DESC, created_at DESC")
    public LiveData<List<Item>> getAllItems();

    // get an item by id
    @Query("SELECT * FROM items WHERE uid = (:uid)")
    public LiveData<Item> getItem(Integer uid);

    // get all top level items
    @Query("SELECT * FROM items WHERE parent_id IS NULL ORDER BY updated_at DESC, created_at DESC")
    public LiveData<List<Item>> getAllRoots();

    // get children of an item
    @Query("SELECT * FROM items WHERE parent_id = (:uid) ORDER BY updated_at DESC, created_at DESC")
    public LiveData<List<Item>> getAllItemsOfParent(Integer uid);

    // search all items for content and return items
    @Query("SELECT * FROM items WHERE content LIKE (:content) ORDER BY updated_at DESC, created_at DESC")
    public LiveData<List<Item>> findItemsWithContent(String content);

    // search child items of an item for content
    @Query("SELECT * FROM items WHERE parent_id = (:uid) AND content LIKE (:content) ORDER BY updated_at DESC, created_at DESC")
    public LiveData<List<Item>> searchItemsOfParent(Integer uid, String content);

    // search only the top level root items for content
    @Query("SELECT * FROM items WHERE parent_id IS NULL AND content LIKE (:content) ORDER BY updated_at DESC, created_at DESC")
    public LiveData<List<Item>> searchRoots(String content);

    // get children count of a item
    @Query("SELECT COUNT(uid) FROM items WHERE parent_id = (:parentId)")
    public LiveData<Integer> countChildrenOfRoot(Integer parentId);

    @Insert
    public void insert(Item item);

    @Update
    public void update(Item item);

    @Delete
    public void delete(Item item);

    @Query("DELETE from items")
    public void deleteAll();
}
