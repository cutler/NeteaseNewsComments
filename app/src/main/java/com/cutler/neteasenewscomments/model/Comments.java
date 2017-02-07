package com.cutler.neteasenewscomments.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Comments {
    private long commentsId;
    private String userName;
    private String userPhoto;
    private String content;

    private List<Floor> floorList;

    public long getCommentsId() {
        return commentsId;
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

    public List<Floor> getFloorList() {
        return floorList;
    }

    public static Comments parseJson(JSONObject jsonObject) {
        Comments instance = new Comments();
        try {
            instance.commentsId = jsonObject.getLong("commentsId");
            instance.userName = jsonObject.getString("userName");
            instance.userPhoto = jsonObject.getString("userPhoto");
            instance.content = jsonObject.getString("content");
            instance.floorList = new ArrayList<>();
            JSONArray floorArray = jsonObject.optJSONArray("floorList");
            if (floorArray != null) {
                for (int i = 0; i < floorArray.length(); i++) {
                    Floor floor = Floor.parseJson(floorArray.getJSONObject(i));
                    if (floor != null) {
                        instance.floorList.add(floor);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }
        return instance;
    }

    public static List<Floor> parseJsonForFloorList(String json) {
        ArrayList<Floor> floorList = new ArrayList<>();
        try {
            JSONArray floorArray = new JSONArray(json);
            for (int i = 0; i < floorArray.length(); i++) {
                Floor floor = Floor.parseJson(floorArray.getJSONObject(i));
                if (floor != null) {
                    floorList.add(floor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            floorList = null;
        }
        return floorList;
    }
}
