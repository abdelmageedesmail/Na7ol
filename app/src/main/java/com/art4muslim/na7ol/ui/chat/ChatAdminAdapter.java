package com.art4muslim.na7ol.ui.chat;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.ChatModel;
import com.art4muslim.na7ol.internet.model.MessagesResponse;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAdminAdapter extends RecyclerView.Adapter<ChatAdminAdapter.ChatHolder> {
    Context context;
    List<MessagesResponse.ReturnsEntity> arrayList;
    PrefrencesStorage storage;
    private MediaPlayer mediaPlayer;
    private Runnable runnable;
    private final Handler handler = new Handler();
    private Geocoder geocoder;
    private List<Address> addresses;
    private String knownName;
    private double lat, lng;
    private GoogleMap gMap;
    private String messageLocation;

    public ChatAdminAdapter(@NonNull Context context, List<MessagesResponse.ReturnsEntity> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        storage = new PrefrencesStorage(context);
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, viewGroup, false);
        ChatHolder holder = new ChatHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final ChatHolder chatHolder, int i) {
//        chatHolder.frChat.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        LinearLayout.LayoutParams paramsMsg = new LinearLayout.

                LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (arrayList.get(i).getMsg_user_id().equals(storage.getId())) {
            chatHolder.ivProfile.setVisibility(View.GONE);
            chatHolder.tvMsg.setTextColor(Color.WHITE);
            chatHolder.liContainer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
//            chatHolder.frChat.setGravity(Gravity.START);
//            chatHolder.tvMsg.setGravity(Gravity.START);
            chatHolder.tvMsg.setTextColor(Color.BLACK);
//            if (arrayList.get(i).getFile().isEmpty()) {
            chatHolder.frShape.setBackgroundResource(R.drawable.chat_to);
//            } else {
//                chatHolder.frShape.setBackgroundResource(R.drawable.item_image);
//            }
            paramsMsg.gravity = Gravity.END;
            chatHolder.frChat.setLayoutParams(paramsMsg);
        } else {
            chatHolder.ivProfile.setVisibility(View.VISIBLE);
            chatHolder.liContainer.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//            chatHolder.frChat.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
//            params.weight = 1.0f;
//            params.gravity = Gravity.END;
//            chatHolder.frChat.setGravity(Gravity.END);
//            chatHolder.tvMsg.setGravity(Gravity.START);
            chatHolder.tvMsg.setTextColor(Color.WHITE);
            chatHolder.frShape.setBackgroundResource(R.drawable.chat_from);
            paramsMsg.gravity = Gravity.START;
            chatHolder.frChat.setLayoutParams(paramsMsg);
            chatHolder.ivProfile.setImageResource(R.drawable.login_header);
        }
        chatHolder.tvMsg.setText(arrayList.get(i).getMsg_text());

        if (arrayList.get(i).getImageUrl().isEmpty()) {
            chatHolder.ivImage.setVisibility(View.GONE);
        } else {
            chatHolder.tvMsg.setText("");
            chatHolder.ivImage.setVisibility(View.VISIBLE);
            Picasso.with(context).load(arrayList.get(i).getImageUrl()).into(chatHolder.ivImage);
        }

        chatHolder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ImagePreview.class);
//                intent.putExtra("image", arrayList.get(chatHolder.getAdapterPosition()).getFile());
//                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        TextView tvMsg;
        LinearLayout frChat, liContainer;
        FrameLayout frShape;
        ImageView ivImage, ivProfile;
        MapView map_view;
        GoogleMap gmap;
        private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tvMsg);
            frChat = itemView.findViewById(R.id.frChat);
            liContainer = itemView.findViewById(R.id.liContainer);
            frShape = itemView.findViewById(R.id.frShape);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivProfile = itemView.findViewById(R.id.ivProfile);

//            map_view = itemView.findViewById(R.id.map_view);
//            Bundle mapViewBundle = null;
//            if (ChatActivity.savedInstance != null) {
//                mapViewBundle = ChatActivity.savedInstance.getBundle(MAP_VIEW_BUNDLE_KEY);
//            }
//            map_view.onCreate(mapViewBundle);
//            map_view.getMapAsync(this);
//            map_view.setClickable(false);
//            map_view.onResume();
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
//            gMap = googleMap;
//            LatLng ny = new LatLng(lat, lng);
//            gMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
//            gMap.clear();
//            gMap.getUiSettings().setMapToolbarEnabled(false);
//            CameraPosition cameraPosition = new CameraPosition.Builder().target(ny).zoom(14).build();
//            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}
