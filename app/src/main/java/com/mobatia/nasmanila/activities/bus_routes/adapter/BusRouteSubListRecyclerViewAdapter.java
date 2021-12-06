package com.mobatia.nasmanila.activities.bus_routes.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.fragments.sports.model.BusModel;
import com.mobatia.nasmanila.manager.AppUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/** BusRouteSubListRecyclerViewAdapter
 * Created by gayatri on 5/4/17.
 */
public class BusRouteSubListRecyclerViewAdapter extends RecyclerView.Adapter<BusRouteSubListRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<BusModel> mPhotosModelArrayList;
    String photo_id="-1";
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time,timeamorpm,routename,destination;
        //RecyclerView recycler_view_participant;
        public MyViewHolder(View view) {
            super(view);

            time= (TextView) view.findViewById(R.id.time);
            timeamorpm= (TextView) view.findViewById(R.id.timeamorpm);
            routename= (TextView) view.findViewById(R.id.routename);
            destination= (TextView) view.findViewById(R.id.destination);

        }
    }


    public BusRouteSubListRecyclerViewAdapter(Context mContext, ArrayList<BusModel> mPhotosList) {
        this.mContext = mContext;
        this.mPhotosModelArrayList = mPhotosList;
    }
   /* public BusRouteSubListRecyclerViewAdapter(Context mContext, ArrayList<BusModel> mPhotosList, String photo_id) {
        this.mContext = mContext;
        this.mPhotosModelArrayList = mPhotosList;
        this.photo_id = photo_id;
    }*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_adapter_busroute_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       holder.time.setText(" "+convertTime(mPhotosModelArrayList.get(position).getTime()).substring(0,5)+" ");
        String am=convertTime(mPhotosModelArrayList.get(position).getTime()).substring(6,7);
       holder.timeamorpm.setText(toUppercase(convertTime(mPhotosModelArrayList.get(position).getTime())));
        holder.routename.setText(mPhotosModelArrayList.get(position).getRoute_name());
        convertTime(mPhotosModelArrayList.get(position).getTime());
        if(position==0){
            holder.destination.setText("Start");
        }else if(position==mPhotosModelArrayList.size()-1){
            holder.destination.setText("Finish");

        }else{
            holder.destination.setVisibility(View.INVISIBLE);
        }

    }

    public String toUppercase(String word){
        String[] strArray = word.split(" ");
        StringBuilder builder = new StringBuilder();
        //for (String s : strArray) {

            String cap = strArray[1].substring(0, 1).toUpperCase() + strArray[1].substring(1);
            builder.append(cap + " ");
        //}
       return builder.toString();

    }

    @Override
    public int getItemCount() {
        return mPhotosModelArrayList.size();
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

    private String convertTime(String time){
        //String convertTime="";
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

// Get time from date
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        String displayValue = timeFormatter.format(date);
        System.out.println("date ---"+displayValue);
        if (displayValue.contains("a.m.")) {
            displayValue= AppUtils.replaceAmdot(displayValue);

        } else  if (displayValue.contains("p.m.")){
            displayValue=AppUtils.replacePmdot(displayValue);
        }
        return displayValue;
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
