package com.mobatia.nasmanila.fragments.sports.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.fragments.parents_evening.model.StudentModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StrudentSpinnerAdapter extends RecyclerView.Adapter<StrudentSpinnerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<StudentModel> mStudentList;
    String dept;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTxt,listTxtClass;
        ImageView imgIcon;

        public MyViewHolder(View view) {
            super(view);

            mTitleTxt= (TextView) view.findViewById(R.id.listTxtTitle);
            listTxtClass= (TextView) view.findViewById(R.id.listTxtClass);
            imgIcon=(ImageView) view.findViewById(R.id.imagicon);
        }
    }


    public StrudentSpinnerAdapter(Context mContext,ArrayList<StudentModel> mStudentList) {
        this.mContext = mContext;
        this.mStudentList = mStudentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mTitleTxt.setText(mStudentList.get(position).getmName());
        holder.imgIcon.setVisibility(View.VISIBLE);
        holder.listTxtClass.setText(mStudentList.get(position).getmClass());
        if (!mStudentList.get(position).getmPhoto().equals("")) {

            Picasso.with(mContext).load(AppUtils.replace(mStudentList.get(position).getmPhoto().toString())).placeholder(R.drawable.student).fit().into(holder.imgIcon);
        }
        else

        {

            holder.imgIcon.setImageResource(R.drawable.student);
        }
    }


    @Override
    public int getItemCount() {
        return mStudentList.size();
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
