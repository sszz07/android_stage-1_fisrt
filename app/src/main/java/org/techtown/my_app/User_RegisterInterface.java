package org.techtown.my_app;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface User_RegisterInterface
{
    String REGIST_URL = "http://52.79.180.89/Login_Register/";

    @FormUrlEncoded
    @POST("register.php")
    Call<String> getUserRegist(
            @Field("id") String id,
            @Field("nick") String nick,
            @Field("password") String password,
            @Field("password2") String password2
    );
}
