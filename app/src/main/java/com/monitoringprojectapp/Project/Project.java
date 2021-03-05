package com.monitoringprojectapp.Project;

import com.monitoringprojectapp.User.User;

import java.util.Objects;

public class Project
{
    private int id;
    private String name;
    private User user;

    public Project(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Project(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project category = (Project) o;
        return id == category.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
