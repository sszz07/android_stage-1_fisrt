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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class board_Update_Activity extends AppCompatActivity
{
    public static final String TAG = "UpdateActivity";

    EditText subject_edittext, content_edittext;
    Button update_done;
    String update_subject, update_content,id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        subject_edittext = (EditText) findViewById(R.id.subject_edittext);
        content_edittext = (EditText) findViewById(R.id.content_edittext);
        update_done = (Button) findViewById(R.id.update_done);

        Intent intent = getIntent();
        int idx = intent.getIntExtra("idx",0);
        update_subject = intent.getStringExtra("subject");
        update_content = intent.getStringExtra("content");


        subject_edittext.setText(update_subject);
        content_edittext.setText(update_content);

        update_done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                update_board(idx);
                Toast.makeText(board_Update_Activity.this, "수정 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(board_Update_Activity.this, board.class);
                startActivity(intent);
            }
        });
    }

    private void update_board(int idx)
    {
        boardApiInterface boardApiInterface = boardApiClient.getApiClient().create(boardApiInterface.class);
        update_subject = subject_edittext.getText().toString();
        update_content = content_edittext.getText().toString();
        Log.e(TAG, "수정 id : " + id + ", 수정 이름 : " + update_subject + ", 수정 취미 : " + update_content);
        Call<board_data> call = boardApiInterface.update_board(idx, update_subject, update_content);
        call.enqueue(new Callback<board_data>()
        {
            @Override
            public void onResponse(@NonNull Call<board_data> call, @NonNull Response<board_data> response)
            {

            }

            @Override
            public void onFailure(@NonNull Call<board_data> call, @NonNull Throwable t)
            {
                Log.e("updatePerson()", "에러 : " + t.getMessage());
            }
        });
    }

}