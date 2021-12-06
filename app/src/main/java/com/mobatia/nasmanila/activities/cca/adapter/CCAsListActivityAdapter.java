package com.mobatia.nasmanila.activities.cca.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.cca.model.CCAModel;
import com.mobatia.nasmanila.manager.AppUtils;

import java.util.ArrayList;

/**
 * Created by gayatri on 22/3/17.
 */
public class CCAsListActivityAdapter extends  RecyclerView.Adapter<CCAsListActivityAdapter.MyViewHolder> {
//    ArrayList<String> mSocialMediaModels;
    ArrayList<CCAModel> mCCAmodelArrayList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listTxtView;
        TextView listTxtViewDate;
        ImageView statusImageView;

        public MyViewHolder(View view) {
            super(view);

            listTxtView= (TextView) view.findViewById(R.id.textViewCCAaItem);
            listTxtViewDate= (TextView) view.findViewById(R.id.textViewCCAaDateItem);
            statusImageView= (ImageView) view.findViewById(R.id.statusImageView);


        }
    }
//    public CCAsListActivityAdapter(Context mContext, ArrayList<String> mSocialMediaModels) {
//        this.mContext = mContext;
//      this.mSocialMediaModels=mSocialMediaModels;
//    }
    public CCAsListActivityAdapter(Context mContext, ArrayList<CCAModel> mCCAmodelArrayList) {
        this.mContext = mContext;
        this.mCCAmodelArrayList=mCCAmodelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cca_first_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {




//    holder.listTxtView.setText(mSocialMediaModels.get(position).toString());
    holder.listTxtView.setText(mCCAmodelArrayList.get(position).getTitle());
    holder.listTxtViewDate.setText(AppUtils.dateParsingTodd_MMM_yyyy(mCCAmodelArrayList.get(position).getFrom_date())+" to "+AppUtils.dateParsingTodd_MMM_yyyy(mCCAmodelArrayList.get(position).getTo_date()));
        if (mCCAmodelArrayList.get(position).getIsAttendee().equalsIgnoreCase("0")) {
            if (mCCAmodelArrayList.get(position).getIsSubmissionDateOver().equalsIgnoreCase("1")) {
                //closed
                holder.statusImageView.setImageResource(R.drawable.closed);
            }
            else {
                holder.statusImageView.setImageResource(R.drawable.edit);//edit
            }
        }
        else  if (mCCAmodelArrayList.get(position).getIsAttendee().equalsIgnoreCase("1")) {
            //approved
            holder.statusImageView.setImageResource(R.drawable.approve_new);

        } else  if (mCCAmodelArrayList.get(position).getIsAttendee().equalsIgnoreCase("2")) {
            //pending
            holder.statusImageView.setImageResource(R.drawable.pending);

        }

        }


    @Override
    public int getItemCount() {
//       System.out.println("Adapter---size" + mSocialMediaModels.size());

        return mCCAmodelArrayList.size();
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
