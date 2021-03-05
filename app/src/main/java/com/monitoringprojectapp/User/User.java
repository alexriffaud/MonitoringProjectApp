package com.monitoringprojectapp.User;

public class User {
    private int id;
    private String username;
    private String password;
    private int isManager;
    private int isProjectManager;
    private String token;

    public User(int id, String username, String password, int isManager, int isProjectManager, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isManager = isManager;
        this.isProjectManager = isProjectManager;
        this.token = token;
    }


    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public int getIsProjectManager() {
        return isProjectManager;
    }

    public void setIsProjectManager(int isProjectManager) {
        this.isProjectManager = isProjectManager;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
