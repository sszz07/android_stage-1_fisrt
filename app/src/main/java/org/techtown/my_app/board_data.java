package org.techtown.my_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class board_data {
    @Expose
    @SerializedName("idx")
    private int idx;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    @Expose
    @SerializedName("room_number")
    private int room_number;

    @Expose
    @SerializedName("me")
    private String me;

    @Expose
    @SerializedName("your")
    private String your;

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getYour() {
        return your;
    }

    public void setYour(String your) {
        this.your = your;
    }

    @Expose
    @SerializedName("subject")
    private String subject;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Expose
    @SerializedName("success")
    private Boolean success;

    @Expose
    @SerializedName("message")
    private String message;




    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
