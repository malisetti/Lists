package com.abbiya.lists;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ItemDao {
    // get all items
    @Query("SELECT * FROM items ORDER BY updated_at DESC, created_at DESC")
    public DataSource.Factory<Integer, Item> getAllItems();

    // get an item by id
    @Query("SELECT * FROM items WHERE uid = (:uid)")
    public DataSource.Factory<Integer, Item> getItem(Integer uid);

    // get all top level items
    @Query("SELECT * FROM items WHERE parent_id IS NULL ORDER BY updated_at DESC, created_at DESC")
    public DataSource.Factory<Integer, Item> getAllRoots();

    // get children of an item
    @Query("SELECT * FROM items WHERE parent_id = (:uid) ORDER BY updated_at DESC, created_at DESC")
    public DataSource.Factory<Integer, Item> getAllItemsOfParent(Integer uid);

    // search all items for content and return items
    @Query("SELECT * FROM items WHERE content LIKE (:content) ORDER BY updated_at DESC, created_at DESC")
    public DataSource.Factory<Integer, Item> findItemsWithContent(String content);

    // search child items of an item for content
    @Query("SELECT * FROM items WHERE parent_id = (:uid) AND content LIKE (:content) ORDER BY updated_at DESC, created_at DESC")
    public DataSource.Factory<Integer, Item> searchItemsOfParent(Integer uid, String content);

    // search only the top level root items for content
    @Query("SELECT * FROM items WHERE parent_id IS NULL AND content LIKE (:content) ORDER BY updated_at DESC, created_at DESC")
    public DataSource.Factory<Integer, Item> searchRoots(String content);

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
