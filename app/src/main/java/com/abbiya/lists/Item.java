package com.abbiya.lists;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

@Entity(indices = {@Index("content"), @Index("parent_id")},
        foreignKeys = @ForeignKey(entity = Item.class,
        parentColumns = "uid",
        childColumns = "parent_id",
        onDelete = ForeignKey.CASCADE))
public class Item {

    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "content")
    @NonNull
    private String content;

    @ColumnInfo(name = "parent_id")
    @Nullable
    private Integer parentId;

    @ColumnInfo(name = "created_at")
    @NonNull
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    @NonNull
    private Date updatedAt;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Nullable
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(@Nullable Integer parentId) {
        this.parentId = parentId;
    }
}
