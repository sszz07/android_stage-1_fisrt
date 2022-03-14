package org.techtown.my_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class boardwrite extends AppCompatActivity {

    Button add;
    EditText subject, content;
    String 제목, 내용;
    board_Adapter adapter;
    board_Adapter.ItemClickListener itemClickListener;
    List<board_data> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardwrite);

        subject = (EditText) findViewById(R.id.제목);
        content = (EditText) findViewById(R.id.내용);
        add = (Button) findViewById(R.id.board_add_btn);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                제목 = subject.getText().toString();
                내용 = content.getText().toString();
                insert_board();
                Intent intent = new Intent(getApplicationContext(), board.class);
                startActivity(intent);

            }
        });
    }


    // ↓ 추가된 부분
    private void insert_board() {
        SharedPreferences preferences = getSharedPreferences("shared",0);
        String 아이디 = preferences.getString("id","");
        Log.e("보드 클래스에서의 아이디값 가져오기",아이디);

        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<board_data> call = boardApiInterface.insert_board(제목, 내용, 아이디);
        call.enqueue(new Callback<board_data>() {
            @Override
            public void onResponse(@NonNull Call<board_data> call, @NonNull Response<board_data> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println(response.isSuccessful());
                    System.out.println(response.code());

                    Boolean success = response.body().getSuccess();
                    if (success) {
                        Log.e("성공", String.valueOf(success));
                        onSuccess(response.body().getMessage());
                    }else {
                        onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<board_data> call, @NonNull Throwable t) {
                Log.e("insertPerson()", "에러 : " + t.getMessage());
            }
        });
    }


    private void onError(String message) {
        Log.e("insertPerson()", "onResponse() 에러 : " + message);
    }

    private void onSuccess(String message) {
        Log.e("insertPerson()", "onResponse() 성공 : " + message);
        setResult(RESULT_OK);
        finish();
    }


}