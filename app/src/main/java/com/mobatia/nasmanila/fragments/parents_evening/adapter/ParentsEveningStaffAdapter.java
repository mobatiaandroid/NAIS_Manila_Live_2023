package com.mobatia.nasmanila.fragments.parents_evening.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.fragments.parents_evening.model.StaffModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gayatri on 22/3/17.
 */
public class ParentsEveningStaffAdapter extends  RecyclerView.Adapter<ParentsEveningStaffAdapter.MyViewHolder> {
    ArrayList<StaffModel> mSocialMediaModels;
    Context mContext;
    Drawable iconImage;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIcon;
        TextView listTxtView;

        public MyViewHolder(View view) {
            super(view);

          imgIcon= (ImageView) view.findViewById(R.id.imagicon);
            listTxtView= (TextView) view.findViewById(R.id.listTxtTitle);


        }
    }
    public ParentsEveningStaffAdapter(Context mContext, ArrayList<StaffModel> mSocialMediaModels) {
        this.mContext = mContext;
      this.mSocialMediaModels=mSocialMediaModels;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_staff_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {



//   holder.imgIcon.setVisibility(View.GONE);
//   holder.imgIcon.setBackgroundResource(R.drawable.roundfb);
    holder.listTxtView.setText(mSocialMediaModels.get(position).getmName());
        if (!mSocialMediaModels.get(position).getmPhoto().equals("")) {

            Picasso.with(mContext).load(AppUtils.replace(mSocialMediaModels.get(position).getmPhoto().toString())).placeholder(R.drawable.staff).fit().into(holder.imgIcon);
        }
        else

        {

            holder.imgIcon.setImageResource(R.drawable.staff);
        }

    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return mSocialMediaModels.size();
    }

}
