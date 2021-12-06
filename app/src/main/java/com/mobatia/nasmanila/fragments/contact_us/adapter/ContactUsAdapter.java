package com.mobatia.nasmanila.fragments.contact_us.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.fragments.contact_us.model.ContactUsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gayatri on 11/5/17.
 */
public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ContactUsModel> mStaffList;
    String phone="";


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTxt;
        ImageView arrowImg;
        TextView subTitle,cotactEmail;

        public MyViewHolder(View view) {
            super(view);

            mTitleTxt = (TextView) view.findViewById(R.id.contactName);
            subTitle= (TextView) view.findViewById(R.id.cotactNumber);
            cotactEmail= (TextView) view.findViewById(R.id.cotactEmail);
            arrowImg= (ImageView) view.findViewById(R.id.arrowImg);


        }
    }


    public ContactUsAdapter(Context mContext,ArrayList<ContactUsModel> mStaffList) {
        this.mContext = mContext;
        this.mStaffList = mStaffList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_adapter_contact_us, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.mTitleTxt.setText(mStaffList.get(position).getContact_name());
        if(!mStaffList.get(position).getContact_phone().equals("")) {
            holder.subTitle.setText(mStaffList.get(position).getContact_phone());
        }else{
            holder.subTitle.setVisibility(View.GONE);
        }
        if (position==mStaffList.size()-1)
        {
            holder.arrowImg.setVisibility(View.VISIBLE);
        }
        else {

            holder.arrowImg.setVisibility(View.GONE);
        }

   if (mStaffList.get(position).getContact_email().equalsIgnoreCase("")&&mStaffList.get(position).getContact_phone().equalsIgnoreCase("")
           &&!mStaffList.get(position).getContact_name().equalsIgnoreCase(""))
   {
       RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.mTitleTxt.getLayoutParams();
       lp.addRule(RelativeLayout.CENTER_VERTICAL);
       holder.mTitleTxt.setLayoutParams(lp);

   }
        //forGotpasswordText.setText(getString(R.string.forgot_password));
        holder.cotactEmail.setText(mStaffList.get(position).getContact_email());
    if(!mStaffList.get(position).getContact_email().equals("")){
    holder.cotactEmail.setPaintFlags(holder.cotactEmail.getPaintFlags());
    }else{
    holder.cotactEmail.setVisibility(View.INVISIBLE);
      }
        holder.subTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent callIntent = null;
                phone = mStaffList.get(position).getContact_phone();
                if ((int) Build.VERSION.SDK_INT >= 23) {

                    TedPermission.with(mContext)
                            .setPermissionListener(permissionlistenerContact)
                            .setDeniedMessage("If you reject permission,you cannot use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                            .setPermissions(Manifest.permission.CALL_PHONE)
                            .check();


                } else {
                    callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + mStaffList.get(position).getContact_phone()));
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
                    mContext.startActivity(callIntent);
                }
            } /*{
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + holder.subTitle.getText().toString()));
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
            }*/
        });

        holder.cotactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(
                        Intent.ACTION_SEND_MULTIPLE);
                String[] deliveryAddress = {holder.cotactEmail.getText().toString()};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, deliveryAddress);
                emailIntent.setType("text/plain");
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                PackageManager pm = v.getContext().getPackageManager();
                List<ResolveInfo> activityList = pm.queryIntentActivities(
                        emailIntent, 0);
                for (final ResolveInfo app : activityList) {
                    System.out.println("packge name" + app.activityInfo.name);
                    if ((app.activityInfo.name).contains("com.google.android.gm")) {
                        final ActivityInfo activity = app.activityInfo;
                        final ComponentName name = new ComponentName(
                                activity.applicationInfo.packageName, activity.name);
                        emailIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        emailIntent.setComponent(name);
                        v.getContext().startActivity(emailIntent);
                        break;
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mStaffList.size();
    }
    PermissionListener permissionlistenerContact = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
//            Toast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT).show();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
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
            mContext.startActivity(callIntent);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(mContext, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
