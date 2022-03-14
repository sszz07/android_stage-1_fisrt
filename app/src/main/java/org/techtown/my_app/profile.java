package org.techtown.my_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends AppCompatActivity {

    // 정보 적는변수
    TextView user;
    EditText et_user_nick;
    // 버튼 변수
    Button btn_user_edit, btn_user_delete, 좋아요;
    // 이미지 선택
    ImageView iv_users;
    //    ImageView iv_users;
    // 이미지 가지고 오기
    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;
    String name, hobby;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 레이아웃 연결
        user = findViewById(R.id.user);
        et_user_nick = findViewById(R.id.et_user_nick);
        btn_user_edit = findViewById(R.id.btn_user_edit);
        btn_user_delete = findViewById(R.id.btn_user_delete);
        iv_users = findViewById(R.id.iv_users);


      //프로필이 나오는 함수
        get_profile();
        SharedPreferences preferences = getSharedPreferences("shared", 0);
        String 아이디 = preferences.getString("id", "");

        user.setText(아이디);

//          버튼눌러서 픽사베이 이미지 가지고 오는 부분
        btn_user_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        // 수정버튼 클릭시 실행
        btn_user_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메소드 실행
                performEdit();
                Toast.makeText(profile.this, "수정 성공", Toast.LENGTH_SHORT).show();
            }
        });

        좋아요 = findViewById(R.id.좋아요);
        좋아요.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, like.class);
                startActivity(intent);
            }
        });
    } // onCreate 끝


    //-----------------------------------------------------------------------------------------------------
    // 서버로 데이터 전달
    private void performEdit() {
        String user_id = user.getText().toString();
        String nick = et_user_nick.getText().toString();
        String image = imageToString();
        Log.e("profile_서버로 이메일 보내기 정보", user_id+nick+image);

        Call<profile_data> call = profileApiClient.getApiClient().create(profileApiInterface.class).performUser_edit(user_id, nick, image);
        call.enqueue(new Callback<profile_data>() {
            @Override
            public void onResponse(Call<profile_data> call, Response<profile_data> response) {
                if (response.code() == 200) {

                    if (response.body().getStatus().equals("ok")) {

                        if (response.body().getResultCode() == 1) {

                        } else {
                            Toast.makeText(getApplicationContext(), "회원정보 변경 실패1", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "회원정보 변경 실패2", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "회원정보 변경 실패3", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<profile_data> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    } // performEdit메소드 끝


    //-----------------------------------------------------------------------------------------------------
    //디비에 있는 내 프로필값을 가져오는곳
    private void get_profile() {
        //현재 사용하고있는 유저를 가지고 오기 위해서
        SharedPreferences preferences = getSharedPreferences("shared", 0);
        String 아이디 = preferences.getString("id", "");

        String nick = et_user_nick.getText().toString();
        Log.e("디비에 있는 내 프로필값을 가져오는곳", nick);
        String user_image = iv_users.toString();

        profileApiInterface profileApiInterface = profileApiClient.getApiClient().create(profileApiInterface.class);
        Call<profile_data> call = profileApiInterface.get_profile(아이디, nick, user_image);
        call.enqueue(new Callback<profile_data>() {
            @Override
            public void onResponse(@NonNull Call<profile_data> call, @NonNull Response<profile_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String nick = response.body().getNick();
                    Log.e("리스폰스 바디에서 가져와 지는지", nick);
                    String user_image = response.body().getImage();
                    et_user_nick.setText(nick);


                    Log.e("profile____어떻게 받오오는지 확인", "profile____프로필 닉네임 : " + nick);
                    if (response.body().getImage() != null) {
                        Glide.with(getApplicationContext())
                                .load("http://52.79.180.89/" + user_image)// image url
                                .into(iv_users);
                    }
                }
            }

            @Override
            public void onFailure(Call<profile_data> call, Throwable t) {

            }

        });

    }


    //-----------------------------------------------------------------------------------------------------
    // 이미지 선택
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }


    //-----------------------------------------------------------------------------------------------------
    // 이미지 받는곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                iv_users.setImageBitmap(bitmap);
                iv_users.setVisibility(View.VISIBLE);
                user.setVisibility(View.VISIBLE);
                iv_users.setEnabled(false);
                btn_user_edit.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //-----------------------------------------------------------------------------------------------------
    // 이미지 보내는곳
    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);

    }
}

