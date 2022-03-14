package org.techtown.my_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class chatting_page_data {

    @Expose
    @SerializedName("last_answer")
    private String last_answer;

    @Expose
    @SerializedName("YOUR")
    private String YOUR;

    @Expose
    @SerializedName("room_number")
    private int room_number;

    @Expose
    @SerializedName("ME")
    private String ME;


    public String getLast_answer() {
        return last_answer;
    }

    public void setLast_answer(String last_answer) {
        this.last_answer = last_answer;
    }

    public String getYOUR() {
        return YOUR;
    }

    public void setYOUR(String YOUR) {
        this.YOUR = YOUR;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getME() {
        return ME;
    }

    public void setME(String ME) {
        this.ME = ME;
    }
}
