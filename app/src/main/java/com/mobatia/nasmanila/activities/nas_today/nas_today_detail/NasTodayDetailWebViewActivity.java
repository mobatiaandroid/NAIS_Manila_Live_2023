package com.mobatia.nasmanila.activities.nas_today.nas_today_detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.activities.pdf.PDFViewActivity;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gayatri on 20/3/17.
 */
public class NasTodayDetailWebViewActivity extends Activity implements
        CacheDIRConstants, NaisClassNameConstants {

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
        setContentView(R.layout.comingup_detailweb_view_layout);
        mContext = this;
        extras = getIntent().getExtras();
        if (extras != null) {
            mLoadUrl = extras.getString("webViewComingDetail");
            pdf=extras.getString("pdf");
            System.out.println("webViewComingUpDetail"+mLoadUrl);
            Log.e("Webview",mLoadUrl);

        }
//		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        initialiseUI();
        getWebViewSettings();
    }


    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Oct 30, 2014 Author : Vandana
     * Surendranath
     *****************************************************/
    private void initialiseUI() {
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        mWebView = (WebView) findViewById(R.id.webView);
        mProgressRelLayout = (RelativeLayout) findViewById(R.id.progressDialog);
        headermanager = new HeaderManager(NasTodayDetailWebViewActivity.this, NAS_TODAY);
        if (pdf.equalsIgnoreCase(""))
        {
            headermanager.getHeader(relativeHeader, 0);

        }
        else
        {
            headermanager.getHeader(relativeHeader, 3);
            infoImg=headermanager.getRightInfoImage();
//            infoImg.setImageResource(R.drawable.pdfdownloadbutton);
            infoImg.setBackgroundResource(R.drawable.pdfdownloadbutton);
            infoImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!pdf.equals("")) {
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pdf)));
                        }else{
                            Intent intent = new Intent(mContext, PDFViewActivity.class);
                            intent.putExtra("pdf_url", pdf);
                            startActivity(intent);
                        }

                    }
                }
            });
        }

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

    /*******************************************************
     * Method name : getWebViewSettings Description : get web view settings
     * Parameters : nil Return type : void Date : Oct 31, 2014 Author : Vandana
     * Surendranath
     *****************************************************/
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
               // view.loadData(mLoadUrl, "text/html", "UTF-8");
                    System.out.println("value is :: "+ url);
                if( url.startsWith("http:") || url.startsWith("https:") || url.startsWith("www.") ) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(intent);
//                    mWebView.loadDataWithBaseURL(null, url, "text/html; charset=utf-8", "utf-8", null);
                    mWebView .loadUrl(url);
                    return true;
                }
                else
                {
                    mWebView.loadDataWithBaseURL("file:///android_asset/fonts/", mLoadUrl, "text/html; charset=utf-8", "utf-8", "about:blank");
                    return true;

                }

