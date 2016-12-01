package com.doubletechnology.todolistapp;

/**
 * Created by root on 12/1/16.
 */

public class PendingItem {
    private String title;
    private int priority;
    private int id;

    public PendingItem() {}

    public PendingItem(String title, int priority) {
        this.title = title;
        this.priority = priority;
    }

    public PendingItem(String title, int priority, int id) {
        this.title = title;
        this.priority = priority;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
