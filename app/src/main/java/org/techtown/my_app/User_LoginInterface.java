package org.techtown.my_app;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface User_LoginInterface
{
    String LOGIN_URL = "http://52.79.180.89/Login_Register/";

    @FormUrlEncoded
    @POST("login.php")
    Call<String> getUserLogin(
            @Field("id") String id,
            @Field("password") String password
    );
}
