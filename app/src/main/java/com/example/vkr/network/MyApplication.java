package com.example.vkr.network;

import android.app.Application;

public class MyApplication extends Application {

    private String login = "";
    private String password = "";

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}