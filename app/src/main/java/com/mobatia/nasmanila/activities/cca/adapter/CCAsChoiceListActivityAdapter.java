package com.mobatia.nasmanila.activities.cca.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.cca.model.WeekListModel;
import com.mobatia.nasmanila.activities.cca.CCASelectionActivity;
import com.mobatia.nasmanila.activities.cca.model.CCAchoiceModel;
import com.mobatia.nasmanila.appcontroller.AppController;
import com.mobatia.nasmanila.manager.AppUtils;

import java.util.ArrayList;

/**
 * Created by gayatri on 22/3/17.
 */
public class CCAsChoiceListActivityAdapter extends RecyclerView.Adapter<CCAsChoiceListActivityAdapter.MyViewHolder> {
    //    ArrayList<String> mSocialMediaModels;
    ArrayList<CCAchoiceModel> mCCAmodelArrayList;
    Context mContext;
    int dayPosition = 0;
    int choicePosition = 0;
    ArrayList<WeekListModel> weekList;
    RecyclerView recyclerWeek;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listTxtView;
        TextView textViewCCAaDateItem;
        TextView textViewCCAVenue;
        ImageView confirmationImageview;

        public MyViewHolder(View view) {
            super(view);

            textViewCCAaDateItem = (TextView) view.findViewById(R.id.textViewCCAaDateItem);
            textViewCCAVenue = (TextView) view.findViewById(R.id.textViewCCAVenue);
            listTxtView = (TextView) view.findViewById(R.id.textViewCCAaItem);
            confirmationImageview = (ImageView) view.findViewById(R.id.confirmationImageview);


        }
    }

    //    public CCAsListActivityAdapter(Context mContext, ArrayList<String> mSocialMediaModels) {
