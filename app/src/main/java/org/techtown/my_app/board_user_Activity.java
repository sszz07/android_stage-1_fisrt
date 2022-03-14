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

public class board_user_Activity extends AppCompatActivity {
    public static final String TAG = "board_user_Activity";

    TextView 제목보기, 내용보기;
    Button 수정버튼, 삭제버튼, 댓글;
    String 제목, 내용;
    Button 수정, 삭제;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_user);

        제목보기 = (TextView) findViewById(R.id.board_subject);
        내용보기 = (TextView) findViewById(R.id.board_content);
        수정버튼 = (Button) findViewById(R.id.board_update_button);
        삭제버튼 = (Button) findViewById(R.id.board_delete_button);


        //수정할때 데이터를 받는곳
        Intent intent = getIntent();
        int idx = intent.getIntExtra("idx",0);
        제목 = intent.getStringExtra("subject");
        내용 = intent.getStringExtra("content");
        Log.e(TAG, "인텐트 idx : " + idx + ", 인텐트 제목 : " + 제목 + ", 인텐트 내용 : " + 내용);
        제목보기.setText(제목);
        내용보기.setText(내용);


        수정버튼.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update_intent = new Intent(board_user_Activity.this, board_Update_Activity.class);
                update_intent.putExtra("idx", idx);
                update_intent.putExtra("subject", 제목);
                update_intent.putExtra("content", 내용);
                startActivity(update_intent);
            }
        });


        삭제버튼.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 삭제 메서드
                delete_board(idx);
                Intent delete_intent = new Intent(board_user_Activity.this, board.class);
                startActivity(delete_intent);
            }
        });
    }


    private void delete_board(int idx) {
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        Call<board_data> call = boardApiInterface.delete_board(idx);
        call.enqueue(new Callback<board_data>() {
            @Override
            public void onResponse(@NonNull Call<board_data> call, @NonNull Response<board_data> response) {

            }

            @Override
            public void onFailure(@NonNull Call<board_data> call, @NonNull Throwable t) {
                Log.e("deletePerson()", t.getMessage());
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