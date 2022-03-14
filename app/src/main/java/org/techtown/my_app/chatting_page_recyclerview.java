package org.techtown.my_app;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class chatting_page_recyclerview extends AppCompatActivity {
    public static final String TAG = "chatting_page_recyclerview";

    RecyclerView recyclerView;
    chatting_page_Adaptor adapter;
    chatting_page_Adaptor.ItemClickListener itemClickListener;
    List<chatting_page_data> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_recyclerview);

        //xml에서 아이디값 가져오기
        recyclerView = (RecyclerView) findViewById(R.id.chatting_page);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //게시판 추가 입력 클래스로 이동하기
        select_chatting_page();

        itemClickListener = new chatting_page_Adaptor.ItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(View view, int position) {
                //내가 대화를 건사람 나에게 대화를 건사람의 방번호를 가지고 채팅방으로 간다
                int room_number = list.get(position).getRoom_number();
                Intent intent = new Intent(getApplicationContext(), chatting_room.class);
                intent.putExtra("room_number", room_number);
                startActivity(intent);
            }
        };
    }

    //내가 미에 있을때
    private void select_chatting_page() {
        //채팅버튼을 누르고 유어의 값을 받기 위해서
        SharedPreferences preferences = getSharedPreferences("shared", 0);
        String 아이디 = preferences.getString("id", "");

        chatting_page_ApiInterface chatting_page_apiInterface = chatting_ApiClient.getApiClient().create(chatting_page_ApiInterface.class);
        Call<List<chatting_page_data>> call = chatting_page_apiInterface.get_chatting_room(아이디);
        call.enqueue(new Callback<List<chatting_page_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<chatting_page_data>> call, @NonNull Response<List<chatting_page_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<chatting_page_data>> call, @NonNull Throwable t) {
                Log.e("select_chatting_page()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<chatting_page_data> lists) {
        SharedPreferences preferences = getSharedPreferences("shared", 0);
        String 아이디 = preferences.getString("id", "");
        adapter = new chatting_page_Adaptor(this, lists, itemClickListener, 아이디);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }
}





