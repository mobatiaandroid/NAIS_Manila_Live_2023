package com.mobatia.nasmanila.activities.notificationssplitting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.notifications.model.PushNotificationModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoWebViewActivityNew extends Activity implements OnClickListener,
		IntentPassValueConstants, JSONConstants, URLConstants, StatusConstants {

	WebView webView;
	int position;
	ProgressBar proWebView;

	ImageView back;
	ImageView home;
	RelativeLayout relativeMain;
	Activity mActivity;
	TextView textcontent;

	RelativeLayout relativeHeader;
	HeaderManager headermanager;
	String id = "";
	String title = "";
	String message = "";
	String url = "";
	String date = "";
	String Day = "";
	String Month = "";
	String Year = "";
	String PushDate = "";
	String PushID = "";

	@SuppressLint("NewApi")
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.videopush_web_view);
		mActivity = this;

		Bundle extra = getIntent().getExtras();
		if (extra != null) {
			Day = extra.getString("Day");
			Month = extra.getString("Month");
			Year = extra.getString("Year");
			PushDate = extra.getString("PushDate");
			PushID = extra.getString("PushID");
//			position = extra.getInt(POSITION);
//
//			videolist = (ArrayList<PushNotificationModel>) extra
//					.getSerializable(PASS_ARRAY_LIST);
		}
		webView = (WebView) findViewById(R.id.webView);

		proWebView = (ProgressBar) findViewById(R.id.proWebView);
		textcontent = findViewById(R.id.txtContent);
		textcontent.setVisibility(View.INVISIBLE);

		relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
		headermanager = new HeaderManager(mActivity, "Notification");
		headermanager.getHeader(relativeHeader, 0);
		back = headermanager.getLeftButton();
		headermanager.setButtonLeftSelector(R.drawable.back,
				R.drawable.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		home = headermanager.getLogoButton();
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(mActivity, HomeListAppCompatActivity.class);
				in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
		});

		if (AppUtils.isNetworkConnected(mActivity)) {
			callPushNotification(PushID);
		} else {
			AppUtils.showDialogAlertDismiss((Activity) mActivity, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
		}
//		webView.setWebViewClient(new HelloWebViewClient());
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setPluginState(PluginState.ON);
//		webView.getSettings().setBuiltInZoomControls(false);
//		webView.getSettings().setDisplayZoomControls(true);
////		DisplayMetrics displaymetrics = new DisplayMetrics();
////	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
////	    int height = displaymetrics.heightPixels;
//		String frameVideo = "<html>"+"<br><iframe width=\"320\" height=\"250\" src=\"";
//		String url_Video=frameVideo+videolist.get(position).getPushUrl()+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
//		String urlYoutube = url_Video.replace("watch?v=", "embed/");
//		System.out.println("urlYoutube:"+urlYoutube);
//		webView.loadData(urlYoutube, "text/html", "utf-8");
//
////		webView.loadUrl(videolist.get(position).getUrl());
//		textcontent.setText(videolist.get(position).getPushTitle());
//		proWebView.setVisibility(View.VISIBLE);

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private void callPushNotification(final String pushID) {
		try {

			final VolleyWrapper manager = new VolleyWrapper(URL_GET_NOTICATIONS_MESSAGE);
			String[] name = {JTAG_ACCESSTOKEN, JTAG_NOTIFICATION_ID};
			String[] value = {PreferenceManager.getAccessToken(mActivity), pushID};
			manager.getResponsePOST(mActivity, 11, name, value, new VolleyWrapper.ResponseListener() {

				@Override
				public void responseSuccess(String successResponse) {
					System.out.println("NofifyRes Message:" + successResponse);

					if (successResponse != null) {
						try {
							JSONObject rootObject = new JSONObject(successResponse);
							String responseCode = rootObject.getString(JTAG_RESPONSECODE);
							if (responseCode.equalsIgnoreCase(RESPONSE_SUCCESS)) {
								JSONObject responseObject = rootObject.optJSONObject(JTAG_RESPONSE);
								String statusCode = responseObject.getString(JTAG_STATUSCODE);
								if (statusCode.equalsIgnoreCase(STATUS_SUCCESS)) {
									JSONArray dataArray = responseObject.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);
									for (int i = 0; i < dataArray.length(); i++) {
										JSONObject dataObject = dataArray.getJSONObject(i);
										id = dataObject.optString("id");
										title = dataObject.optString("title");
										message = dataObject.optString("message");
										System.out.println("Videomessage:"+message);
										url = dataObject.optString("url");
										date = dataObject.optString("date");
										String pushNotificationDetail = "<!DOCTYPE html>\n" +
												"<html>\n" +
												"<head>\n" +
												"<style>\n" +
												"\n" +
												"@font-face {\n" +
												"font-family: SourceSansPro-Semibold;" +
												"src: url(SourceSansPro-Semibold.ttf);" +

												"font-family: SourceSansPro-Regular;" +
												"src: url(SourceSansPro-Regular.ttf);" +
												"}" +
												".title {" +
												"font-family: SourceSansPro-Regular;" +
												"font-size:16px;" +
												"text-align:left;" +
												"color:	#46C1D0;" +
												"text-align: ####TEXT_ALIGN####;" +
												"}" +
												".description {" +
												"font-family: SourceSansPro-Semibold;" +
												"text-align:justify;" +
												"font-size:14px;" +
												"color: #000000;" +
												"text-align: ####TEXT_ALIGN####;" +
												"}" +
												"</style>\n" + "</head>" +
												"<body>" +
												"<p class='title'>" + message;
										pushNotificationDetail = pushNotificationDetail + "<p class='description'>" + Day + "-" + Month + "-" + Year + " " + PushDate + "</p>";
										System.out.println("URL IMAGE" + url);
										if (!url.equalsIgnoreCase("")) {
											pushNotificationDetail = pushNotificationDetail + "<center><img src='" + url + "'width='100%', height='auto'>";
										}
										pushNotificationDetail = pushNotificationDetail +
												"</body>\n</html>";

										webView.setWebViewClient(new VideoWebViewActivityNew.HelloWebViewClient());
										webView.getSettings().setJavaScriptEnabled(true);
										webView.getSettings().setPluginState(PluginState.ON);
										webView.getSettings().setBuiltInZoomControls(false);
										webView.getSettings().setDisplayZoomControls(true);
										webView.setWebViewClient(new VideoWebViewActivityNew.HelloWebViewClient());
//										textcontent.getSettings().setJavaScriptEnabled(true);
//										textcontent.getSettings().setPluginState(PluginState.ON);
//										textcontent.getSettings().setBuiltInZoomControls(false);
//										textcontent.getSettings().setDisplayZoomControls(true);
										System.out.println("Messagedata:"+pushNotificationDetail);
										//textcontent.loadData(pushNotificationDetail, "text/html; charset=utf-8", "utf-8");
										String frameVideo = "<html>" + "<br><iframe width=\"320\" height=\"250\" src=\"";
										String url_Video = frameVideo + url + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
										String urlYoutube = url_Video.replace("watch?v=", "embed/");
										System.out.println("urlYoutube:" + urlYoutube);
										webView.loadData(urlYoutube, "text/html", "utf-8");
										//textcontent.setText(message);
										proWebView.setVisibility(View.VISIBLE);
									}

								} else if (statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
										statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
										statusCode.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)) {
									AppUtils.postInitParam(mActivity, new AppUtils.GetAccessTokenInterface() {
										@Override
										public void getAccessToken() {
										}
									});
									callPushNotification(pushID);

								}
							} else if (responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
									responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
									responseCode.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)) {
								AppUtils.postInitParam(mActivity, new AppUtils.GetAccessTokenInterface() {
									@Override
									public void getAccessToken() {
									}
								});
								callPushNotification(pushID);

							} else {
								Toast.makeText(mActivity, "Some Error Occured", Toast.LENGTH_SHORT).show();

							}


						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

				@Override
				public void responseFailure(String failureResponse) {
					// CustomStatusDialog(RESPONSE_FAILURE);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	private class HelloWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return true;
		}

		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);

		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			proWebView.setVisibility(View.GONE);
			textcontent.setVisibility(View.VISIBLE);
		}

	}
}


