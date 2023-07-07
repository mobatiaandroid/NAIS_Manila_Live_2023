package com.mobatia.nasmanila.fragments.notifications_new;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.notifications.AudioPlayerViewActivity;
import com.mobatia.nasmanila.activities.notifications.ImageActivity;
import com.mobatia.nasmanila.activities.notifications.TextalertActivity;
import com.mobatia.nasmanila.activities.notifications.VideoWebViewActivity;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.notifications_new.adapter.PushNotificationListAdapterNew;
import com.mobatia.nasmanila.fragments.notifications_new.model.PushNotificationModelNew;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.ItemOffsetDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.leolin.shortcutbadger.ShortcutBadger;

public class NotificationsFragmentNew extends Fragment implements
        NaisTabConstants, CacheDIRConstants, URLConstants,
        IntentPassValueConstants, NaisClassNameConstants, JSONConstants, StatusConstants {

    TextView mTitleTextView;
    private View mRootView;
    private Context mContext;
    private RecyclerView notification_recycler;
    private LinearLayout swipe_notification;
    private String mTitle;
    private String mTabId;
    private RelativeLayout relMain;
    private ImageView mBannerImage;
    ArrayList<PushNotificationModelNew> pushNotificationArrayList;
    PushNotificationListAdapterNew mPushNotificationListAdapter;
    Intent mIntent;
    String myFormatCalender = "yyyy-MM-dd HH:mm:ss";
    static SimpleDateFormat sdfcalender;
//    boolean isFromBottom = false;
//    boolean swiperefresh = false;
    String pagefrom = "";
    String scroll_to = "";
    int notificationSize = 0;
    public NotificationsFragmentNew() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public NotificationsFragmentNew(String title, String tabId) {
        this.mTitle = title;
        this.mTabId = tabId;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_notifications_new, container,
                false);

//		setHasOptionsMenu(true);
        mContext = getActivity();
        myFormatCalender = "yyyy-MM-dd HH:mm:ss";
        sdfcalender = new SimpleDateFormat(myFormatCalender, Locale.ENGLISH);
//		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        ShortcutBadger.applyCount(mContext, 0);//badge
        initialiseUI();
        if (AppUtils.isNetworkConnected(mContext)) {
            clearBadge();
            boolean isFromBottom = false;
            // pagefrom = "0";
            callPushNotification(URL_GET_NOTICATIONS_LIST, JTAG_ACCESSTOKEN, JTAG_DEVICE_iD, JTAG_DEVICE_tYPE, JTAG_USERS_ID, pagefrom, scroll_to);
        } else {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
        }

//        swipe_notification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                Log.e("SWIPEREFRESH", "WORKING");
//                int listSize = pushNotificationArrayList.size();
//                //pagefrom=pushNotificationArrayList.get(listSize-1).getId();
//                pagefrom = "";
//                scroll_to = "new";
//                System.out.println("Page From Refresh " + pagefrom);
//                if (AppUtils.isNetworkConnected(mContext)) {
////                    swiperefresh = true;
//                    System.out.println("Refreshcrolldata " + pagefrom);
//
//                    callPushNotification(URL_GET_NOTICATIONS_LIST, JTAG_ACCESSTOKEN, JTAG_DEVICE_iD, JTAG_DEVICE_tYPE, JTAG_USERS_ID, pagefrom, scroll_to);
//                } else {
//                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
//                }
//
////                swipe_notification.setRefreshing(false);
//
//            }
//        });
        return mRootView;
    }
    private void initialiseUI() {
        mTitleTextView = (TextView) mRootView.findViewById(R.id.titleTextView);
        mTitleTextView.setText(NOTIFICATIONS);
//        isFromBottom = false;
//        swiperefresh = false;
        pushNotificationArrayList = new ArrayList<PushNotificationModelNew>();
        notification_recycler = mRootView.findViewById(R.id.notification_recycler);
        swipe_notification = mRootView.findViewById(R.id.swipe_notification);
//		mProgressDialog = (RelativeLayout) mRootView
//				.findViewById(R.id.progressDialog);
//		mBannerImage = (ImageView) mRootView.findViewById(R.id.bannerImage);
        relMain = (RelativeLayout) mRootView.findViewById(R.id.relMain);
        relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //pushListView.setOnItemClickListener(this);
        //pushListView.setOnClickListener((View.OnClickListener) this);
        notification_recycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        int spacing = 10;
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, spacing);
        notification_recycler.addItemDecoration(
                new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));
        notification_recycler.addItemDecoration(itemDecoration);

        notification_recycler.setLayoutManager(linearLayoutManager);


        notification_recycler.addOnItemTouchListener(new RecyclerItemListener(mContext, notification_recycler,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {

                        if (pushNotificationArrayList.get(position).getType().equalsIgnoreCase("Text")) {
                            mIntent = new Intent(mContext, TextalertActivity.class);
                            mIntent.putExtra(POSITION, position);
                            mIntent.putExtra(PASS_ARRAY_LIST, pushNotificationArrayList);
                            mContext.startActivity(mIntent);
                        }
                        if (pushNotificationArrayList.get(position).getType().equalsIgnoreCase("Image")) {
                            mIntent = new Intent(mContext, ImageActivity.class);
                            mIntent.putExtra(POSITION, position);
//                            mIntent.putExtra("message", pushNotificationArrayList.get(position).getMessage());
//                            Log.e("here",pushNotificationArrayList.get(position).getMessage().toString());
//                            mIntent.putExtra("date", pushNotificationArrayList.get(position).getDate());
//                            mIntent.putExtra("url", pushNotificationArrayList.get(position).getUrl());
                            mIntent.putExtra(PASS_ARRAY_LIST, pushNotificationArrayList);
                            mContext.startActivity(mIntent);
                        }
                        if (pushNotificationArrayList.get(position).getType().equalsIgnoreCase("Voice")) {
                            mIntent = new Intent(mContext, AudioPlayerViewActivity.class);
                            mIntent.putExtra(POSITION, position);
                            mIntent.putExtra(PASS_ARRAY_LIST, pushNotificationArrayList);
                            mContext.startActivity(mIntent);
                        }
                        if (pushNotificationArrayList.get(position).getType().equalsIgnoreCase("Video")) {
                            mIntent = new Intent(mContext, VideoWebViewActivity.class);
                            mIntent.putExtra(POSITION, position);
                            mIntent.putExtra(PASS_ARRAY_LIST, pushNotificationArrayList);
                            mContext.startActivity(mIntent);
                        }

                    }


                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));

    }
    private PushNotificationModelNew getSearchValues(JSONObject Object)
            throws JSONException {
        PushNotificationModelNew mPushNotificationModel = new PushNotificationModelNew();
        mPushNotificationModel.setType(Object.optString("type"));
        mPushNotificationModel.setUrl(Object.optString("url"));
        mPushNotificationModel.setDate(Object.optString("date"));
        mPushNotificationModel.setPush_from(Object.optString("push_from"));
        mPushNotificationModel.setMessage(Object.optString("message"));


        return mPushNotificationModel;
    }
    private void callPushNotification(String URL, String jtagAccesstoken, String jtagDeviceid, String jtagDevicetype, String jtagUsersId, String page_from, final String scroll_to) {
        try {
            final VolleyWrapper manager = new VolleyWrapper(URL);
            jtagAccesstoken = PreferenceManager.getAccessToken(mContext);
            jtagDeviceid = PreferenceManager.getFCMID(mContext);
            jtagDevicetype = "2";
            notificationSize = 0;
            Log.e("firbase", PreferenceManager.getFCMID(mContext));
            Log.e("userid", PreferenceManager.getUserId(mContext));
            jtagUsersId = PreferenceManager.getUserId(mContext);
            String[] name = {JTAG_ACCESSTOKEN, JTAG_DEVICE_iD, JTAG_DEVICE_tYPE, JTAG_USERS_ID, JTAG_PAGE_FROM, JTAG_SCROLL_TO};
            String[] value = {PreferenceManager.getAccessToken(mContext), PreferenceManager.getFCMID(mContext), jtagDevicetype, PreferenceManager.getUserId(mContext), page_from, scroll_to};
            System.out.println("Notificationpassing:" + "A:" + PreferenceManager.getAccessToken(mContext) + "DID:" + PreferenceManager.getFCMID(mContext) + "DTYPE:" + jtagDevicetype + "UID:" + PreferenceManager.getUserId(mContext) + "PAGEFROM:" + page_from + "Scrollto:" + scroll_to);
            manager.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {
                @Override
                public void responseSuccess(String successResponse) {
                    System.out.println("NotificationResponse:" + successResponse);
                    if (successResponse != null) {
                        Log.e("Notifications_Response",successResponse);
                        try {
                            JSONObject rootObject = new JSONObject(successResponse);
                            String responseCode = rootObject.getString(JTAG_RESPONSECODE);
                            if (responseCode.equalsIgnoreCase(RESPONSE_SUCCESS)) {
                                JSONObject responseObject = rootObject.optJSONObject(JTAG_RESPONSE);
                                String statusCode = responseObject.getString(JTAG_STATUSCODE);
                                if (statusCode.equalsIgnoreCase(STATUS_SUCCESS)) {
                                    JSONArray dataArray = responseObject.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);
                                    if (dataArray.length() > 0) {
                                        notificationSize = dataArray.length();
                                        for (int i = 0; i < dataArray.length(); i++) {
                                            JSONObject dataObject = dataArray.getJSONObject(i);
                                            if (pushNotificationArrayList.size() == 0) {
                                                Log.e("NotificationmodelItem", String.valueOf(getSearchValues(dataObject).getDate()));
                                                pushNotificationArrayList.add(getSearchValues(dataObject));

                                            } else {
//                                                Checking duplication items in arraylist
                                                boolean isFound = false;
                                                for (int j = 0; j< pushNotificationArrayList.size(); j++){
                                                    PushNotificationModelNew temp = getSearchValues(dataObject);
                                                    if (temp.getDate().equalsIgnoreCase(pushNotificationArrayList.get(j).getDate())){
                                                        isFound = true;
                                                    }
                                                }
                                                if (!isFound){
                                                    pushNotificationArrayList.add(getSearchValues(dataObject));
                                                    System.out.println("Arraylistsize:" + pushNotificationArrayList.size());
                                                }
//                                                for (int j = 0; j < pushNotificationArrayList.size(); j++) {
//                                                    String listId = dataObject.optString("id");
//                                                    if (listId.equalsIgnoreCase(pushNotificationArrayList.get(j).getId())) {
//                                                        isFound = true;
//                                                    }
//                                                }
//                                                if (!isFound) {
//                                                    pushNotificationArrayList.add(getSearchValues(dataObject));
//                                                    System.out.println("Arraylistsize:" + pushNotificationArrayList.size());
//                                                }

                                            }
                                        }
                                        Log.e("Size", String.valueOf(pushNotificationArrayList.size()));
                                        mPushNotificationListAdapter = new PushNotificationListAdapterNew(mContext, pushNotificationArrayList);
                                        notification_recycler.setAdapter(mPushNotificationListAdapter);

//                                        if (isFromBottom) {
//                                            Log.e("Reachedbottomcondition", "working");
//                                            notification_recycler.scrollToPosition(pushNotificationArrayList.size() - 16);
//                                            //pushListView.smoothScrollToPosition(pushNotificationArrayList.size()-16);
//                                        }
//                                        if (swiperefresh) {
//                                            notification_recycler.scrollToPosition(pushNotificationArrayList.size());
//                                            Log.e("swiprefreshcondition", "working");
//
//                                        }
//                                        mPushNotificationListAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
//                                            @Override
//                                            public void onBottomReached(int position) {
//                                                Log.e("Reachedbottom", "working");
//                                                isFromBottom = true;
//                                                int listSize = pushNotificationArrayList.size();
//                                                pagefrom = pushNotificationArrayList.get(listSize - 1).getId();
//                                                // scroll_to = "old";
//                                                String scroll = "old";
//                                                System.out.println("Page From Value" + pagefrom);
//                                                if (notificationSize == 15) {
//                                                    if (AppUtils.isNetworkConnected(mContext)) {
//                                                        System.out.println("Scrolldata" + scroll);
//                                                        callPushNotification(URL_GET_NOTICATIONS_LIST, JTAG_ACCESSTOKEN, JTAG_DEVICE_iD, JTAG_DEVICE_tYPE, JTAG_USERS_ID, pagefrom, scroll);
//
//                                                    } else {
//                                                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
//
//                                                    }
//                                                }
//
//                                            }
//                                        });
//                                        System.out.println("Swiperefreshdata:" + swiperefresh);
                                    } else {
//                                        if (swiperefresh) {
//
//                                        } else {
//                                            Toast.makeText(mContext, "Currently you do not have any notification.", Toast.LENGTH_SHORT).show();
//
//                                        }

                                    }
//
                                } else if (statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                        statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                                        statusCode.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)) {
                                    AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                        @Override
                                        public void getAccessToken() {
                                        }
                                    });
                                    callPushNotification(URL_GET_NOTICATIONS_LIST, JTAG_ACCESSTOKEN, JTAG_DEVICE_iD, JTAG_DEVICE_tYPE, JTAG_USERS_ID, pagefrom, scroll_to);
//                                    isFromBottom = false;
//                                    swiperefresh = false;

                                }
                            }else if (responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED)||
                                    responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)||
                                    responseCode.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)){
                                AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                    @Override
                                    public void getAccessToken() {
                                    }
                                });
                                callPushNotification(URL_GET_NOTICATIONS_LIST, JTAG_ACCESSTOKEN, JTAG_DEVICE_iD, JTAG_DEVICE_tYPE, JTAG_USERS_ID, pagefrom, scroll_to);
