package com.monitoringprojectapp.Task;

import java.util.Date;
import java.util.Objects;

import com.monitoringprojectapp.Milestone.Milestone;
import com.monitoringprojectapp.User.User;

public class Task
{
    private int id;
    private String label;
    private String description;
    private String identifier;
    private User user;
    private Date theoreticalStartDate;
    private int load;
    private Date realStartDate;
    private Task previousTask;
    private Milestone milestone;

    public Task(int id, String label, String description, String identifier, User user, Date theoreticalStartDate, int load, Date realStartDate, Task previousTask, Milestone milestone) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.identifier = identifier;
        this.user = user;
        this.theoreticalStartDate = theoreticalStartDate;
        this.load = load;
        this.realStartDate = realStartDate;
        this.previousTask = previousTask;
        this.milestone = milestone;
    }

    public Task(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTheoreticalStartDate() {
        return theoreticalStartDate;
    }

    public void setTheoreticalStartDate(Date theoreticalStartDate) {
        this.theoreticalStartDate = theoreticalStartDate;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public Date getRealStartDate() {
        return realStartDate;
    }

    public void setRealStartDate(Date realStartDate) {
        this.realStartDate = realStartDate;
    }

    public Task getPreviousTask() {
        return previousTask;
    }

    public void setPreviousTask(Task previousTask) {
        this.previousTask = previousTask;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }
}
