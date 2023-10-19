package com.mobatia.nasmanila.activities.pdf;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;

import java.io.File;

public class PdfViewActivityNew extends AppCompatActivity {

//    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    ImageView back, download,backImageView, print, share;
//    private RelativeLayout mProgressRelLayout;
    PDFView pdf;
    WebView webView;
    private WebSettings mwebSettings;
    private String pdfUrl = null;
    private boolean loadingFlag = true;
    private boolean mErrorFlag = false;
    String url, title, name;
    Bundle extras;
    RotateAnimation anim;
    Context mContext;
    ProgressDialog mProgressDialog;
    private PrintManager printManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_pdf_view_new);
        mContext = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
        //  LocaleHelper.setLocale(getApplicationContext(), PrefUtils.getLanguageString(mContext));

        extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("pdf_url");
            title = extras.getString("title");
            name = extras.getString("filename");
            pdfUrl = AppUtils.replace(extras.getString("pdf_url").replace("&","%26"));

        }
        //resetDisconnectTimer();
        initialiseUI();
        getWebViewSettings();

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdfUrl);

    }

    private void getWebViewSettings() {
//        mProgressRelLayout.setVisibility(View.VISIBLE);
        anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(mContext, android.R.interpolator.linear);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1000);
//        mProgressRelLayout.setAnimation(anim);
//        mProgressRelLayout.startAnimation(anim);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setBackgroundColor(0X00000000);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());
//        int sdk = Build.VERSION.SDK_INT;
//        if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
//            mWebView.setBackgroundColor(Color.argb(1, 0, 0, 0));
//        }
        mwebSettings = webView.getSettings();
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

//        webView.getSettings().setAppCacheMaxSize(10 * 1024 * 1024); // 5MB
//        webView.getSettings().setAppCachePath(
//                mContext.getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings()
                .setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingFlag = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                mProgressRelLayout.clearAnimation();
//                mProgressRelLayout.setVisibility(View.GONE);
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

//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
////                mProgressRelLayout.clearAnimation();
////                mProgressRelLayout.setVisibility(View.GONE);
//                if (AppUtils.checkInternet(mContext)) {
//                    AppUtils.showAlertFinish((Activity) mContext, getResources()
//                                    .getString(R.string.common_error), "",
//                            getResources().getString(R.string.ok), false);
//                }
//
//                super.onReceivedError(view, errorCode, description, failingUrl);
//            }


//            @Override
//            public void onReceivedSslError(WebView view,
//                                           SslErrorHandler handler, SslError error) {
//                // TODO Auto-generated method stub
//                // this method will proceed your url however if certification issues are there or not
//                handler.proceed();
//            }
        });
        if (pdfUrl.equals("")) {

            mErrorFlag = true;
        } else {
            mErrorFlag = false;
        }
        if (pdfUrl != null && !mErrorFlag) {
            System.out.println("NAIS load url " + pdfUrl);
            String loadUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdfUrl;
            webView.loadUrl(loadUrl);
        } else {
//            mProgressRelLayout.clearAnimation();
//            mProgressRelLayout.setVisibility(View.GONE);
            AppUtils.showAlertFinish((Activity) mContext, getResources()
                            .getString(R.string.common_error_loading_page), "",
                    getResources().getString(R.string.ok), false);
        }
    }

    private void initialiseUI() {
        printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
//        relativeHeader = findViewById(R.id.relativeHeader);
//        mProgressRelLayout = (RelativeLayout) findViewById(R.id.progressDialog);
        download = findViewById(R.id.pdfDownloadImgView);
        pdf = findViewById(R.id.pdfView);
        backImageView =  findViewById(R.id.backImageView);
        headermanager = new HeaderManager(PdfViewActivityNew.this, title);
        webView = (WebView) findViewById(R.id.webView);
        /*if (AppPreferenceManager.getSchoolSelection(mContext).equals("ISG")) {
            headermanager.getHeader(relativeHeader, 1);
        } else {
            headermanager.getHeader(relativeHeader, 3);
        }
        back = headermanager.getLeftButton();
        headermanager.setButtonLeftSelector(R.drawable.backbtn,
                R.drawable.backbtn);*/

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopDisconnectTimer();
                finish();
            }
        });*/
       /* print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopDisconnectTimer();
                if (AppUtilityMethods.isNetworkConnected(mContext)) {
                    String jobName = getString(R.string.app_name) + " Document";
                    PrintJob printJob = printManager.print(jobName, new PDFPrintDocumentAdapter(PDFViewActivity.this, "document", getFilepath("document.pdf")), null);

                } else {
                    AppUtilityMethods.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
                }
            }
        });*/
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl)));

                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });



    }


    @Override
    public void onStop() {
        super.onStop();
//        stopDisconnectTimer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //stopDisconnectTimer();

    }

    private String getFilepath(String filename) {

        return new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Download/" + filename).getPath();

    }


}
