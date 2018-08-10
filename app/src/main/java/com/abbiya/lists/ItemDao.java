package com.abbiya.lists;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item ORDER BY updated_at DESC, created_at")
    public List<Item> getAllItems();

    @Query("SELECT * FROM item WHERE parent_id IS NULL ORDER BY updated_at DESC, created_at")
    public List<Item> getAllParentItems();

    @Query("SELECT * FROM item WHERE parent_id = (:uid) ORDER BY updated_at DESC, created_at")
    public List<Item> getAllItemsOfParentItem(Integer uid);

    @Query("SELECT * FROM item WHERE content LIKE (:content) ORDER BY updated_at DESC, created_at")
    public List<Item> findItemsWithContent(String content);

    @Query("SELECT * FROM item WHERE parent_id = (:uid) AND content LIKE (:content) ORDER BY updated_at DESC, created_at")
    public List<Item> searchItemsOfParent(Integer uid, String content);

    @Query("SELECT * FROM item WHERE parent_id IS NULL AND content LIKE (:content) ORDER BY updated_at DESC, created_at")
    public List<Item> searchParents(String content);

    @Insert
    public void insert(Item item);

    @Update
    public void update(Item item);

    @Delete
    public void delete(Item item);
}
