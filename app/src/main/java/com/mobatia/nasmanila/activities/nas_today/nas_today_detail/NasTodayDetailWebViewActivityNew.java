package com.mobatia.nasmanila.activities.nas_today.nas_today_detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;

public class NasTodayDetailWebViewActivityNew extends AppCompatActivity {

    private Context mContext;
    private WebView mWebView;
    private RelativeLayout mProgressRelLayout;
    private WebSettings mwebSettings;
    private boolean loadingFlag = true;
    private String mLoadUrl = null;
    private boolean mErrorFlag = false;
    Bundle extras;
    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    ImageView back;
    ImageView home;
    ImageView infoImg;
    RotateAnimation anim;
    String pdf="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nas_today_detail_web_view_new);
        mContext = this;
        extras = getIntent().getExtras();
        if (extras != null) {
            mLoadUrl = extras.getString("webViewComingDetail");
            pdf=extras.getString("pdf");
            System.out.println("webViewComingUpDetail"+mLoadUrl);
            Log.e("Webview", mLoadUrl);

        }
//		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        initialiseUI();
        getWebViewSettings();
    }



    private void initialiseUI() {
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        mWebView = (WebView) findViewById(R.id.webView);
        mProgressRelLayout = (RelativeLayout) findViewById(R.id.progressDialog);
        headermanager = new HeaderManager(NasTodayDetailWebViewActivityNew.this, "NAIS Manila Today");
        headermanager.getHeader(relativeHeader, 0);
        back = headermanager.getLeftButton();
        headermanager.setButtonLeftSelector(R.drawable.back,
                R.drawable.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.hideKeyBoard(mContext);
                finish();
            }
        });
        home = headermanager.getLogoButton();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, HomeListAppCompatActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
        });
    }
    private void getWebViewSettings() {
        mProgressRelLayout.setVisibility(View.GONE);
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

//        mWebView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024); // 5MB
//        mWebView.getSettings().setAppCachePath(
//                mContext.getCacheDir().getAbsolutePath());
        mWebView.getSettings().setAllowFileAccess(true);
//        mWebView.getSettings().setAppCacheEnabled(true);
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
//                view.loadUrl(url);
                //view.loadData(mLoadUrl, "text/html", "UTF-8");
                //view.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");
//				view.loadDataWithBaseURL("file:///android_asset/", mLoadUrl, "text/html; charset=utf-8", "utf-8", "about:blank");
                if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("www.")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    System.out.println("NAS load url " + mLoadUrl);
                    view.loadDataWithBaseURL("file:///android_asset/fonts/", mLoadUrl, "text/html; charset=utf-8", "utf-8", "about:blank");
                    return true;

                }
            }

//            public void onPageFinished(WebView view, String url) {
//                mProgressRelLayout.clearAnimation();
//                mProgressRelLayout.setVisibility(View.GONE);
//                if (AppUtils.checkInternet(mContext) && loadingFlag) {
//                    view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
////                    view.loadUrl(url);
//                    // view.loadData(mLoadUrl, "text/html", "UTF-8");
//                    //view.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");
//                    System.out.println("NAIS load url " + mLoadUrl);
//                    view.loadDataWithBaseURL("file:///android_asset/", mLoadUrl, "text/html; charset=utf-8", "utf-8", "about:blank");
//
//                    loadingFlag = false;
//                } else if (!AppUtils.checkInternet(mContext) && loadingFlag) {
//                    view.getSettings()
//                            .setCacheMode(WebSettings.LOAD_CACHE_ONLY);
////                    view.loadUrl(url);
//                    //view.loadData(mLoadUrl, "text/html", "UTF-8");
//                    //view.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");
//                    System.out.println("NAIS load url " + mLoadUrl);
//                    view.loadDataWithBaseURL("file:///android_asset/", mLoadUrl, "text/html; charset=utf-8", "utf-8", "about:blank");
//
//                    System.out.println("CACHE LOADING");
//                    loadingFlag = false;
//                }
//            }

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
            System.out.println("NAS load url " + mLoadUrl);
            //mWebView.loadData(mLoadUrl, "text/html", "UTF-8");
            mWebView.loadDataWithBaseURL("file:///android_asset/", mLoadUrl, "text/html; charset=utf-8", "utf-8", "about:blank");
            //mWebView.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");
        } else {
            mProgressRelLayout.clearAnimation();
            mProgressRelLayout.setVisibility(View.GONE);
            AppUtils.showAlertFinish((Activity) mContext, getResources()
                            .getString(R.string.common_error_loading_page), "",
                    getResources().getString(R.string.ok), false);
        }
