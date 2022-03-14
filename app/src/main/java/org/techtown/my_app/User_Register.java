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

public class User_Register extends AppCompatActivity
{

    private EditText id, nick, password, password2;
    private Button btnregister;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferenceHelper = new PreferenceHelper(this);

        id = (EditText) findViewById(R.id.id);
        nick = (EditText) findViewById(R.id.nick);
        password = (EditText) findViewById(R.id.register_password);
        password2 = (EditText) findViewById(R.id.register_password2);

        btnregister = (Button) findViewById(R.id.btn_회원가입);
        btnregister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerMe();
            }
        });
    }

    //값을 서버에 보내는 함수
    private void registerMe()
    {
        final String 아이디 = id.getText().toString();
        final String 닉네임 = nick.getText().toString();
        final String 비번 = password.getText().toString();
        final String 비번2 = password2.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(User_RegisterInterface.REGIST_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

       User_RegisterInterface api = retrofit.create(User_RegisterInterface.class);
        Call<String> call = api.getUserRegist(아이디, 닉네임, 비번, 비번2);
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    Log.e("onSuccess", response.body());
                    String jsonResponse = response.body();
                    try
                    {
                        parseRegData(jsonResponse);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t)
            {
                Log.e("false", "연결이 안되었습니다");
            }
        });
    }

    private void parseRegData(String response) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true"))
        {
            saveInfo(response);
            Intent intent = new Intent(User_Register.this, User_Login.class);
            startActivity(intent);
            Toast.makeText(User_Register.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(User_Register.this, "회원가입이 안되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveInfo(String response)
    {

        preferenceHelper.putIsLogin(true);
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true"))
            {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++)
                {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.put_id(dataobj.getString("id"));
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}