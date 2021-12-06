/**
 * 
 */
package com.mobatia.nasmanila.fragments.settings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.manager.PreferenceManager;

import java.util.ArrayList;


/**
 * @author Rijo K Jose
 * 
 */
public class CustomSettingsAdapter extends BaseAdapter implements
		CacheDIRConstants, IntentPassValueConstants {

	private Context mContext;
//	private ArrayList<AboutUsModel> mAboutusList;
	private ArrayList<String> mSettingsList;
	private View view;
	private TextView mTitleTxt;
	private TextView mTxtUser;
	private ImageView mImageView;
	private String mTitle;
	private String mTabId;

//	public CustomAboutUsAdapter(Context context,
//								ArrayList<AboutUsModel> arrList, String title, String tabId) {
//		this.mContext = context;
//		this.mAboutusList = arrList;
//		this.mTitle = title;
//		this.mTabId = tabId;
//	}
public CustomSettingsAdapter(Context context,
							 ArrayList<String> arrList, String title, String tabId) {
	this.mContext = context;
	this.mSettingsList = arrList;
	this.mTitle = title;
	this.mTabId = tabId;
}
	public CustomSettingsAdapter(Context context,
								 ArrayList<String> arrList) {
		this.mContext = context;
		this.mSettingsList = arrList;

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSettingsList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mSettingsList.get(position);
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
			view = inflate.inflate(R.layout.custom_settings_list_adapter, null);
		} else {
			view = convertView;
		}
		try {
			mTitleTxt = (TextView) view.findViewById(R.id.listTxtTitle);
			mTxtUser=(TextView)view.findViewById(R.id.txtUser);
			mTitleTxt.setText(mSettingsList.get(position).toString());
			if (PreferenceManager.getUserId(mContext).equalsIgnoreCase("")) {
				if (position==4)
				{
					mTxtUser.setVisibility(View.VISIBLE);
					mTitleTxt.setText(mSettingsList.get(position).toString());
					mTxtUser.setText("("+"Guest"+")");

				}
				else
				{
//                    mTitleTxt.setGravity(Gravity.CENTER_VERTICAL);
					RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mTitleTxt.getLayoutParams();
					lp.addRule(RelativeLayout.CENTER_VERTICAL);
					mTitleTxt.setLayoutParams(lp);


				}
			} else {
				if (position==5)
				{
					mTxtUser.setVisibility(View.VISIBLE);
					mTitleTxt.setText(mSettingsList.get(position).toString());
					mTxtUser.setText("("+PreferenceManager.getUserEmail(mContext)+")");
				}
				else
				{
					RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mTitleTxt.getLayoutParams();
					lp.addRule(RelativeLayout.CENTER_VERTICAL);
					mTitleTxt.setLayoutParams(lp);
				}
			}

		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}/*{
		if (convertView == null) {
			LayoutInflater inflate = LayoutInflater.from(mContext);
			view = inflate.inflate(R.layout.custom_settings_list_adapter, null);
		} else {
			view = convertView;
		}
		try {
			mTitleTxt = (TextView) view.findViewById(R.id.listTxtTitle);
			mTitleTxt.setText(mSettingsList.get(position).toString());
			if (PreferenceManager.getUserId(mContext).equalsIgnoreCase("")) {
				if (position==4)
				{
					mTitleTxt.setMinLines(2);
					mTitleTxt.setText(mSettingsList.get(position).toString() + "\n(" + "Guest"+")");

				}
			} else {
				if (position==5)
				{
					mTitleTxt.setMinLines(2);
					mTitleTxt.setText(mSettingsList.get(position).toString() + "\n(" + PreferenceManager.getUserEmail(mContext)+")");

				}
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}*/


	@Override
	public int getItemViewType(int position) {
		return position;
	}

}
