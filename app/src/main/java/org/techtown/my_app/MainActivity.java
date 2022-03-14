package org.techtown.my_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    EditText edit_name, edit_hobby;
    ImageButton add_btn;
    RecyclerView recyclerView;
   imageboard_text_Adapter adapter;
    imageboard_text_Adapter.ItemClickListener itemClickListener;
    List<imageboard_text_data> list = new ArrayList<>();
    List<image_data> imagelist = new ArrayList<>();
    ImageView imageView, mainImage;
   imageboard_imageshow_adpater imageadapter;
    ImageButton board, profile,chatting;
    String name, password;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
      //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환

        //getPreferences ( ) 함수는 별도의 파일명을 지정하지 않으므로 자동으로 액티비티 이름의 파일 내에 저장합니다.



        //getSharedPreferences ( ) 함수는 파일명에 대한 정보를 매개변수로 지정하므로 해당 이름으로 XML 파일을 만듭니다.
        //다른 액티비티나 컴포넌트들이 데이터를 공유해서 이용할 수 있습니다. 데이터가 많은데 이를 각각의 파일로 나누어 구분하여 저장하고자 할 때 주로 이용합니다.

        SharedPreferences preferences = getSharedPreferences("shared",0);
        String text = preferences.getString("id","");
        Log.e("쉐어드로 가지고 와지는지 확인",text);


        imageView = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.person_recyclerview);
        add_btn = (ImageButton) findViewById(R.id.add_btn);


        board = findViewById(R.id.board);
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, board.class);
                startActivity(intent);
            }
        });


        chatting = findViewById(R.id.chatting);
        chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, chatting_page_recyclerview.class);
                startActivity(intent);
            }
        });



        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, profile.class);
                startActivity(intent);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        selectPerson();
        itemClickListener = new imageboard_text_Adapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = list.get(position).getId();
                String name = list.get(position).getName();
                Log.e("네임 데이터 확인", name);
                String hobby = list.get(position).getHobby();
                Log.e("취미 데이터 확인", hobby);
                String nick = list.get(position).getNick();
                Log.e("닉네임 데이터 확인", nick);



                Log.e(TAG, "id : " + id + ", name : " + name + ", hobby : " + hobby);
                Intent intent = new Intent(MainActivity.this, imageboard_select.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("hobby", hobby);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        };

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, imageboard.class);
                startActivity(intent);
            }
        });
    }




    //------------------------------------------------------------------------------------------------------------------------------------------
    //글을 가지고 올수있는 메소드
    private void selectPerson() {
        imageboard_text_ApiInterface imageboard_text_ApiInterface = imageboard_text_ApiClient.getApiClient().create(imageboard_text_ApiInterface.class);
        Call<List<imageboard_text_data>> call = imageboard_text_ApiInterface.getNameHobby();
        call.enqueue(new Callback<List<imageboard_text_data>>() {
            @Override
            public void onResponse(@NonNull Call<List<imageboard_text_data>> call, @NonNull Response<List<imageboard_text_data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());


                }
            }

            @Override
            public void onFailure(@NonNull Call<List<imageboard_text_data>> call, @NonNull Throwable t) {
                Log.e("selectPerson()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<imageboard_text_data> lists) {
        adapter = new imageboard_text_Adapter(this, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        list = lists;
    }

}