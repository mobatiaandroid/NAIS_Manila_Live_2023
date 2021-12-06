package com.mobatia.nasmanila.activities.tutorial.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobatia.nasmanila.R;

import java.util.ArrayList;

/**
 * Created by RIJO K JOSE on 29/3/16.
 */
public class TutorialViewPagerAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<Integer> mImagesArrayListBg;
    private LayoutInflater mInflaters;


    public TutorialViewPagerAdapter(Context context, ArrayList<Integer> mImagesArrayList)
    {
    	 this.mImagesArrayListBg=new ArrayList<Integer>();
        this.mContext=context;
        this.mImagesArrayListBg=mImagesArrayList;
    }

    @Override
    public int getCount() {
        return mImagesArrayListBg.size()+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View pageview = null;
        mInflaters = LayoutInflater.from(mContext);
        pageview = mInflaters.inflate(R.layout.layout_imagepager_adapter, null);
        ImageView imageView = (ImageView) pageview.findViewById(R.id.adImg);
        if(position<mImagesArrayListBg.size()) {
            imageView.setBackgroundResource(mImagesArrayListBg.get(position));

            ((ViewPager) container).

                    addView(pageview, 0);
        }


             return pageview;
         }

        @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
    
}
