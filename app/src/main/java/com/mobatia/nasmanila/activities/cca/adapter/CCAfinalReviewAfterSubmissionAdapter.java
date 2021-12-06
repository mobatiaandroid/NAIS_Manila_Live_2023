package com.mobatia.nasmanila.activities.cca.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.cca.model.CCAReviewAfterSubmissionModel;
import com.mobatia.nasmanila.manager.AppUtils;

import java.util.ArrayList;

/**
 * Created by gayatri on 22/3/17.
 */
public class CCAfinalReviewAfterSubmissionAdapter extends RecyclerView.Adapter<CCAfinalReviewAfterSubmissionAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<CCAReviewAfterSubmissionModel> mCCADetailModelArrayList;
    Dialog dialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCCADay;
        TextView textViewCCAChoice1;
        TextView textViewCCAChoice2;
        ImageView attendanceListIcon;
        LinearLayout linearChoice1, linearChoice2;
        TextView textViewCCAaDateItemChoice1;
        TextView textViewCCAaDateItemChoice2;
        TextView textViewCCAVenue;
        TextView textViewCCAVenueChoice2;
        public MyViewHolder(View view) {
            super(view);
            textViewCCAaDateItemChoice1= (TextView) view.findViewById(R.id.textViewCCAaDateItemChoice1);
            textViewCCAaDateItemChoice2= (TextView) view.findViewById(R.id.textViewCCAaDateItemChoice2);
            textViewCCADay = (TextView) view.findViewById(R.id.textViewCCADay);
            textViewCCAChoice1 = (TextView) view.findViewById(R.id.textViewCCAChoice1);
            textViewCCAChoice2 = (TextView) view.findViewById(R.id.textViewCCAChoice2);
            attendanceListIcon = (ImageView) view.findViewById(R.id.attendanceListIcon);
            linearChoice1 = (LinearLayout) view.findViewById(R.id.linearChoice1);
            linearChoice2 = (LinearLayout) view.findViewById(R.id.linearChoice2);
            textViewCCAVenue= (TextView) view.findViewById(R.id.textViewCCAVenue);
            textViewCCAVenueChoice2= (TextView) view.findViewById(R.id.textViewCCAVenueChoice2);

        }
    }

    //    public CCAfinalReviewAdapter(Context mContext) {
//        this.mContext = mContext;
//    }
//    public CCAfinalReviewAdapter(Context mContext,ArrayList<CCADetailModel>mCCADetailModelArrayList) {
//        this.mContext = mContext;
//        this.mCCADetailModelArrayList = mCCADetailModelArrayList;
//    }
    public CCAfinalReviewAfterSubmissionAdapter(Context mContext, ArrayList<CCAReviewAfterSubmissionModel> mCCADetailModelArrayList) {
        this.mContext = mContext;
        this.mCCADetailModelArrayList = mCCADetailModelArrayList;
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_attendance_list);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cca_review_after_submit, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


