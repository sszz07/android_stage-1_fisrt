package org.techtown.my_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class board_other_Activity extends AppCompatActivity
{
    public static final String TAG = "OtherActivity";

    TextView 제목보기,내용보기 ;
    Button 채팅;
    int room;
    String 제목, 내용,your,your2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_other);



        제목보기 = (TextView) findViewById(R.id.board_other_subject);
        내용보기 = (TextView) findViewById(R.id.board_other_content);


        Intent intent = getIntent();
        your = intent.getStringExtra("your");
        제목 = intent.getStringExtra("subject");
        내용 = intent.getStringExtra("content");
        Log.e(TAG, "인텐트 id : " + your + ", 인텐트 제목 : " + 제목 + ", 인텐트 내용 : " + 내용);
        제목보기.setText(제목);
        내용보기.setText(내용);

        get_room_number();

        채팅 = (Button) findViewById(R.id.채팅);
        채팅.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insert_room_number();

                Intent intent = new Intent(board_other_Activity.this, chatting_room.class);
                startActivity(intent);



            }
        });
    }






    // 채팅방이 만들어짐
    private void insert_room_number() {
        SharedPreferences preferences = getSharedPreferences("shared",0);
        String 아이디 = preferences.getString("id","");
        Log.e(TAG,"insert_room_number()"+아이디);
        Log.e(TAG,"insert_room_number()"+your);
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<board_data> call = boardApiInterface.insert_room_number(아이디,your);
        call.enqueue(new Callback<board_data>() {
            @Override
            public void onResponse(@NonNull Call<board_data> call, @NonNull Response<board_data> response) {
                if (response.isSuccessful() && response.body() != null) {


                }
            }

            @Override
            public void onFailure(@NonNull Call<board_data> call, @NonNull Throwable t) {
                Log.e(TAG, "에러 : " + t.getMessage());
            }
        });
    }

    private void get_room_number() {
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<board_data> call = boardApiInterface.room_number(room);
        call.enqueue(new Callback<board_data>() {
            @Override
            public void onResponse(@NonNull Call<board_data> call, @NonNull Response<board_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int room = response.body().getRoom_number();
                    Log.e("get_room_number()", String.valueOf(room));
                    Intent intent = new Intent(board_other_Activity.this, chatting_room.class);
                    intent.putExtra("room_number", room);
                }
            }

            @Override
            public void onFailure(@NonNull Call<board_data> call, @NonNull Throwable t) {
                Log.e("get_room_number()", "에러 : " + t.getMessage());
            }
        });
    }

}
