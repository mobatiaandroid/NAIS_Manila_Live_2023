package com.mobatia.nasmanila.activities.events.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.events.model.EventsModel;
import com.mobatia.nasmanila.manager.AppUtils;

import java.util.ArrayList;

/**
 * Created by gayatri on 23/3/17.
 */
public class EventslistAdapter extends RecyclerView.Adapter<EventslistAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<EventsModel> mStaffList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTxt,listTxtDate;
        ImageView iconImg;

        public MyViewHolder(View view) {
            super(view);

            mTitleTxt = (TextView) view.findViewById(R.id.listTxtTitle);
            listTxtDate = (TextView) view.findViewById(R.id.listTxtDate);
            iconImg = (ImageView) view.findViewById(R.id.iconImg);

        }
    }


    public EventslistAdapter(Context mContext,ArrayList<EventsModel> mStaffList) {
        this.mContext = mContext;
        this.mStaffList = mStaffList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_coming_up_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.iconImg.setVisibility(View.GONE);
        holder.mTitleTxt.setText(mStaffList.get(position).getTitle());
        if (mStaffList.get(position).getStart_date().equalsIgnoreCase(mStaffList.get(position).getEnd_date()))
        {
            holder.listTxtDate.setText(AppUtils.dateParsingTodd_MMM_yyyy(mStaffList.get(position).getStart_date()));
        }
        else {
            holder.listTxtDate.setText(AppUtils.dateParsingTodd_MMM_yyyy(mStaffList.get(position).getStart_date()) + " to " + AppUtils.dateParsingTodd_MMM_yyyy(mStaffList.get(position).getEnd_date()));
        }
        /*if(mStaffList.get(position).getType().equalsIgnoreCase("Video"))
        {
            holder.iconImg.setBackgroundResource(R.drawable.alerticon_video);
        }
        else   if(mStaffList.get(position).getType().equalsIgnoreCase("Image"))
        {
            holder.iconImg.setBackgroundResource(R.drawable.alerticon_image);

        }*/


    }


    @Override
    public int getItemCount() {
        return mStaffList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