//    holder.listTxtView.setText(mSocialMediaModels.get(position).toString());
        holder.textViewCCADay.setText(mCCADetailModelArrayList.get(position).getDay());
        holder.attendanceListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!(mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("0")) || !(mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("-1"))) || (!(mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("0")) || !(mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("-1")))) {
                    showAttendanceList(position);
                }
            }
        });
        if (mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("0")) {
            holder.linearChoice1.setVisibility(View.GONE);

            holder.textViewCCAChoice1.setText("Choice 1 : None");

        } else if (mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("-1")) {
            holder.linearChoice1.setVisibility(View.GONE);
//            holder.textViewCCAChoice1.setVisibility(View.INVISIBLE);
            holder.textViewCCAChoice1.setText("Choice 1 : Nil");

        } else {
            holder.linearChoice1.setVisibility(View.VISIBLE);
//            holder.textViewCCAChoice1.setVisibility(View.VISIBLE);
//            holder.textViewCCAChoice1.setText("Choice 1 : " + mCCADetailModelArrayList.get(position).getChoice1());
            holder.textViewCCAChoice1.setText(mCCADetailModelArrayList.get(position).getChoice1());
            if (mCCADetailModelArrayList.get(position).getVenue().equalsIgnoreCase("0")|| (mCCADetailModelArrayList.get(position).getVenue().equalsIgnoreCase("")))
            {
                holder.textViewCCAVenue.setVisibility(View.GONE);

            }
            else
            {

                holder.textViewCCAVenue.setText(mCCADetailModelArrayList.get(position).getVenue());
                holder.textViewCCAVenue.setVisibility(View.VISIBLE);

            }
            if (mCCADetailModelArrayList.get(position).getCca_item_start_time()!=null && mCCADetailModelArrayList.get(position).getCca_item_end_time()!=null)
            {
                holder.textViewCCAaDateItemChoice1.setVisibility(View.VISIBLE);

                holder.textViewCCAaDateItemChoice1.setText("("+ AppUtils.convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_start_time())+" - "+AppUtils.convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_end_time())+")");

            }else if (mCCADetailModelArrayList.get(position).getCca_item_start_time()!=null)
            {
                holder.textViewCCAaDateItemChoice1.setVisibility(View.VISIBLE);

                holder.textViewCCAaDateItemChoice1.setText("("+AppUtils.convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_start_time())+")");
            }else if (mCCADetailModelArrayList.get(position).getCca_item_end_time()!=null)
            {
                holder.textViewCCAaDateItemChoice1.setVisibility(View.VISIBLE);

                holder.textViewCCAaDateItemChoice1.setText("("+AppUtils.convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_end_time())+")");
            }
            else
            {
                holder.textViewCCAaDateItemChoice1.setVisibility(View.GONE);

            }

        }
        if (mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("0")) {
            holder.linearChoice2.setVisibility(View.GONE);
            holder.textViewCCAChoice2.setText("Choice 2 : None");

        } else if (mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("-1")) {
            holder.linearChoice2.setVisibility(View.GONE);
//            holder.textViewCCAChoice2.setVisibility(View.INVISIBLE);
            holder.textViewCCAChoice2.setText("Choice 2 : Nil");

        } else {
            holder.linearChoice2.setVisibility(View.VISIBLE);
//            holder.textViewCCAChoice2.setVisibility(View.VISIBLE);
            holder.textViewCCAChoice2.setText( mCCADetailModelArrayList.get(position).getChoice2());
//            holder.textViewCCAChoice2.setText("Choice 2 : " + mCCADetailModelArrayList.get(position).getChoice2());
            if (mCCADetailModelArrayList.get(position).getVenue().equalsIgnoreCase("0")|| (mCCADetailModelArrayList.get(position).getVenue().equalsIgnoreCase("")))
            {
                holder.textViewCCAVenueChoice2.setVisibility(View.GONE);

            }
            else
            {

                holder.textViewCCAVenueChoice2.setText(mCCADetailModelArrayList.get(position).getVenue());
                holder.textViewCCAVenueChoice2.setVisibility(View.VISIBLE);

            }
            if (mCCADetailModelArrayList.get(position).getCca_item_start_time()!=null && mCCADetailModelArrayList.get(position).getCca_item_end_time()!=null)
            {
                holder.textViewCCAaDateItemChoice2.setVisibility(View.VISIBLE);

                holder.textViewCCAaDateItemChoice2.setText("("+ AppUtils.convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_start_time())+" - "+AppUtils.convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_end_time())+")");

            }else if (mCCADetailModelArrayList.get(position).getCca_item_start_time()!=null)
            {
                holder.textViewCCAaDateItemChoice2.setVisibility(View.VISIBLE);

                holder.textViewCCAaDateItemChoice2.setText("("+AppUtils.convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_start_time())+")");
            }else if (mCCADetailModelArrayList.get(position).getCca_item_end_time()!=null)
            {
                holder.textViewCCAaDateItemChoice2.setVisibility(View.VISIBLE);

                holder.textViewCCAaDateItemChoice2.setText("("+AppUtils.convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_end_time())+")");
            }
            else
            {
                holder.textViewCCAaDateItemChoice2.setVisibility(View.GONE);

            }

        }
        if (((mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("0")) || (mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("-1"))) && ((mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("0")) || (mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("-1")))) {
            holder.attendanceListIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.attendanceListIcon.setVisibility(View.VISIBLE);

        }


    }


    @Override
    public int getItemCount() {
//       System.out.println("Adapter---size" + mSocialMediaModels.size());

        return mCCADetailModelArrayList.size();
    }


    public void showAttendanceList(int mPosition) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Button dialogDismiss = (Button) dialog.findViewById(R.id.btn_dismiss);
        LinearLayout linearChoice3 = (LinearLayout) dialog.findViewById(R.id.linearChoice1);
        LinearLayout linearChoice4 = (LinearLayout) dialog.findViewById(R.id.linearChoice2);
        TextView alertHead = (TextView) dialog.findViewById(R.id.alertHead);
        TextView textViewCCAChoiceFirst = (TextView) dialog.findViewById(R.id.textViewCCAChoice1);
        TextView textViewCCAChoiceSecond = (TextView) dialog.findViewById(R.id.textViewCCAChoice2);
        ScrollView scrollViewMain = (ScrollView) dialog.findViewById(R.id.scrollViewMain);
        RecyclerView socialMediaList = (RecyclerView) dialog.findViewById(R.id.recycler_view_social_media);
        RecyclerView recycler_view_social_mediaChoice2 = (RecyclerView) dialog.findViewById(R.id.recycler_view_social_mediaChoice2);
        alertHead.setText("Attendance report of " + mCCADetailModelArrayList.get(mPosition).getDay());

