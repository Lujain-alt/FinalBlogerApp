package com.example.finalblogerapp;

import org.json.JSONException;
import org.json.JSONObject;

public  class CommentModel {
    private String comText,uname,comTime;
    private String uID,pID;


    public CommentModel() {
    }

    public CommentModel(String comText, String uID, String uname, String comTime) {
        this.comText = comText;
        this.uID = uID;
        this.uname = uname;
        this.comTime = comTime;

    }



    public CommentModel(String comText, String uID, String pId) {
        this.comText = comText;
        this.uID = uID;
        this.pID = pId;
        this.uname=uname;
        this.comTime=comTime;
    }




    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getComText() {
        return comText;
    }

    public void setComText(String comText) {
        this.comText = comText;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getComTime() {
        return comTime;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }


    static CommentModel createComment(JSONObject JOcomment) throws JSONException {

        CommentModel commentsModel =new CommentModel();

        commentsModel.setComText(JOcomment.getString("com_text"));
        commentsModel.setComTime(JOcomment.getString("com_time"));
        commentsModel.setUname(JOcomment.getString("u_name"));
        commentsModel.setuID(JOcomment.getString("u_id"));
        commentsModel.setpID(JOcomment.getString("P__id"));


        return commentsModel;

    }
}
