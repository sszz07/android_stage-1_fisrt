package org.techtown.my_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class board_Adapter extends RecyclerView.Adapter<board_Adapter.PersonViewHolder> {
    private Context context;
    private List<board_data> list;
    private ItemClickListener itemClickListener;


    public board_Adapter(Context context, List<board_data> list, ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }




    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.board_item, parent, false);
        return new PersonViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
       board_data board = list.get(position);
        //클라이드
        holder.subject.setText(board.getSubject());
        holder.writer.setText(board.getId());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //public LinearLayout linearLayout; 이게 뭐길래 아이템을 클릭을 했는데 안열릴까?
        public LinearLayout linearLayout;
        public TextView subject, writer;
        ItemClickListener itemClickListener;


        public PersonViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            super(view);
            linearLayout = view.findViewById(R.id.linear_layout);
            subject = view.findViewById(R.id.board_item_subject);
            writer = view.findViewById(R.id.board_item_writer);


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