//		mProgressRelLayout.setVisibility(View.VISIBLE);
//		mWebView.setVisibility(View.VISIBLE);
//
//		anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
//				Animation.RELATIVE_TO_SELF, 0.5f);
//		anim.setInterpolator(mContext, android.R.interpolator.linear);
//		anim.setRepeatCount(Animation.INFINITE);
//		anim.setDuration(1000);
//		mProgressRelLayout.setAnimation(anim);
//		mProgressRelLayout.startAnimation(anim);
//		mWebView.setFocusable(true);
//		mWebView.setFocusableInTouchMode(true);
//		mWebView.setBackgroundColor(0X00000000);
//		mWebView.setVerticalScrollBarEnabled(false);
//		mWebView.setHorizontalScrollBarEnabled(false);
//		mWebView.setWebChromeClient(new WebChromeClient());
////        int sdk = Build.VERSION.SDK_INT;
////        if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
////            mWebView.setBackgroundColor(Color.argb(1, 0, 0, 0));
////        }
//		mwebSettings = mWebView.getSettings();
//		mwebSettings.setSaveFormData(true);
//		mwebSettings.setBuiltInZoomControls(false);
//		mwebSettings.setSupportZoom(false);
//
//		mwebSettings.setPluginState(WebSettings.PluginState.ON);
//		mwebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//		mwebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//		mwebSettings.setDomStorageEnabled(true);
//		mwebSettings.setDatabaseEnabled(true);
//		mwebSettings.setDefaultTextEncodingName("utf-8");
//		mwebSettings.setLoadsImagesAutomatically(true);
//
//		mWebView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024); // 5MB
//		mWebView.getSettings().setAppCachePath(
//				mContext.getCacheDir().getAbsolutePath());
//		mWebView.getSettings().setAllowFileAccess(true);
//		mWebView.getSettings().setAppCacheEnabled(true);
//		mWebView.getSettings().setJavaScriptEnabled(true);
//		mWebView.getSettings()
//				.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//
////		refreshBtn.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// TODO Auto-generated method stub
////				GetWebUrlAsyncTask getWebUrl = new GetWebUrlAsyncTask(WEB_CONTENT_URL
////						+ mType, WEB_CONTENT + "/" + mType, 1, mTAB_ID);
////				getWebUrl.execute();
////			}
////		});
//
//		mWebView.setWebViewClient(new WebViewClient() {
//
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//				//view.loadData(mLoadUrl, "text/html", "UTF-8");
////				view.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");
//
//				return true;
//			}
//
//			public void onPageFinished(WebView view, String url) {
//				mProgressRelLayout.clearAnimation();
//				mProgressRelLayout.setVisibility(View.GONE);
//
//			}
//
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				super.onPageStarted(view, url, favicon);
//
//			}
//
//			/*
//             * (non-Javadoc)
//             *
//             * @see
//             * android.webkit.WebViewClient#onReceivedError(android.webkit.WebView
//             * , int, java.lang.String, java.lang.String)
//             */
//			@Override
//			public void onReceivedError(WebView view, int errorCode,
//										String description, String failingUrl) {
//				mProgressRelLayout.clearAnimation();
//				mProgressRelLayout.setVisibility(View.GONE);
//				if (AppUtils.checkInternet(mContext)) {
//					AppUtils.showAlertFinish((Activity) mContext, getResources()
//									.getString(R.string.common_error), "",
//							getResources().getString(R.string.ok), false);
//				}
//
//				super.onReceivedError(view, errorCode, description, failingUrl);
//			}
//		});
//
//		if (mLoadUrl.equals("")) {
//
//			mErrorFlag = true;
//		} else {
//			mErrorFlag = false;
//		}
//		if (mLoadUrl != null && !mErrorFlag) {
//			System.out.println("NAIS load url " + mLoadUrl);
//			//mWebView.loadData(mLoadUrl, "text/html", "UTF-8");
////			mWebView.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");
//			mWebView.loadDataWithBaseURL("file:///android_asset/", mLoadUrl, "text/html; charset=utf-8", "utf-8", "about:blank");
//		} else {
//			mProgressRelLayout.clearAnimation();
//			mProgressRelLayout.setVisibility(View.GONE);
//			AppUtils.showAlertFinish((Activity) mContext, getResources()
//							.getString(R.string.common_error_loading_page), "",
//					getResources().getString(R.string.ok), false);
//		}

    }
}
