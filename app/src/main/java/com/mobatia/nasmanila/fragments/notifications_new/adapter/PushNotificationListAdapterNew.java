package com.mobatia.nasmanila.fragments.notifications_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.fragments.notifications_new.OnBottomReachedListenerNew;
import com.mobatia.nasmanila.fragments.notifications_new.model.PushNotificationModelNew;

import java.util.ArrayList;


public class PushNotificationListAdapterNew extends RecyclerView.Adapter<PushNotificationListAdapterNew.MyViewHolder>{
    private Context context;
    private ArrayList<PushNotificationModelNew> pushNotificationList;
    OnBottomReachedListenerNew onBottomReachedListener;

    public PushNotificationListAdapterNew(Context context, ArrayList<PushNotificationModelNew> mPushNotificationList) {
        this.context = context;
        this.pushNotificationList = mPushNotificationList;

    }
    public void setOnBottomReachedListener(OnBottomReachedListenerNew onBottomReachedListener) {

        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public PushNotificationListAdapterNew.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_adapter_pushlist_item_new, parent, false);

        return new PushNotificationListAdapterNew.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PushNotificationListAdapterNew.MyViewHolder holder, int position) {

//        if (position == pushNotificationList.size() - 1) {
//
//            onBottomReachedListener.onBottomReached(position);
//        }

        if (pushNotificationList.get(position).getType().equalsIgnoreCase("Video")) {

            holder.img.setImageResource(R.drawable.alerticon_video);

        } else if (pushNotificationList.get(position).getType().equalsIgnoreCase("Text")) {
            holder.img.setImageResource(R.drawable.alerticon_text);

        } else if (pushNotificationList.get(position).getType().equalsIgnoreCase("image")) {
            holder.img.setImageResource(R.drawable.alerticon_image);
        } else if (pushNotificationList.get(position).getType().equalsIgnoreCase("Voice")) {
            holder.img.setImageResource(R.drawable.alerticon_audio);

        }
        String message = pushNotificationList.get(position).getMessage();
        message = message.replaceAll("\\<.*?\\>", "");
        holder.title.setText(message);
        // holder.title.setText(pushNotificationList.get(position).getPushTitle());


    }

    @Override
    public int getItemCount() {
        return pushNotificationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.Img);
            title = itemView.findViewById(R.id.title);
        }
    }
}