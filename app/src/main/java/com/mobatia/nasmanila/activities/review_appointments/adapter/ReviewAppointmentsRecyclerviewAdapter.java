package com.mobatia.nasmanila.activities.review_appointments.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.review_appointments.model.ReviewModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rijo on 17/1/17.
 */
public class ReviewAppointmentsRecyclerviewAdapter extends RecyclerView.Adapter<ReviewAppointmentsRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ReviewModel> mVideosModelArrayList;
    CancelAppointment cancelAppointment;
    ConfirmAppointment confirmAppointment;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        ImageView addTocalendar;
        ImageView cancelAppointmentImg;
        ImageView confirmationImageview;
        ImageView confirmAppointment;
        TextView studNameTV;
        TextView classNameTV;
        TextView staffNameTV;
        TextView reserveDateTimeTextView;
        TextView expireDateTimeTextView;
        RelativeLayout gridClickRelative;

        public MyViewHolder(View view) {
            super(view);

            photoImageView = (ImageView) view.findViewById(R.id.imgView);
            addTocalendar = (ImageView) view.findViewById(R.id.addTocalendar);
            cancelAppointmentImg = (ImageView) view.findViewById(R.id.cancelAppointment);
            confirmationImageview = (ImageView) view.findViewById(R.id.confirmationImageview);
            confirmAppointment = (ImageView) view.findViewById(R.id.confirmAppointment);
            gridClickRelative = (RelativeLayout) view.findViewById(R.id.gridClickRelative);
            studNameTV = (TextView) view.findViewById(R.id.studNameTV);
            classNameTV = (TextView) view.findViewById(R.id.classNameTV);
            staffNameTV = (TextView) view.findViewById(R.id.staffNameTV);
            reserveDateTimeTextView = (TextView) view.findViewById(R.id.reserveDateTimeTextView);
            expireDateTimeTextView = (TextView) view.findViewById(R.id.expireDateTimeTextView);

        }
    }

    public ReviewAppointmentsRecyclerviewAdapter(Context mContext, ArrayList<ReviewModel> mPhotosList) {
        this.mContext = mContext;
        this.mVideosModelArrayList = mPhotosList;
    }

    public ReviewAppointmentsRecyclerviewAdapter(Context mContext, ArrayList<ReviewModel> mPhotosList, CancelAppointment mCancelAppointment) {
        this.mContext = mContext;
        this.mVideosModelArrayList = mPhotosList;
        this.cancelAppointment = mCancelAppointment;
    }

    public ReviewAppointmentsRecyclerviewAdapter(Context mContext, ArrayList<ReviewModel> mPhotosList, CancelAppointment mCancelAppointment, ConfirmAppointment mConfirmAppointment) {
        this.mContext = mContext;
        this.mVideosModelArrayList = mPhotosList;
        this.cancelAppointment = mCancelAppointment;
        this.confirmAppointment = mConfirmAppointment;
//        setHasStableIds(false);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_review_appointments, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.studNameTV.setText(mVideosModelArrayList.get(position).getStudentName());
        holder.classNameTV.setText(mVideosModelArrayList.get(position).getClassName());
        holder.staffNameTV.setText(mVideosModelArrayList.get(position).getStaffName());
        if (!(mVideosModelArrayList.get(position).getBook_end_date().trim().equalsIgnoreCase(""))) {
            holder.expireDateTimeTextView.setText("Confirm/Cancellation date will over on " + AppUtils.dateParsingToDdMmmYyyy(mVideosModelArrayList.get(position).getBook_end_date()));
        } else {
            holder.expireDateTimeTextView.setText("");
        }
        if (!(mVideosModelArrayList.get(position).getDateAppointment().trim().equalsIgnoreCase("")) && !(mVideosModelArrayList.get(position).getStartTimeFormated().trim().equalsIgnoreCase("")) && !(mVideosModelArrayList.get(position).getEndTimeFormated().trim().equalsIgnoreCase("")) ) {
            holder.reserveDateTimeTextView.setText(AppUtils.dateParsingToDdMmYyyy(mVideosModelArrayList.get(position).getDateAppointment()) + " " + mVideosModelArrayList.get(position).getStartTimeFormated() + " - " + mVideosModelArrayList.get(position).getEndTimeFormated());
        }
        else
        {
            holder.reserveDateTimeTextView.setText("");
        }
        if (!mVideosModelArrayList.get(position).getStudentPhoto().equalsIgnoreCase("")) {
            Picasso.with(mContext).load(AppUtils.replace(mVideosModelArrayList.get(position).getStudentPhoto())).fit().placeholder(R.drawable.student)
                    .into(holder.photoImageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
        if (mVideosModelArrayList.get(position).getStatus().equalsIgnoreCase("3") && mVideosModelArrayList.get(position).getBooking_open().equalsIgnoreCase("y")) {
            holder.confirmationImageview.setBackgroundResource(R.drawable.tick_icon);
            holder.addTocalendar.setVisibility(View.VISIBLE);
            holder.cancelAppointmentImg.setVisibility(View.VISIBLE);
            holder.confirmAppointment.setVisibility(View.GONE);
            holder.confirmAppointment.setImageResource(R.drawable.confirm);
            holder.cancelAppointmentImg.setImageResource(R.drawable.cancel);
//            holder.confirmAppointment.getBackground().setAlpha(255);
//            holder.cancelAppointmentImg.getBackground().setAlpha(255);
            System.out.println("1First");

        } else if (mVideosModelArrayList.get(position).getStatus().equalsIgnoreCase("2") && mVideosModelArrayList.get(position).getBooking_open().equalsIgnoreCase("y")) {
            holder.confirmationImageview.setBackgroundResource(R.drawable.doubtinparticipatingsmallicon);
            holder.addTocalendar.setVisibility(View.GONE);
            holder.confirmAppointment.setVisibility(View.VISIBLE);
            holder.cancelAppointmentImg.setVisibility(View.VISIBLE);
            holder.confirmAppointment.setVisibility(View.VISIBLE);
            holder.confirmAppointment.setImageResource(R.drawable.confirm);
            holder.cancelAppointmentImg.setImageResource(R.drawable.cancel);
//            holder.confirmAppointment.getBackground().setAlpha(255);
//            holder.cancelAppointmentImg.getBackground().setAlpha(255);
            System.out.println("2Second");


        } else if (mVideosModelArrayList.get(position).getStatus().equalsIgnoreCase("3") && mVideosModelArrayList.get(position).getBooking_open().equalsIgnoreCase("n")) {
            holder.confirmationImageview.setBackgroundResource(R.drawable.tick_icon);
            holder.addTocalendar.setVisibility(View.VISIBLE);
            holder.confirmAppointment.setVisibility(View.GONE);
            holder.cancelAppointmentImg.setVisibility(View.VISIBLE);
            holder.confirmAppointment.setImageResource(R.drawable.confirm_dis);
            holder.cancelAppointmentImg.setImageResource(R.drawable.cancel_dis);
//            holder.confirmAppointment.getBackground().setAlpha(150);
//            holder.cancelAppointmentImg.getBackground().setAlpha(150);
            System.out.println("3three");


        } else if (mVideosModelArrayList.get(position).getStatus().equalsIgnoreCase("2") && mVideosModelArrayList.get(position).getBooking_open().equalsIgnoreCase("n")) {
            holder.confirmationImageview.setBackgroundResource(R.drawable.doubtinparticipatingsmallicon);
            holder.addTocalendar.setVisibility(View.GONE);
            holder.confirmAppointment.setVisibility(View.VISIBLE);
            holder.cancelAppointmentImg.setVisibility(View.VISIBLE);
            holder.confirmAppointment.setImageResource(R.drawable.confirm_dis);
            holder.cancelAppointmentImg.setImageResource(R.drawable.cancel_dis);
//            holder.confirmAppointment.getBackground().setAlpha(150);
//            holder.cancelAppointmentImg.getBackground().setAlpha(150);
            System.out.println("4four");


        } /*else {
            holder.confirmationImageview.setBackgroundResource(R.drawable.doubtinparticipatingsmallicon);
            holder.confirmAppointment.setVisibility(View.VISIBLE);
            holder.addTocalendar.setVisibility(View.GONE);
            holder.cancelAppointmentImg.setVisibility(View.VISIBLE);
            holder.confirmAppointment.getBackground().setAlpha(255);
            holder.cancelAppointmentImg.getBackground().setAlpha(255);
            System.out.println("5five");

        }*/
        holder.addTocalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long startTime = 0, endTime = 0;

                try {
//                    Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mVideosModelArrayList.get(0).getDateAppointment());
                    Date dateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(mVideosModelArrayList.get(0).getDateAppointment() + " " + mVideosModelArrayList.get(0).getStartTime());
                    startTime = dateStart.getTime();
                    Date dateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(mVideosModelArrayList.get(0).getDateAppointment() + " " + mVideosModelArrayList.get(0).getEndTime());
                    endTime = dateEnd.getTime();
                } catch (Exception e) {
                }

                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", startTime);
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", endTime);
                intent.putExtra("title", "PARENT MEETING");
                mContext.startActivity(intent);
            }
        });
        holder.cancelAppointmentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAppointment.cancelAppointmentPTA(position);

            }
        });
        holder.confirmAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAppointment.confirmAppointmentPTA(position);

            }
        });


    }


    @Override
    public int getItemCount() {
        return mVideosModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

 /*   @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
*/

    /*    @Override
        public long getItemId(int position) {
            Object listItem = mVideosModelArrayList.get(position);
            return listItem.hashCode();
        }*/
    public interface CancelAppointment {
        void cancelAppointmentPTA(int position);
    }

    public interface ConfirmAppointment {
        void confirmAppointmentPTA(int position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
