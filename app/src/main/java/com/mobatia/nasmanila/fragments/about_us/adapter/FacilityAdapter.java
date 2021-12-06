package com.mobatia.nasmanila.fragments.about_us.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.fragments.about_us.model.AboutusModel;

import java.util.ArrayList;

/**
 * Created by gayatri on 10/5/17.
 */
public class FacilityAdapter extends BaseAdapter {

    private Context mContext;
    ArrayList<AboutusModel> aboutusModelArrayList;

    public FacilityAdapter(Context context,ArrayList<AboutusModel> aboutusModelArrayList) {
        mContext = context;
        this.aboutusModelArrayList=aboutusModelArrayList;
    }

    @Override
    public int getCount() {
        return aboutusModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        int imageID = 0;



        if (convertView == null) {
            // If convertView is null then inflate the appropriate layout file
            convertView = LayoutInflater.from(mContext).inflate(R.layout.facility_grid_item, null);
        }
        else {

        }

        imageView = (ImageView) convertView.findViewById(R.id.imageView);

        if(aboutusModelArrayList.get(position).getItemPdfUrl().endsWith(".pdf")){
            imageView.setImageResource(R.drawable.pdfdownloadbutton);
        }else{
            imageView.setImageResource(R.drawable.webcontentviewbutton);

        }



        return convertView;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

}