package com.mobatia.nasmanila.fragments.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NASCalendarConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.manager.AppUtils;

/**
 * Created by krishnaraj on 07/11/18.
 */

public class CalendarWebViewFragment extends Fragment implements
        NaisTabConstants, CacheDIRConstants, URLConstants, JSONConstants, StatusConstants,
        IntentPassValueConstants, NaisClassNameConstants, NASCalendarConstants, View.OnClickListener {

    private View mRootView;
    private Context mContext;

    private RelativeLayout relMain;

    Activity activity;

    String mTitle, mTabId;
    private WebView mWebView;
    private RelativeLayout mProgressRelLayout;
    private WebSettings mwebSettings;
    private boolean loadingFlag = true;
    private String mLoadUrl = "https://www.nordangliaeducation.com/our-schools/philippines/manila/international/news-and-insights/school-calendar";
    private boolean mErrorFlag = false;
    RotateAnimation anim;
    TextView mTitleTextView;
    public CalendarWebViewFragment(String title, String tabId) {
        this.mTitle = title;
        this.mTabId = tabId;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu,
     * android.view.MenuInflater)
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public CalendarWebViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_calendar_webview,
                container, false);
        mContext = getActivity();
        activity = getActivity();
//		setHasOptionsMenu(true);
//	 Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        initialiseUI();
        getWebViewSettings();

        return mRootView;
    }


    private void initialiseUI() {
        mLoadUrl = "https://www.nordangliaeducation.com/our-schools/philippines/manila/international/news-and-insights/school-calendar";
        relMain = (RelativeLayout) mRootView.findViewById(R.id.relMain);
        mWebView = (WebView)  mRootView.findViewById(R.id.webView);
        mTitleTextView = mRootView.findViewById(R.id.titleTextView);
        mTitleTextView.setText(CALENDAR);
        mProgressRelLayout = (RelativeLayout)  mRootView.findViewById(R.id.progressDialog);
        relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onClick(View v) {

    }
    private void getWebViewSettings() {


        mProgressRelLayout.setVisibility(View.VISIBLE);
        anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(mContext, android.R.interpolator.linear);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1000);
        mProgressRelLayout.setAnimation(anim);
        mProgressRelLayout.startAnimation(anim);
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.setBackgroundColor(0X00000000);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebChromeClient(new WebChromeClient());
//        int sdk = Build.VERSION.SDK_INT;
//        if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
//            mWebView.setBackgroundColor(Color.argb(1, 0, 0, 0));
//        }
        mwebSettings = mWebView.getSettings();
        mwebSettings.setSaveFormData(true);
        mwebSettings.setBuiltInZoomControls(false);
        mwebSettings.setSupportZoom(false);

        mwebSettings.setPluginState(WebSettings.PluginState.ON);
        mwebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mwebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mwebSettings.setDomStorageEnabled(true);
        mwebSettings.setDatabaseEnabled(true);
        mwebSettings.setDefaultTextEncodingName("utf-8");
        mwebSettings.setLoadsImagesAutomatically(true);

        mWebView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024); // 5MB
        mWebView.getSettings().setAppCachePath(
                mContext.getCacheDir().getAbsolutePath());
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings()
                .setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

//		refreshBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetWebUrlAsyncTask getWebUrl = new GetWebUrlAsyncTask(WEB_CONTENT_URL
//						+ mType, WEB_CONTENT + "/" + mType, 1, mTAB_ID);
//				getWebUrl.execute();
//			}
//		});

        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
//                view.loadData(mLoadUrl, "text/html", "UTF-8");

                return true;
            }

            public void onPageFinished(WebView view, String url) {
                mProgressRelLayout.clearAnimation();
                mProgressRelLayout.setVisibility(View.GONE);
                if (AppUtils.checkInternet(mContext) && loadingFlag) {
                    view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                    view.loadUrl(url);
//                    view.loadData(mLoadUrl, "text/html", "UTF-8");

                    loadingFlag = false;
                } else if (!AppUtils.checkInternet(mContext) && loadingFlag) {
                    view.getSettings()
                            .setCacheMode(WebSettings.LOAD_CACHE_ONLY);
                    view.loadUrl(url);
//                    view.loadData(mLoadUrl, "text/html", "UTF-8");

                    System.out.println("CACHE LOADING");
                    loadingFlag = false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            /*
             * (non-Javadoc)
             *
             * @see
             * android.webkit.WebViewClient#onReceivedError(android.webkit.WebView
             * , int, java.lang.String, java.lang.String)
             */
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                mProgressRelLayout.clearAnimation();
                mProgressRelLayout.setVisibility(View.GONE);
                if (AppUtils.checkInternet(mContext)) {
                    AppUtils.showAlertFinish((Activity) mContext, getResources()
                                    .getString(R.string.common_error), "",
                            getResources().getString(R.string.ok), false);
                }

                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        if (mLoadUrl.equals("")) {

            mErrorFlag = true;
        } else {
            mErrorFlag = false;
        }
        if (mLoadUrl != null && !mErrorFlag) {
            System.out.println("BISAD load url " + mLoadUrl);
            mWebView.loadUrl(mLoadUrl);
        } else {
            mProgressRelLayout.clearAnimation();
            mProgressRelLayout.setVisibility(View.GONE);
            AppUtils.showAlertFinish((Activity) mContext, getResources()
                            .getString(R.string.common_error_loading_page), "",
                    getResources().getString(R.string.ok), false);
        }

    }



}