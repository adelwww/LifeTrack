package com.example.lifetrack.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskModel {
    @PrimaryKey(autoGenerate = true)
     public long id;

    String task;
    String deadline;
    String repeatCount;

    public TaskModel(String task, String deadline, String repeatCount) {
        this.task = task;
        this.deadline = deadline;
        this.repeatCount = repeatCount;
    }

    public String getTask() {
        return task;
    }


    public String getDeadline() {
        return deadline;
    }


    public String getRepeatCount() {
        return repeatCount;
    }
}
