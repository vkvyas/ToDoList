package com.example.todolist.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vishalvyas on 4/27/16.
 */
public class TodoItemBean implements Parcelable {

    private String id;
    private String title;
    private String description;
    private long createdDate;
    private long updatedDate;
    private String isDeleted;

    public TodoItemBean() {
    }

    public TodoItemBean(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        createdDate = in.readLong();
        updatedDate = in.readLong();
        isDeleted = in.readString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String isDeleted() {
        return isDeleted;
    }

    public void setDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(createdDate);
        dest.writeLong(updatedDate);
        dest.writeString(isDeleted);
    }

    static final Creator<TodoItemBean> CREATOR
            = new Creator<TodoItemBean>() {

        public TodoItemBean createFromParcel(Parcel in) {
            return new TodoItemBean(in);
        }

        public TodoItemBean[] newArray(int size) {
            return new TodoItemBean[size];
        }
    };

}