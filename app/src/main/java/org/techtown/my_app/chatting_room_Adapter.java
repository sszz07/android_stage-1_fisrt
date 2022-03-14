package org.techtown.my_app;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class chatting_room_Adapter extends RecyclerView.Adapter<chatting_room_Adapter.MyViewHolder> {
    private List<chatting_room_data> mDataset;
    private String myNickName;


    public chatting_room_Adapter(List<chatting_room_data> myDataset, Context context, String myNickName) {
        mDataset = myDataset;
        this.myNickName = myNickName;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        chatting_room_data chat = mDataset.get(position);
//        holder.TextView_nickname.setText(chat.getNickname());
        holder.TextView_msg.setText(chat.getMsg());
        Log.e(" ", "14");
        if (chat.getMsg().equals(this.myNickName)) {
            Log.e(" ", "이프에 들어가는지 확인");
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        } else {
            Log.e(" ", "엘스에 들어가는지 확인");
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }


    @Override
    public int getItemCount() {
        //삼항 연산자
        return mDataset == null ? 0 : mDataset.size();
    }

    public chatting_room_data getChat(int position) {
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(chatting_room_data chat) {
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1); //갱신
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView TextView_msg;
        public View rootView;

        public MyViewHolder(View v) {
            super(v);
            TextView_msg = v.findViewById(R.id.TextView_msg);
            rootView = v;
        }
    }


}

