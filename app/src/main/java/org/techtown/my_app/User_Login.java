package org.techtown.my_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class User_Login extends AppCompatActivity {
    private final String TAG = "LoginActivity";

    private EditText id, password;
    private Button btnlogin, 회원가입;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceHelper = new PreferenceHelper(this);

        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);

        btnlogin = (Button) findViewById(R.id.login);
        회원가입 = (Button) findViewById(R.id.회원가입);


        회원가입.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Login.this, User_Register.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        final String 아이디 = id.getText().toString();
        Log.e(" ", 아이디);
        final String 비번 = password.getText().toString();
        Log.e(" ", 비번);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(User_LoginInterface.LOGIN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        User_LoginInterface api = retrofit.create(User_LoginInterface.class);
        Call<String> call = api.getUserLogin(아이디, 비번);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("onSuccess", response.body());
                    String jsonResponse = response.body();
                    parseLoginData(jsonResponse);
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e(TAG, "에러 = " + t.getMessage());
            }
        });
    }

    private void parseLoginData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                saveInfo(response);
                Toast.makeText(User_Login.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(User_Login.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(User_Login.this, "아이디와 비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveInfo(String response) {
        preferenceHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.put_id(dataobj.getString("id"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}