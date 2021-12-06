package com.mobatia.nasmanila.activities.bus_routes.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.bus_routes.Location;
import com.mobatia.nasmanila.fragments.sports.model.BusModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;

import java.util.ArrayList;

/**
 * Created by Rijo on 17/1/17.
 */
public class BusRouteListRecyclerviewAdapter extends RecyclerView.Adapter<BusRouteListRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<BusModel> mPhotosModelArrayList;
    String photo_id = "-1";
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView busName;
        RecyclerView recycler_view_participant;
        ImageView location, callIconBusRoute;

        public MyViewHolder(View view) {
            super(view);

            busName = (TextView) view.findViewById(R.id.houseNameTextView);
            recycler_view_participant = (RecyclerView) view.findViewById(R.id.recycler_view_participant);
            location = (ImageView) view.findViewById(R.id.busLocation);
            callIconBusRoute = (ImageView) view.findViewById(R.id.callIconBusRoute);
        }
    }


    public BusRouteListRecyclerviewAdapter(Context mContext, ArrayList<BusModel> mPhotosList) {
        this.mContext = mContext;
        this.mPhotosModelArrayList = mPhotosList;
    }

    public BusRouteListRecyclerviewAdapter(Context mContext, ArrayList<BusModel> mPhotosList, String photo_id) {
        this.mContext = mContext;
        this.mPhotosModelArrayList = mPhotosList;
        this.photo_id = photo_id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_adapter_bus_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.recycler_view_participant.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recycler_view_participant.setLayoutManager(llm);
        holder.recycler_view_participant.addItemDecoration(new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));
        BusRouteSubListRecyclerViewAdapter busRouteSubListRecyclerViewAdapter = new BusRouteSubListRecyclerViewAdapter(mContext, mPhotosModelArrayList.get(position).getmBusRoute());
        holder.recycler_view_participant.setAdapter(busRouteSubListRecyclerViewAdapter);

        holder.busName.setText(mPhotosModelArrayList.get(position).getBus_name() + " (" + mPhotosModelArrayList.get(position).getBus_no() + ")");
        holder.recycler_view_participant.addOnItemTouchListener(new RecyclerItemListener(mContext, holder.recycler_view_participant,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int pos) {
//                        showDetail(pos,position);

                    }

                    public void onLongClickItem(View v, int pos) {
//                        showDetail(pos,position);
                    }
                }));

        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotosModelArrayList.size() > 0) {
                    Intent intent = new Intent(mContext, Location.class);
                    intent.putExtra("busModelArray", mPhotosModelArrayList.get(position).getmBusRoute());
                    mContext.startActivity(intent);
                } else {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), mContext.getString(R.string.no_details_available), R.drawable.infoicon, R.drawable.round);

                }
            }
        });

/*
        holder.callIconBusRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotosModelArrayList.size() > 0) {
                    if (!mPhotosModelArrayList.get(0).getContact_no().equals("")) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + mPhotosModelArrayList.get(position).getContact_no()));
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mContext.startActivity(intent);
                    } else {
                        AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), mContext.getString(R.string.no_contact_no), R.drawable.exclamationicon, R.drawable.round);

                    }
                }
                else
                {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), mContext.getString(R.string.no_contact_no), R.drawable.exclamationicon, R.drawable.round);

                }
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return mPhotosModelArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

//    public void showDetail(int pos,int position) {
//        final Dialog dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialogue_participant_detail);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        Button dialogDismiss = (Button) dialog.findViewById(R.id.btn_dismiss);
////        ImageView iconImageView = (ImageView) dialog.findViewById(R.id.iconImageView);
//        TextView studentNameTV = (TextView) dialog.findViewById(R.id.studentNameTV);
//        TextView studentClassTV = (TextView) dialog.findViewById(R.id.studentClassTV);
//        TextView studentSectionTV = (TextView) dialog.findViewById(R.id.studentSectionTV);
//        studentNameTV.setText(mPhotosModelArrayList.get(position).getSportsModelParticipantsArrayList().get(pos).getmName());
//        studentClassTV.setText(mPhotosModelArrayList.get(position).getSportsModelParticipantsArrayList().get(pos).getmClass());
//        studentSectionTV.setText(mPhotosModelArrayList.get(position).getSportsModelParticipantsArrayList().get(pos).getmSection());
//        dialogDismiss.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//
//            public void onClick(View v) {
//
//                dialog.dismiss();
//
//            }
//
//        });
//
//
//        dialog.show();
//    }

//    public void didTapButton(View view) {
////        ImageView button = (ImageView)view.findViewById(R.id.imgView);
////        final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
////        button.startAnimation(myAnim);
//        final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
//
//        // Use bounce interpolator with amplitude 0.2 and frequency 20
//        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.6, 20);
//        myAnim.setInterpolator(interpolator);
//
//        view.startAnimation(myAnim);
//    }
//    @Override
//    public void onViewDetachedFromWindow(MyViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        holder.photoImageView.clearAnimation();
//    }
}
