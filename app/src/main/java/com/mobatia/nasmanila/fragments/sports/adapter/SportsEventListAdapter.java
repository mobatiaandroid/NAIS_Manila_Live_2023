package com.mobatia.nasmanila.fragments.sports.adapter;

/**
 * Created by gayatri on 28/3/17.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.fragments.sports.SportsDetailActivity;
import com.mobatia.nasmanila.fragments.sports.model.SportsModel;
import com.mobatia.nasmanila.manager.AppUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gayathri on 28th MAr 2017.
 */
public class SportsEventListAdapter extends RecyclerView.Adapter<SportsEventListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<SportsModel> mEventList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView start_date,event,end_date,start_time,end_time;
        LinearLayout buttonholder;
        LinearLayout bubbleLinear;

        public MyViewHolder(View view) {
            super(view);

            start_date = (TextView) view.findViewById(R.id.start_date);
            end_date= (TextView) view.findViewById(R.id.end_date);
            start_time= (TextView) view.findViewById(R.id.start_time);
            end_time= (TextView) view.findViewById(R.id.end_time);
            event = (TextView) view.findViewById(R.id.event);
            buttonholder= (LinearLayout) view.findViewById(R.id.buttonholder);
            bubbleLinear= (LinearLayout) view.findViewById(R.id.bubbleLinear);

        }
    }


    public SportsEventListAdapter(Context mContext,ArrayList<SportsModel> eventList) {
        this.mContext = mContext;
        this.mEventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_adapter_sports_event_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.start_date.setText(separateDate(mEventList.get(position).getSports_start_date()));
        holder.start_time.setText(separateTime(mEventList.get(position).getSports_start_date()));
        holder.end_date.setText(separateDate(mEventList.get(position).getSports_end_date()));
        holder.end_time.setText(separateTime(mEventList.get(position).getSports_end_date()));

        holder.event.setText(mEventList.get(position).getSports_name());
        holder.bubbleLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent=new Intent(mContext, SportsDetailActivity.class);
                sIntent.putExtra("event_id",mEventList.get(position).getSports_id());
                mContext.startActivity(sIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    private String separateDate(String inputDate){
        String mDate="";

        try {
            Log.d("Time here ", "InputDate--" + inputDate);
            Date date;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            date = formatter.parse(inputDate);
            //Subtracting 6 hours from selected time
            long time = date.getTime();

            SimpleDateFormat formatterFullDate = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            mDate = formatterFullDate.format(time);
            Log.e("Date ", mDate);


        } catch (Exception e) {
            Log.d("Exception", "" + e);
        }
        return mDate;
    }

    private String separateTime(String inputDate){
        String mTime="";
        try {
            Log.d("Time here ", "InputDate--"+inputDate);
            Date date;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            date = formatter.parse(inputDate);
            //Subtracting 6 hours from selected time
            long time = date.getTime();

            SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            mTime = formatterTime.format(time);
            if (mTime.contains("a.m.")) {
                mTime=AppUtils.replaceAmdot(mTime);


            } else  if (mTime.contains("p.m.")){
                mTime=AppUtils.replacePmdot(mTime);
            }


            Log.e("Time ",mTime );

        } catch (Exception e) {
            Log.d("Exception", "" + e);
        }
        return mTime;
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
