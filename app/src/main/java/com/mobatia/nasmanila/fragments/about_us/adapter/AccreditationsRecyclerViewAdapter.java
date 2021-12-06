package com.mobatia.nasmanila.fragments.about_us.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.fragments.about_us.model.AboutusModel;

import java.util.ArrayList;

/**
 * Created by gayatri on 11/5/17.
 */
public class AccreditationsRecyclerViewAdapter extends BaseAdapter implements
        CacheDIRConstants, IntentPassValueConstants {

    private Context mContext;
    private ArrayList<String> mAboutusLists;
    private ArrayList<AboutusModel> aboutusModelArrayList;
    private View view;
    private TextView mTitleTxt;
    private ImageView mImageView;
    private String mTitle;
    private String mTabId;


    public AccreditationsRecyclerViewAdapter(Context context,
                                             ArrayList<AboutusModel> aboutusModelArrayList) {
        this.mContext = context;
        this.aboutusModelArrayList = aboutusModelArrayList;

    }


    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return aboutusModelArrayList.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return aboutusModelArrayList.get(position);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflate = LayoutInflater.from(mContext);
            view = inflate.inflate(R.layout.custom_aboutus_list_adapter, null);
        } else {
            view = convertView;
        }
        try {
            mTitleTxt = (TextView) view.findViewById(R.id.listTxtTitle);
            mTitleTxt.setText(aboutusModelArrayList.get(position).getItemTitle());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
