package com.example.finalblogerapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

public class PostModel {


    private String userId;
    private String pID;
    private String title;
    private String content;
    private String userName;
    private String userPhoto;
    private String timeStamp ;
    private String category;
    private String currentUser;

    public PostModel(String title,String content,String userName,String timeStamp,String pID,String uID) {
        this.title=title;
        this.content = content;
        this.userName = userName;
        this.timeStamp = timeStamp;
        this.userId=userId;
        this.pID=pID;
    this.currentUser=uID;

        Log.i("PostModelpID",pID);
    }

    public PostModel(String title,String content,String userName,String timeStamp) {
        this.title=title;
        this.content = content;
        this.userName = userName;
        this.timeStamp = timeStamp;


    }

    public PostModel(String title,String content,String currentUser) {
        this.content = content;
        this.title=title;
        this.currentUser = currentUser;
        this.category = category;
    }


    public PostModel() {
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    static PostModel createPost(JSONObject JOpost) throws JSONException {

        PostModel post=new PostModel();

        post.setUserName(JOpost.getString("u_name"));
        post.setCurrentUser(JOpost.getString("u_id"));
        post.setpID(JOpost.getString("p__id"));
        post.setTitle(JOpost.getString("p__title"));
        post.setContent(JOpost.getString("p_content"));
        post.setTimeStamp(JOpost.getString("p_time"));
        post.setCategory(JOpost.getString("p_category"));


        return post;

    }

}

