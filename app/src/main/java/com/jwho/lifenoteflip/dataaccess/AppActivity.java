package com.jwho.lifenoteflip.dataaccess;

public class AppActivity {
    protected String status;
    protected String appName;
    protected String color;
    private String imgPath;

    public AppActivity(String status, String appName, String color, String imgPath) {
        this.status = status;
        this.appName = appName;
        this.color = color;
        this.imgPath = imgPath;
    }
}
