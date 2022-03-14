package org.techtown.my_app;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class board extends AppCompatActivity {
    public static final String TAG = "board";

    NestedScrollView nestedScrollView;
    ImageButton write;
    RecyclerView recyclerView;
    board_Adapter adapter;
    board_Adapter.ItemClickListener itemClickListener;
    List<board_data> list = new ArrayList<>();
    int page = 0, limit = 10;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //xml에서 아이디값 가져오기
        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = (RecyclerView) findViewById(R.id.person_recyclerview);



        //현재 사용하고있는 유저를 가지고 오기 위해서
        SharedPreferences preferences = getSharedPreferences("shared", 0);
        String 아이디 = preferences.getString("id", "");


        //게시판 추가 입력 클래스로 이동하기
        write = findViewById(R.id.board_add_btn);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(board.this, boardwrite.class);
                startActivity(intent);

            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        select_board(page, limit);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    Log.e("페이징 확인", String.valueOf(page));
                    Log.e("리미트 확인", String.valueOf(limit));
                    limit += 10;
                    Log.e("page +", String.valueOf(page));
                    Log.e("limit", String.valueOf(limit));
                    select_board(page, limit);
                }
            }
        });


        itemClickListener = new board_Adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = list.get(position).getId();
                int idx = list.get(position).getIdx();
                int room = list.get(position).getRoom_number();
                Log.e("idx값이 무엇인지 확인하기", String.valueOf(idx));
                Log.e("아이디값이 무엇인지 확인하기", 아이디);

                //int형일때는 == 이것을 사용하지만 String형태일때는 이퀄스를 사용한다
                if (아이디.equals(id)) {

                    String 제목 = list.get(position).getSubject();
                    String 내용 = list.get(position).getContent();
                    Log.e(TAG, "idx : " + idx + ", 제목 : " + 제목 + ", 내용 : " + 내용);
                    Intent intent = new Intent(board.this, board_user_Activity.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("subject", 제목);
                    intent.putExtra("content", 내용);
                    startActivity(intent);
                }
                else{
                    String 게시글올린사람 = list.get(position).getId();
                    String 제목 = list.get(position).getSubject();
                    String 내용 = list.get(position).getContent();
                    Log.e(TAG, "게시글올린사람 : " + 게시글올린사람 + ", 제목 : " + 제목 + ", 내용 : " + 내용);
                    Intent intent = new Intent(board.this, board_other_Activity.class);
                    intent.putExtra("your", 게시글올린사람);
                    intent.putExtra("subject", 제목);
                    intent.putExtra("content", 내용);
                    startActivity(intent);
                }
            }
        };
    }

    //내가 선택을 했을때
    private void select_board(int page, int limit) {
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<List<board_data>> call = boardApiInterface.get_board(page, limit);
        call.enqueue(new Callback<List<board_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<board_data>> call, @NonNull Response<List<board_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<board_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<board_data> lists) {
        adapter = new board_Adapter(this, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }
}





