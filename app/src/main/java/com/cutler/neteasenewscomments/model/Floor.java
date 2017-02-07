package com.cutler.neteasenewscomments.model;

import org.json.JSONObject;

public class Floor {
    private int floorId;
    private String userName;
    private String userPhoto;
    private String content;
    private boolean isHide;

    public int getFloorId() {
        return floorId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public String getContent() {
        return content;
    }

    public boolean isHide() {
        return isHide;
    }

    public static Floor parseJson(JSONObject jsonObject) {
        Floor instance = new Floor();
        try {
            instance.isHide = jsonObject.optBoolean("isHide", false);
            if (!instance.isHide) {
                instance.floorId = jsonObject.getInt("floorId");
                instance.userName = jsonObject.getString("userName");
                instance.userPhoto = jsonObject.getString("userPhoto");
                instance.content = jsonObject.getString("content");
            }
        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }
        return instance;
    }
}
