package org.techtown.my_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// DTP(pojo): (Data Transfer Object)||(Plain Old Java Object)형태의 모델(Model)/json 타입변환에 사용
public class profile_data {

    // Gson은 자바 객체와 JSON 간의 직렬화 및 역직렬화를 위한 오픈소스 라이브러리
    // @SerializedName("")을 붙여 원래 변수명을 넣어주고 그 밑에 변수를 선언할 떄는 원하는 변수명을 사용하면 된다.
    // @SerializedName은 Gson라이브러리에서 제공하는 어노테이션인데 여기서 Serialize는 data class객체를 JSON형태로 변환하는 것을 말한다. (한국말로는 직렬화이다)
    // user_email == 직렬화 이름

    @SerializedName("status")
    private String status;

    @SerializedName("result_code")
    private int resultCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("nick")
    private String nick;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(String user_idx) {
        this.user_idx = user_idx;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("password")
    private String password;

    @SerializedName("user_idx")
    private String user_idx;

    @Expose
    @SerializedName("image")
    private String image;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
