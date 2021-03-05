package com.monitoringprojectapp.Milestone;

import com.monitoringprojectapp.User.User;

import java.util.Date;

public class Milestone {
    private int id;
    private String label;
    private User manager;
    private Date expectedDeliveryDate;
    private Date realDeliveryDate;

    public Milestone(int id, String label, User manager, Date expectedDeliveryDate, Date realDeliveryDate) {
        this.id = id;
        this.label = label;
        this.manager = manager;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.realDeliveryDate = realDeliveryDate;
    }

    public Milestone(int id, String label, User manager, Date expectedDeliveryDate) {
        this.id = id;
        this.label = label;
        this.manager = manager;
        this.expectedDeliveryDate = expectedDeliveryDate;
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

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Date getRealDeliveryDate() {
        return realDeliveryDate;
    }

    public void setRealDeliveryDate(Date realDeliveryDate) {
        this.realDeliveryDate = realDeliveryDate;
    }
}
