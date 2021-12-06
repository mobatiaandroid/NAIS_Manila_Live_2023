/**
 * 
 */
package com.mobatia.nasmanila.activities.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;


/**
 * @author RIJO K JOSE
 * 
 */
public class HomeListAdapter extends BaseAdapter{

	private Context mContext;
	private String[] mListItemArray;
	private TypedArray mListImgArray;
	private int customLayout;
	private boolean mDisplayListImage;

	public HomeListAdapter(Context context, String[] listItemArray,
						   TypedArray listImgArray, int customLayout, boolean displayListImage) {
		this.mContext = context;
		this.mListItemArray = listItemArray;
		this.mListImgArray = listImgArray;
		this.customLayout = customLayout;
		this.mDisplayListImage = displayListImage;
	}

	public HomeListAdapter(Context context, String[] listItemArray,
						   int customLayout, boolean displayListImage) {
		this.mContext = context;
		this.mListItemArray = listItemArray;
		this.customLayout = customLayout;
		this.mDisplayListImage = displayListImage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListItemArray.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListItemArray[position];
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(customLayout, null);
		}
		TextView txtTitle = (TextView) convertView
				.findViewById(R.id.listTxtView);
		ImageView imgView = (ImageView) convertView.findViewById(R.id.listImg);
		txtTitle.setText(mListItemArray[position]);
		if (mDisplayListImage) {
			imgView.setVisibility(View.VISIBLE);
			imgView.setImageDrawable(mListImgArray.getDrawable(position));
		} else {
			imgView.setVisibility(View.GONE);
		}
		return convertView;
	}



	@Override
	public int getItemViewType(int position) {
		return position;
	}



	
}
