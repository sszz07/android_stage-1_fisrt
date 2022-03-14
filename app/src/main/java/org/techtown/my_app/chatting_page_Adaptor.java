package org.techtown.my_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chatting_page_Adaptor extends RecyclerView.Adapter<chatting_page_Adaptor.chattingViewHolder> {
    private Context context;
    private List<chatting_page_data> list;
    private ItemClickListener itemClickListener;
    private String Me;

    public chatting_page_Adaptor(Context context, List<chatting_page_data> list, ItemClickListener itemClickListener,String Me) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
        this.Me=Me;
    }


    @NonNull
    @Override
    public chattingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatting_item, parent, false);
        //왜 chatting_page_Adaptor이것을 넣으면 에러가 날까?
        return new chattingViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull chattingViewHolder holder, int position) {
        chatting_page_data page_data = list.get(position);
        String 나 = page_data.getME();
        System.out.println(나);

        String 너 = page_data.getYOUR();
        System.out.println(너+"채팅 페이지 어뎁터");


        //나와
        if (나.equals(Me)) {

            holder.last_answer.setText(page_data.getLast_answer());
            holder.chatting_other_user.setText(page_data.getYOUR());
            if(!나.equals(Me)) {
                holder.chatting_other_user.setVisibility(View.GONE);
                holder.last_answer.setVisibility(View.GONE);
            }
        }
        //유어 컬럼에 너가 있으면 가져와
        else if(너.equals(Me)){

            holder.last_answer.setText(page_data.getLast_answer());
            //getME는 내가 다른 사람과 대화한것을 가지고 오기 위해서
            holder.chatting_other_user.setText(page_data.getME());
            if(!너.equals(Me)) {
                holder.chatting_other_user.setVisibility(View.GONE);
                holder.last_answer.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class chattingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //public LinearLayout linearLayout; 이게 뭐길래 아이템을 클릭을 했는데 안열릴까?
        public LinearLayout linearLayout;
        public TextView last_answer,last_answer2, chatting_other_user;
        ItemClickListener itemClickListener;


        @SuppressLint("CutPasteId")
        public chattingViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            super(view);
            linearLayout = view.findViewById(R.id.chatting_linear_layout);
            last_answer = view.findViewById(R.id.chatting_last_answer);
            last_answer2 = view.findViewById(R.id.chatting_last_answer);
            chatting_other_user = view.findViewById(R.id.chatting_other_user);


            this.itemClickListener = itemClickListener;
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
