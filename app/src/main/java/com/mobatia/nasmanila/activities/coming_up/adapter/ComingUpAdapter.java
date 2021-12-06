/**
 * 
 */
package com.mobatia.nasmanila.activities.coming_up.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.coming_up.model.ComingUpModel;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.manager.AppUtils;

import java.util.ArrayList;


/**
 * @author Rijo K Jose
 * 
 */
public class ComingUpAdapter extends BaseAdapter implements
		CacheDIRConstants, IntentPassValueConstants {

	private Context mContext;
	private ArrayList<ComingUpModel> mStaffList;
	private View view;
	private TextView mTitleTxt;
	private ImageView mImageView;
	private String mTitle;
	private String mTabId;
	ViewHolder mViewHolder;

public ComingUpAdapter(Context context,
					   ArrayList<ComingUpModel> mStaffList, String title, String tabId) {
	this.mContext = context;
	this.mStaffList = mStaffList;
	this.mTitle = title;
	this.mTabId = tabId;
}
	public ComingUpAdapter(Context context,
						   ArrayList<ComingUpModel> arrList) {
		this.mContext = context;
		this.mStaffList = arrList;

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mStaffList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mStaffList.get(position);
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
			 mViewHolder=new ViewHolder();
			convertView = inflate.inflate(R.layout.custom_coming_up_list_adapter, null);
			mViewHolder.listTxtTitle = (TextView) convertView.findViewById(R.id.listTxtTitle);
			mViewHolder.listTxtDate = (TextView) convertView.findViewById(R.id.listTxtDate);
			mViewHolder.iconImg = (ImageView) convertView.findViewById(R.id.iconImg);

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		try {
			mViewHolder.listTxtTitle.setText(mStaffList.get(position).getTitle().trim());
			mViewHolder.listTxtDate.setText(  AppUtils.dateParsingTodd_MMM_yyyy(mStaffList.get(position).getDate()));

		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}
	class ViewHolder
	{
		TextView listTxtTitle;
		TextView listTxtDate;
		ImageView iconImg;

	}



	@Override
	public int getItemViewType(int position) {
		return position;
	}

}