//        scrollViewMain.pageScroll(View.FOCUS_DOWN);
        scrollViewMain.smoothScrollTo(0,0);
        if (!(mCCADetailModelArrayList.get(mPosition).getChoice1().equalsIgnoreCase("0")) && !(mCCADetailModelArrayList.get(mPosition).getChoice1().equalsIgnoreCase("-1"))) {
            textViewCCAChoiceFirst.setText(mCCADetailModelArrayList.get(mPosition).getChoice1());
            linearChoice3.setVisibility(View.VISIBLE);
            socialMediaList.setVisibility(View.VISIBLE);
            socialMediaList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            socialMediaList.setLayoutManager(llm);
            CCAAttendenceListAdapter socialMediaAdapter = new CCAAttendenceListAdapter(mContext, mCCADetailModelArrayList.get(mPosition).getCalendarDaysChoice1());
            socialMediaList.setAdapter(socialMediaAdapter);
        } else {
            linearChoice3.setVisibility(View.GONE);
            socialMediaList.setVisibility(View.GONE);

        }


        if (!(mCCADetailModelArrayList.get(mPosition).getChoice2().equalsIgnoreCase("0")) && !(mCCADetailModelArrayList.get(mPosition).getChoice2().equalsIgnoreCase("-1"))) {
            textViewCCAChoiceSecond.setText(mCCADetailModelArrayList.get(mPosition).getChoice2());

            linearChoice4.setVisibility(View.VISIBLE);
            recycler_view_social_mediaChoice2.setVisibility(View.VISIBLE);
            recycler_view_social_mediaChoice2.setHasFixedSize(true);
            LinearLayoutManager llmrecycler_view_social_mediaChoice2 = new LinearLayoutManager(mContext);
            llmrecycler_view_social_mediaChoice2.setOrientation(LinearLayoutManager.VERTICAL);
            recycler_view_social_mediaChoice2.setLayoutManager(llmrecycler_view_social_mediaChoice2);
            CCAAttendenceListAdapter socialMediaAdapterChoice2 = new CCAAttendenceListAdapter(mContext, mCCADetailModelArrayList.get(mPosition).getCalendarDaysChoice2());
            recycler_view_social_mediaChoice2.setAdapter(socialMediaAdapterChoice2);
        } else {
            linearChoice4.setVisibility(View.GONE);
            recycler_view_social_mediaChoice2.setVisibility(View.GONE);

        }
        dialogDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
