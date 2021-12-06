package com.mobatia.nasmanila.activities.cca.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.cca.model.CCAAttendanceModel;
import com.mobatia.nasmanila.manager.AppUtils;

import java.util.ArrayList;

/**
 * Created by gayatri on 22/3/17.
 */
public class CCAAttendenceListAdapter extends  RecyclerView.Adapter<CCAAttendenceListAdapter.MyViewHolder> {
    ArrayList<CCAAttendanceModel> mSocialMediaModels;
    Context mContext;
    int mPosition=0;
    int mChoiceStatus=0;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ccaDate;
        TextView ccaDateStatus;

        public MyViewHolder(View view) {
            super(view);

            ccaDate= (TextView) view.findViewById(R.id.ccaDate);
            ccaDateStatus= (TextView) view.findViewById(R.id.ccaDateStatus);


        }
    }
    public CCAAttendenceListAdapter(Context mContext, ArrayList<CCAAttendanceModel> mSocialMediaModels) {
        this.mContext = mContext;
      this.mSocialMediaModels=mSocialMediaModels;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cca_review_attendancelist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.ccaDate.setText(AppUtils.dateParsingTodd_MMM_yyyy(mSocialMediaModels.get(position).getDateAttend()));
        if (mSocialMediaModels.get(position).getStatusCCA().equalsIgnoreCase("u"))
        {
            holder.ccaDateStatus.setText("Upcoming");
            holder.ccaDateStatus.setTextColor(mContext.getResources().getColor(R.color.rel_six));

        }
        else  if (mSocialMediaModels.get(position).getStatusCCA().equalsIgnoreCase("p"))
        {
            holder.ccaDateStatus.setText("Present");
            holder.ccaDateStatus.setTextColor(mContext.getResources().getColor(R.color.nas_green));


        } else  if (mSocialMediaModels.get(position).getStatusCCA().equalsIgnoreCase("a"))
        {
            holder.ccaDateStatus.setText("Absent");
            holder.ccaDateStatus.setTextColor(mContext.getResources().getColor(R.color.rel_nine));

        }


    }


    @Override
    public int getItemCount() {


        return(mSocialMediaModels.size());
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
