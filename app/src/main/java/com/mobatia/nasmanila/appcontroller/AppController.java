package com.mobatia.nasmanila.appcontroller;

import android.content.res.TypedArray;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.drawerlayout.widget.DrawerLayout;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mobatia.nasmanila.activities.cca.model.WeekListModel;
import com.mobatia.nasmanila.fragments.gallery.model.PhotosListModel;

import java.util.ArrayList;


public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;
    public static ArrayList<String> eventIdList=new ArrayList<>();
    public static boolean isProviderEnabled=false;
    public static ArrayList<WeekListModel> weekList;
    public static ArrayList<Integer> weekListWithData;
    public static ArrayList<PhotosListModel> mPhotosModelArrayListGallery;
    public static TypedArray mListImgArrays;
    public static String mTitles;
    public static DrawerLayout mDrawerLayouts;
    public static LinearLayout mLinearLayouts;
    public static ListView mListViews;
    public static String[] listitemArrays;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
}
}