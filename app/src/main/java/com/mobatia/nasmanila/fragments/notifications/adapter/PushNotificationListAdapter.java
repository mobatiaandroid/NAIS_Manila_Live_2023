/**
 *
 */
package com.mobatia.nasmanila.fragments.notifications.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.fragments.notifications.OnBottomReachedListener;
import com.mobatia.nasmanila.fragments.notifications.model.PushNotificationModel;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc

/**
 * The Class AlertAdapter.
 *
 * @author rahul
 */
public class PushNotificationListAdapter extends RecyclerView.Adapter<PushNotificationListAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<PushNotificationModel> pushNotificationList;
    OnBottomReachedListener onBottomReachedListener;

    public PushNotificationListAdapter(Context context, ArrayList<PushNotificationModel> mPushNotificationList) {
        this.context = context;
        this.pushNotificationList = mPushNotificationList;

    }
    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {

        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public PushNotificationListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_adapter_pushlist_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PushNotificationListAdapter.MyViewHolder holder, int position) {

        if (position == pushNotificationList.size() - 1) {

            onBottomReachedListener.onBottomReached(position);
        }

        if (pushNotificationList.get(position).getPushType().equalsIgnoreCase("Video")) {

            holder.img.setImageResource(R.drawable.alerticon_video);

        } else if (pushNotificationList.get(position).getPushType().equalsIgnoreCase("Text")) {
            holder.img.setImageResource(R.drawable.alerticon_text);

        } else if (pushNotificationList.get(position).getPushType().equalsIgnoreCase("image")) {
            holder.img.setImageResource(R.drawable.alerticon_image);
        } else if (pushNotificationList.get(position).getPushType().equalsIgnoreCase("Voice")) {
            holder.img.setImageResource(R.drawable.alerticon_audio);

        }
        holder.title.setText(pushNotificationList.get(position).getHeadTitle());
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
//public class PushNotificationListAdapters extends BaseAdapter {
//
//    /** The context. */
//    private Context context;
//
//    /** The inflater. */
//    private LayoutInflater inflater;
//
//    /** The view. */
//    private View view;
//
//    /** The profile name. */
//    private TextView profileName;
//
//    /** The title. */
//    private TextView title;
//
//    /** The news date. */
//    private TextView newsDate;
//
//    /** The Img. */
//    private ImageView Img;
//
//    /** The alert list. */
//    private ArrayList<PushNotificationModel> pushNotificationList;
//
//    /** The progress img. */
//    ProgressBar progressImg;
//
//    /** The Pagination listener. */
//    OnBottomReachedListener onBottomReachedListener;
//
//
//    public PushNotificationListAdapters(Context context, ArrayList<PushNotificationModel> mPushNotificationList) {
//        this.context = context;
//        this.pushNotificationList = mPushNotificationList;
//
//    }
//
//    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
//
//        this.onBottomReachedListener = onBottomReachedListener;
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see android.widget.Adapter#getCount()
//     */
//    @Override
//    public int getCount() {
//
//        return pushNotificationList.size();
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see android.widget.Adapter#getItem(int)
//     */
//    @Override
//    public Object getItem(int position) {
//
//        return pushNotificationList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        inflater = LayoutInflater.from(context);
//        view = inflater.inflate(R.layout.custom_adapter_pushlist_item, null);
//        Img = (ImageView) view.findViewById(R.id.Img);
//
//        title = (TextView) view.findViewById(R.id.title);
//        if (position == pushNotificationList.size() - 1) {
//
//            onBottomReachedListener.onBottomReached(position);
//        }
//
//        if (pushNotificationList.get(position).getPushType().equalsIgnoreCase("video")) {
//
//            Img.setImageResource(R.drawable.alerticon_video);
//
//        } else if (pushNotificationList.get(position).getPushType().equalsIgnoreCase("text")) {
//            Img.setImageResource(R.drawable.alerticon_text);
//
//        } else if (pushNotificationList.get(position).getPushType().equalsIgnoreCase("image")) {
//            Img.setImageResource(R.drawable.alerticon_image);
//        } else if (pushNotificationList.get(position).getPushType().equalsIgnoreCase("audio")) {
//            Img.setImageResource(R.drawable.alerticon_audio);
//
//        }
//        title.setText(pushNotificationList.get(position).getHeadTitle());
//
//        return view;
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//
//}
