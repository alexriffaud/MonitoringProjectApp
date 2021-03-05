package com.monitoringprojectapp.Requirement;

import com.monitoringprojectapp.Project.Project;
import com.monitoringprojectapp.Type.Type;

public class Requirement {
    private int id;
    private String identifier;
    private String description;
    private int isFunctional;
    private Project project;
    private Type type;

    public Requirement(int id, String identifier, String description, int isFunctional, Project project, Type type) {
        this.id = id;
        this.identifier = identifier;
        this.description = description;
        this.isFunctional = isFunctional;
        this.project = project;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsFunctional() {
        return isFunctional;
    }

    public void setIsFunctional(int isFunctional) {
        this.isFunctional = isFunctional;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