//        this.mContext = mContext;
//      this.mSocialMediaModels=mSocialMediaModels;
//    }
    public CCAsChoiceListActivityAdapter(Context mContext, ArrayList<CCAchoiceModel> mCCAmodelArrayList) {
        this.mContext = mContext;
        this.mCCAmodelArrayList = mCCAmodelArrayList;
    }

    public CCAsChoiceListActivityAdapter(Context mContext, ArrayList<CCAchoiceModel> mCCAmodelArrayList, int mdayPosition, ArrayList<WeekListModel> mWeekList) {
        this.mContext = mContext;
        this.mCCAmodelArrayList = mCCAmodelArrayList;
        this.dayPosition = mdayPosition;
        this.weekList = mWeekList;

    }

    public CCAsChoiceListActivityAdapter(Context mContext, ArrayList<CCAchoiceModel> mCCAmodelArrayList, int mdayPosition, ArrayList<WeekListModel> mWeekList, int mChoicePosition, RecyclerView recyclerWeek) {
        this.mContext = mContext;
        this.mCCAmodelArrayList = mCCAmodelArrayList;
        this.dayPosition = mdayPosition;
        this.weekList = mWeekList;
        this.choicePosition = mChoicePosition;
        this.recyclerWeek = recyclerWeek;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_ccalist_activity, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        holder.confirmationImageview.setVisibility(View.VISIBLE);
        if (mCCAmodelArrayList.get(position).getVenue().equalsIgnoreCase("0")|| (mCCAmodelArrayList.get(position).getVenue().equalsIgnoreCase("")))
        {
            holder.textViewCCAVenue.setVisibility(View.GONE);

        }
        else
        {

            holder.textViewCCAVenue.setText(mCCAmodelArrayList.get(position).getVenue());
            holder.textViewCCAVenue.setVisibility(View.VISIBLE);

        }
        if (choicePosition == 0) {
            if (mCCAmodelArrayList.get(position).getStatus().equalsIgnoreCase("0")) {
                holder.confirmationImageview.setBackgroundResource(R.drawable.close_icon_with_white);
//                AppController.weekList.get(dayPosition).setChoiceStatus("0");

            } else {
                holder.confirmationImageview.setBackgroundResource(R.drawable.participatingsmallicon);
                AppController.weekList.get(dayPosition).setChoiceStatus("1");
                CCASelectionActivity.CCADetailModelArrayList.get(CCASelectionActivity.ccaDetailpos).setChoice1(mCCAmodelArrayList.get(position).getCca_item_name());
                CCASelectionActivity.CCADetailModelArrayList.get(CCASelectionActivity.ccaDetailpos).setChoice1Id(mCCAmodelArrayList.get(position).getCca_details_id());
                CCAsWeekListAdapter mCCAsWeekListAdapter = new CCAsWeekListAdapter(mContext, AppController.weekList, dayPosition);
                mCCAsWeekListAdapter.notifyDataSetChanged();
                recyclerWeek.setAdapter(mCCAsWeekListAdapter);
            }
        } else {
            if (mCCAmodelArrayList.get(position).getStatus().equalsIgnoreCase("0")) {
                holder.confirmationImageview.setBackgroundResource(R.drawable.close_icon_with_white);
//                AppController.weekList.get(dayPosition).setChoiceStatus1("0");

            } else {
                holder.confirmationImageview.setBackgroundResource(R.drawable.participatingsmallicon);
                AppController.weekList.get(dayPosition).setChoiceStatus1("1");
                CCASelectionActivity.CCADetailModelArrayList.get(CCASelectionActivity.ccaDetailpos).setChoice2(mCCAmodelArrayList.get(position).getCca_item_name());
                CCASelectionActivity.CCADetailModelArrayList.get(CCASelectionActivity.ccaDetailpos).setChoice2Id(mCCAmodelArrayList.get(position).getCca_details_id());
                CCAsWeekListAdapter mCCAsWeekListAdapter = new CCAsWeekListAdapter(mContext, AppController.weekList, dayPosition);
                mCCAsWeekListAdapter.notifyDataSetChanged();
                recyclerWeek.setAdapter(mCCAsWeekListAdapter);
            }
        }
        for (int j = 0; j < AppController.weekList.size(); j++) {
            if (AppController.weekList.get(j).getChoiceStatus().equalsIgnoreCase("0") || AppController.weekList.get(j).getChoiceStatus1().equalsIgnoreCase("0")) {
                CCASelectionActivity.filled = false;
                break;
            } else {
                CCASelectionActivity.filled = true;
            }
            if (!( CCASelectionActivity.filled)) {
                break;
            }

        }
        if (CCASelectionActivity.filled) {
            CCASelectionActivity.submitBtn.getBackground().setAlpha(255);
            CCASelectionActivity.submitBtn.setVisibility(View.VISIBLE);
            CCASelectionActivity.nextBtn.getBackground().setAlpha(255);
            CCASelectionActivity.nextBtn.setVisibility(View.GONE);

        } else {
            CCASelectionActivity.submitBtn.getBackground().setAlpha(150);
            CCASelectionActivity.submitBtn.setVisibility(View.INVISIBLE);
            CCASelectionActivity.nextBtn.getBackground().setAlpha(255);
            CCASelectionActivity.nextBtn.setVisibility(View.VISIBLE);

        }
//    holder.listTxtView.setText(mSocialMediaModels.get(position).toString());
        holder.listTxtView.setText(mCCAmodelArrayList.get(position).getCca_item_name());
        if (mCCAmodelArrayList.get(position).getCca_item_start_time()!=null && mCCAmodelArrayList.get(position).getCca_item_end_time()!=null)
        {
            holder.textViewCCAaDateItem.setVisibility(View.VISIBLE);

            holder.textViewCCAaDateItem.setText("("+AppUtils.convertTimeToAMPM(mCCAmodelArrayList.get(position).getCca_item_start_time())+" - "+AppUtils.convertTimeToAMPM(mCCAmodelArrayList.get(position).getCca_item_end_time())+")");

        }else if (mCCAmodelArrayList.get(position).getCca_item_start_time()!=null)
        {
            holder.textViewCCAaDateItem.setVisibility(View.VISIBLE);

            holder.textViewCCAaDateItem.setText("("+AppUtils.convertTimeToAMPM(mCCAmodelArrayList.get(position).getCca_item_start_time())+")");
        }else if (mCCAmodelArrayList.get(position).getCca_item_end_time()!=null)
        {
            holder.textViewCCAaDateItem.setVisibility(View.VISIBLE);

            holder.textViewCCAaDateItem.setText("("+AppUtils.convertTimeToAMPM(mCCAmodelArrayList.get(position).getCca_item_end_time())+")");
        }
        else
        {
            holder.textViewCCAaDateItem.setVisibility(View.GONE);

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
