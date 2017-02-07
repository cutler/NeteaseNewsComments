package com.cutler.neteasenewscomments.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private long topicId;
    private String userName;
    private String userPhoto;
    private String title;
    private String content;
    private List<String> imageList;
    private int praise;
    private int tread;
    private int reply;
    private int share;
    private long createTime;
    private List<Comments> commentsList;

    public String getUserName() {
        return userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public int getPraise() {
        return praise;
    }

    public int getTread() {
        return tread;
    }

    public int getReply() {
        return reply;
    }

    public int getShare() {
        return share;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getTopicId() {
        return topicId;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public static Topic parseJson(String jsonStr) {
        Topic instance = new Topic();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            instance.userName = jsonObject.getString("userName");
            instance.userPhoto = jsonObject.getString("userPhoto");
            instance.title = jsonObject.getString("title");
            instance.content = jsonObject.getString("content");
            instance.imageList = new ArrayList<>();
            JSONArray imageArray = jsonObject.getJSONArray("imageList");
            for (int i = 0; i < imageArray.length(); i++) {
                instance.imageList.add(imageArray.getString(i));
            }
            instance.praise = jsonObject.getInt("praise");
            instance.tread = jsonObject.getInt("tread");
            instance.reply = jsonObject.getInt("reply");
            instance.share = jsonObject.getInt("share");
            instance.createTime = jsonObject.getLong("createTime");
            instance.topicId = jsonObject.getLong("topicId");
            instance.commentsList = new ArrayList<>();
            JSONArray commentsArray = jsonObject.getJSONArray("commentsList");
            for (int i = 0; i < commentsArray.length(); i++) {
                Comments comments = Comments.parseJson(commentsArray.getJSONObject(i));
                if (comments != null) {
                    instance.commentsList.add(comments);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }
        return instance;
    }
}