//                                isFromBottom = false;
//                                swiperefresh = false;
                            }

                            else {
                                //  Toast.makeText(mContext, "Some Error Occured", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }

                @Override
                public void responseFailure(String failureResponse) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void clearBadge() {
        pushNotificationArrayList = new ArrayList<PushNotificationModelNew>();

        try {

            final VolleyWrapper manager = new VolleyWrapper(URL_CLEAR_BADGE);
            String[] name = {JTAG_ACCESSTOKEN, JTAG_USERS_ID};
            String[] value = {PreferenceManager.getAccessToken(mContext), PreferenceManager.getUserId(mContext)};
            manager.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {

                @Override
                public void responseSuccess(String successResponse) {
                    System.out.println("NofifyRes:" + successResponse);

                    if (successResponse != null) {
                        try {
                            JSONObject rootObject = new JSONObject(successResponse);
                            String responseCode = rootObject.getString(JTAG_RESPONSECODE);
                            if (responseCode.equalsIgnoreCase(RESPONSE_SUCCESS)) {
                                JSONObject responseObject = rootObject.optJSONObject(JTAG_RESPONSE);
                                String statusCode = responseObject.getString(JTAG_STATUSCODE);
                                if (statusCode.equalsIgnoreCase(STATUS_SUCCESS)) {

                                } else if (statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                        statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)) {
                                    AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                        @Override
                                        public void getAccessToken() {
                                        }
                                    });
                                    clearBadge();

                                }
                            } else if (responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                    responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)) {
                                AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                    @Override
                                    public void getAccessToken() {
                                    }
                                });
                                clearBadge();

                            } else {
                                Toast.makeText(mContext, "Some Error Occured", Toast.LENGTH_SHORT).show();

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
    public List<String> pullLinks(String text) {
//        String links ="";
        List<String> links = new ArrayList<String>();

        //String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        String regex = "\\(?\\b(https?://|http?://|www[.]|ftp://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);

        while (m.find()) {
            String urlStr = m.group();

            if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }

//            links=urlStr;
            if (!(links.contains(urlStr)))
                links.add(urlStr);

        }

        return links;
    }
}