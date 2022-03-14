package org.techtown.my_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class chatting_room extends AppCompatActivity {
    public static final String TAG = "chatting_room";
    private Handler mHandler;
    Socket socket;
    PrintWriter sendWriter;
    private String ip = "192.168.56.1";
    private int port = 1234;
    int room_number;
    String UserID;
    Button chatbutton;
    TextView chatView;
    EditText message;
    String sendmsg, your;
    String read;


    private RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<chatting_room_data> chatList;

//    @Override
//    protected void onPause() {
//        super.onPause();
//        try {
//            sendWriter.close();
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);
        mHandler = new Handler();





//        Intent intent = getIntent();
//        your = intent.getStringExtra("your");


        //서버에 보내고 디비에 저장을 하기 위해서 나의 아이디값을 보낸다
        SharedPreferences preferences = getSharedPreferences("shared", 0);
        String me = preferences.getString("id", "");

        Intent intent = getIntent();
        room_number = intent.getIntExtra("room_number",0);
        Log.e(TAG, String.valueOf(room_number));





        //주석하는 이유는 어댑터에 넣어놓았다
        chatView = (TextView) findViewById(R.id.TextView_msg);
        message = (EditText) findViewById(R.id.EditText_chat);

//        textView.setText(UserID);
        chatbutton = (Button) findViewById(R.id.Button_send);
        //---------------------------------------------------------------------------------


        //리사이클러뷰
        mRecyclerView = findViewById(R.id.my_recycler_view);
        Log.e(TAG, "15");
        //?
        mRecyclerView.setHasFixedSize(true);
        Log.e(TAG, "16");

        //레이아웃을 세로로 보이게 하겠다
        mLayoutManager = new LinearLayoutManager(this);
        Log.e(TAG, "17");

        //윗줄에 있는것을 제대로 보이게 하겠다
        mRecyclerView.setLayoutManager(mLayoutManager);
        Log.e(TAG, "18");

        //2.25 일단 어레이에 데이터가 쌓여있지는 않는다
        chatList = new ArrayList<>();
        for (int i = 0; i <= chatList.size(); i++) {
            Log.e(TAG, String.valueOf(chatList.size()));
        }
        //어댑터를 담겠다
        mAdapter = new chatting_room_Adapter(chatList, chatting_room.this, UserID);
        Log.e(TAG, "20");

        //메세지값 어댑터에 담기
        mRecyclerView.setAdapter(mAdapter);
        Log.e(TAG, "21");
        //---------------------------------------------------------------------------------


        //내가 데이터를 받는곳
        new Thread() {
            public void run() {
                try {
                    InetAddress serverAddr = InetAddress.getByName(ip);
                    socket = new Socket(serverAddr, port);
                    Log.e(TAG, "1");

                    sendWriter = new PrintWriter(socket.getOutputStream());
                    Log.e(TAG, "2");

                    //서버에 데이터를 받는곳
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Log.e(TAG, "3");

                    while (true) {
                        //버퍼리더에 문자열이 아닌 문자로 값을 받아와줌으로 read의 스트링값으로
                        //바뀌게 되었다
                        read = input.readLine();


                        //밑에 메소드를 만들었는데 안드로이드는 스레드를 실행을 할수가 없고 무조건 핸들러를 거쳐서 스레드를 이용할수가 있다
//                        chat_messege(read);
                        //리사이클러뷰를 실행하기 위해 메소드를 만듬
                        Log.e(TAG, "4");

                        System.out.println("TTTTTTTT" + read + "제대로 데이터가 받아와 지는가?");
                        if (read != null) {
                            mHandler.post(new msgUpdate(read));
                            //밑에 메소드를 만들었는데 안드로이드는 스레드를 실행을 할수가 없고 무조건 핸들러를 거쳐서 스레드를 이용할수가 있다
//                        chat_messege(read);
                            //밑에 만들어 놓은 핸들러를 거쳐서 실행을 할수가 있다
                            Log.e(TAG, "5");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        //내가 데이터를 입력하고 보내는곳
        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "6");
                sendmsg = message.getText().toString();
                Log.e(TAG, sendmsg);

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            if (sendmsg != null) {
                                Log.e(TAG, "7");
                                Log.e(TAG, "8");
                                Log.e(TAG, "9");
                                Log.e(TAG, me + "DTO에 값을 받아와 지는지 확인해보기");
                                Log.e(TAG, "10");
                                Log.e(TAG, sendmsg);
                                Log.e(TAG, "11");
                                Log.e(TAG, "12");
                                sendWriter.println(sendmsg);
                                Log.e(TAG, "13");
                                sendWriter.flush();
                                Log.e(TAG, "14");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }


    public void chat_messege(String messeage) {
        Log.e(TAG, messeage + "read에서 받아온 값을 잘 받아와 지는지 확인하느곳");
        //리사이클러뷰로 실제 앱에서 보이는곳

    }


    class msgUpdate implements Runnable {
        private String msg;

        public msgUpdate(String str) {
            Log.e(TAG, "22");
            this.msg = str;
        }

        @Override
        public void run() {
            Log.e(TAG, msg + "맨밑에 런쪽에 있는 변수값 확인");
            chat_messege(msg);
            chatting_room_data chat = new chatting_room_data();
            chat.setMsg(read);
            ((chatting_room_Adapter) mAdapter).addChat(chat);

        }
    }

}