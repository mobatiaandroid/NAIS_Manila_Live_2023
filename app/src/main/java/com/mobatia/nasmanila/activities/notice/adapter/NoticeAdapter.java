/**
 * 
 */
package com.mobatia.nasmanila.activities.notice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.notice.model.NoticeModel;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;

import java.util.ArrayList;


/**
 * @author Rijo K Jose
 * 
 */
public class NoticeAdapter extends BaseAdapter implements
		CacheDIRConstants, IntentPassValueConstants {

	private Context mContext;
//	private ArrayList<AboutUsModel> mAboutusList;
	private ArrayList<NoticeModel> mStaffList;
	private View view;
	private TextView mTitleTxt;
	private ImageView mImageView;
	private String mTitle;
	private String mTabId;
	ViewHolder mViewHolder;
//	public CustomAboutUsAdapter(Context context,
//								ArrayList<AboutUsModel> arrList, String title, String tabId) {
//		this.mContext = context;
//		this.mAboutusList = arrList;
//		this.mTitle = title;
//		this.mTabId = tabId;
//	}
public NoticeAdapter(Context context,
					 ArrayList<NoticeModel> mStaffList, String title, String tabId) {
	this.mContext = context;
	this.mStaffList = mStaffList;
	this.mTitle = title;
	this.mTabId = tabId;
}
	public NoticeAdapter(Context context,
						 ArrayList<NoticeModel> arrList) {
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
			convertView = inflate.inflate(R.layout.custom_notice_list_adapter, null);
			mViewHolder.listTxtTitle = (TextView) convertView.findViewById(R.id.listTxtTitle);
			mViewHolder.listTxtDate = (TextView) convertView.findViewById(R.id.listTxtDate);
			mViewHolder.listDay = (TextView) convertView.findViewById(R.id.listDay);
			mViewHolder.listDayEnd = (TextView) convertView.findViewById(R.id.listDayEnd);
			mViewHolder.listYearEnd = (TextView) convertView.findViewById(R.id.listYearEnd);
			mViewHolder.listYear = (TextView) convertView.findViewById(R.id.listYear);
			mViewHolder.listMonth = (TextView) convertView.findViewById(R.id.listMonth);
			mViewHolder.listMonthEnd = (TextView) convertView.findViewById(R.id.listMonthEnd);
			mViewHolder.startLinear = (LinearLayout) convertView.findViewById(R.id.startLinear);
			mViewHolder.endLinear = (LinearLayout) convertView.findViewById(R.id.endLinear);
			mViewHolder.arrowMark = (TextView) convertView.findViewById(R.id.arrowMark);

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		try {
			mViewHolder.listTxtTitle.setText(mStaffList.get(position).getTitle().trim());
//			mViewHolder.listTxtDate.setText("( "+mStaffList.get(position).getType()+" )");
//			mViewHolder.listDay.setText(mStaffList.get(position).getDay());
//			mViewHolder.listMonth.setText(mStaffList.get(position).getMonth()+" "+mStaffList.get(position).getYear());
			if ((mStaffList.get(position).getYear().equalsIgnoreCase(mStaffList.get(position).getYearEnd())) && (mStaffList.get(position).getMonth().equalsIgnoreCase(mStaffList.get(position).getMonthEnd())) && (mStaffList.get(position).getDay().equalsIgnoreCase(mStaffList.get(position).getDayEnd())))
			{
				mViewHolder.listDay.setText(mStaffList.get(position).getDay());
				mViewHolder.listYear.setText(mStaffList.get(position).getYear());
				mViewHolder.listMonth.setText(mStaffList.get(position).getMonth());
				mViewHolder.startLinear.setVisibility(View.VISIBLE);
				mViewHolder.arrowMark.setVisibility(View.GONE);
				mViewHolder.endLinear.setVisibility(View.GONE);
			}
			else
			{
				mViewHolder.listDay.setText(mStaffList.get(position).getDay());
				mViewHolder.listYear.setText(mStaffList.get(position).getYear());
				mViewHolder.listMonth.setText(mStaffList.get(position).getMonth());
				mViewHolder.listDayEnd.setText(mStaffList.get(position).getDayEnd());
				mViewHolder.listYearEnd.setText(mStaffList.get(position).getYearEnd());
				mViewHolder.listMonthEnd.setText(mStaffList.get(position).getMonthEnd());
				mViewHolder.startLinear.setVisibility(View.VISIBLE);
				mViewHolder.arrowMark.setVisibility(View.VISIBLE);
				mViewHolder.endLinear.setVisibility(View.VISIBLE);
			}
//			if (!mStaffList.get(position).getImage().equalsIgnoreCase("")) {
//				Picasso.with(mContext).load(AppUtils.replace(mStaffList.get(position).getImage())).fit()
//						.into(mViewHolder.iconImg, new com.squareup.picasso.Callback() {
//							@Override
//							public void onSuccess() {
//
//							}
//
//							@Override
//							public void onError() {
//
//							}
//						});
//			}
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
		TextView listDayEnd;
		TextView listDay;
		TextView listYear;
		TextView listYearEnd;
		TextView listMonth;
		TextView listMonthEnd;
		LinearLayout endLinear;
		LinearLayout startLinear;
		TextView arrowMark;

	}



	@Override
	public int getItemViewType(int position) {
		return position;
	}

}
