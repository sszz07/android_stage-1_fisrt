package org.techtown.my_app;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface profileApiInterface {

    @FormUrlEncoded
    @POST("profile_edit.php")
    Call<profile_data> performUser_edit(@Field("id") String id, @Field("nick") String nick,
                                                           @Field("image") String image);




    @FormUrlEncoded
    @POST("profile_select.php")
    //@Field을 하는이유는 서버에서 잘 받아와야 되기 때문이다
    Call<profile_data> get_profile(@Field("id") String id, @Field("nick") String nick, @Field("image")
            String image);
}