//                mWebView.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");
//                return true;
            }

            public void onPageFinished(WebView view, String url) {
                mProgressRelLayout.clearAnimation();
                mProgressRelLayout.setVisibility(View.GONE);
                if (AppUtils.checkInternet(mContext) && loadingFlag) {
                    view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//                    view.loadUrl(url);
                    //view.loadData(mLoadUrl, "text/html", "UTF-8");
                    view.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");

                    loadingFlag = false;
                } else if (!AppUtils.checkInternet(mContext) && loadingFlag) {
                    view.getSettings()
                            .setCacheMode(WebSettings.LOAD_CACHE_ONLY);
//                    view.loadUrl(url);
                   // view.loadData(mLoadUrl, "text/html", "UTF-8");
                    view.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");

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
//            List<String> pullLinks=new ArrayList<>();
//            pullLinks=pullLinks(mLoadUrl);
//            if (pullLinks.size()>0) {
//                for (int i = 0; i < pullLinks.size(); i++) {
//                    mLoadUrl = mLoadUrl.replaceAll(pullLinks.get(i), "<a href=\"" + pullLinks.get(i)+ "\">" + pullLinks.get(i)+ " </a>");
//
//                }
//            }
            mWebView.loadData(mLoadUrl, "text/html; charset=utf-8", "utf-8");

        } else {
            mProgressRelLayout.clearAnimation();
            mProgressRelLayout.setVisibility(View.GONE);
            AppUtils.showAlertFinish((Activity) mContext, getResources()
                            .getString(R.string.common_error_loading_page), "",
                    getResources().getString(R.string.ok), false);
        }
//        System.out.println("NAIS load url " +mLoadUrl);
        Log.v("URL Log:",mLoadUrl);

    }
    public List<String> pullLinks(String text)
    {
        System.out.println("working");
//        String links ="";
        List<String> links = new ArrayList<String>();

        //String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        String regex = "\\(?\\b(https?://|www[.]|ftp://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);

        while(m.find())
        {
            String urlStr = m.group();

            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }

//            links=urlStr;
            links.add(urlStr);

        }

        return links;
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
//            mWebView.goBack();
////If there is history, then the canGoBack method will return ‘true’//
//            return true;
//        }
//
////If the button that’s been pressed wasn’t the ‘Back’ button, or there’s currently no
////WebView history, then the system should resort to its default behavior and return
////the user to the previous Activity//
//        return super.onKeyDown(keyCode, event);    }
//
//
//    @Override
//    public void onBackPressed() {
//
//        if ( mWebView.canGoBack()) {
//            mWebView.goBack();
//        }else
//        {
//            finish();
//        }
//        super.onBackPressed();
//    }
}
//  {
//          String pushNotificationDetail= "<!DOCTYPE html>\n" +
//          "<html>\n" +
//          "<head>\n" +
//          "<style>\n" +
//          "\n" +
//          "@font-face {\n" +
//          "font-family: SourceSansPro-Semibold;"+
//          "src: url(SourceSansPro-Semibold.ttf);"+
//
//          "font-family: SourceSansPro-Regular;"+
//          "src: url(SourceSansPro-Regular.ttf);"+
//          "}"+
//          ".title {"+
//          "font-family: SourceSansPro-Regular;"+
//          "font-size:16px;"+
//          "text-align:left;"+
//          "color:    #46C1D0;"+
//          "}"+
//          ".description {"+
//          "font-family: SourceSansPro-Semibold;"+
//          "text-align:justify;"+
//          "font-size:14px;"+
//          "color: #000000;"+
//          "}"+".date {"+
//          "font-family: SourceSansPro-Semibold;"+
//          "text-align:right;"+
//          "font-size:12px;"+
//
//          "color: #A9A9A9;"+
//          "}"+
//          "</style>\n"+"</head>"+
//          "<body>";
//          if (!AppController.mMessageReadList.get(position).getUrl().equalsIgnoreCase("")) {
//          pushNotificationDetail = pushNotificationDetail + "<center><img src='" + AppController.mMessageReadList.get(position).getUrl() + "'width='100%', height='auto'>";
//          }
//          DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//          DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");
//          String mdate =AppController.mMessageReadList.get(position).getDate();
//          Date date = null;
//          try {
//          date = inputFormat.parse(mdate);
//          } catch (ParseException e) {
//          e.printStackTrace();
//          }
//          String outputDateStr = outputFormat.format(date);
//          pushNotificationDetail=pushNotificationDetail+
//          "<p class='description'>"+AppController.mMessageReadList.get(position).getMessage();
//          pushNotificationDetail=pushNotificationDetail+"<p class='date'>"+*//*AppController.mMessageReadList.get(position).getDate()*//*outputDateStr +"</p>"+
//
//          "</body>\n</html>";*//*+"<p class='description'>"+pushNotificationArrayList.get(position).getDay() + "-" + pushNotificationArrayList.get(position).getMonthString() + "-" + pushNotificationArrayList.get(position).getYear()+" "+pushNotificationArrayList.get(position).getPushTime()+"</p>"+*//*
//          mIntent = new Intent(mContext, ImageActivity.class);
//        mIntent.putExtra("webViewComingDetail", pushNotificationDetail);
//        mIntent.putExtra("title", AppController.mMessageReadList.get(position).getTitle());
//        mIntent.putExtra("date", AppController.mMessageReadList.get(position).getDate());
//        mIntent.putExtra("isfromUnread", false);
//        mIntent.putExtra("isfromRead", true);
//
//        mIntent.putExtra(POSITION, position);
//        mIntent.putExtra(PASS_ARRAY_LIST, AppController.mMessageReadList);
//
//        mContext.startActivity(mIntent);
//
//        }
/**/
