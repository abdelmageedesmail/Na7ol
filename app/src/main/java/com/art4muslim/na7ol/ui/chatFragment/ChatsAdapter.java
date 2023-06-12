package com.art4muslim.na7ol.ui.chatFragment;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.ChatsModel;
import com.art4muslim.na7ol.ui.chat.ChatActivity;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.MyHolder> {

    Context context;
    List<ChatsModel.ReturnsEntity> list;
    PrefrencesStorage storage;

    public ChatsAdapter(Context context, List<ChatsModel.ReturnsEntity> list) {
        this.context = context;
        this.list = list;
        storage = new PrefrencesStorage(context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_fragment, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int i) {
        if (!list.get(i).getMsg_user_image().isEmpty()) {
            Picasso.with(context).load(list.get(i).getMsg_user_image()).into(myHolder.ivProfile);
        }
        myHolder.tvName.setText(list.get(i).getMsg_user_name());
        myHolder.tvMessage.setText(list.get(i).getMsg_text());
        myHolder.tvTime.setText(list.get(i).getCreated_since());
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                if (list.get(myHolder.getAdapterPosition()).getMsg_is_administrative().equals("1")) {
                    intent.putExtra("isAdmin", "1");
                }
                intent.putExtra("to", list.get(myHolder.getAdapterPosition()).getMsg_user_id());
//                storage.getKey("name")
                intent.putExtra("userName", list.get(myHolder.getAdapterPosition()).getMsg_user_name());
                intent.putExtra("userImage", list.get(myHolder.getAdapterPosition()).getMsg_user_image());
                intent.putExtra("fromImage", storage.getKey("photo"));
                intent.putExtra("tripId", list.get(myHolder.getAdapterPosition()).getMsg_service_id());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivProfile)
        ImageView ivProfile;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvMessage)
        TextView tvMessage;
        @BindView(R.id.tvTime)
        TextView tvTime;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
