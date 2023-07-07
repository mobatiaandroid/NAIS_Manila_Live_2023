/**
 *
 */
package com.mobatia.nasmanila.fragments.notifications;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.notificationssplitting.AudioPlayerViewActivityNew;
import com.mobatia.nasmanila.activities.notificationssplitting.ImageActivityNew;
import com.mobatia.nasmanila.activities.notificationssplitting.TextalertActivityNew;
import com.mobatia.nasmanila.activities.notificationssplitting.VideoWebViewActivityNew;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.notifications.adapter.PushNotificationListAdapter;
import com.mobatia.nasmanila.fragments.notifications.model.PushNotificationModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.ItemOffsetDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * @author Rijo K Jose
 *
 */

public class NotificationsFragment extends Fragment implements
        NaisTabConstants, CacheDIRConstants, URLConstants,
        IntentPassValueConstants, NaisClassNameConstants, JSONConstants, StatusConstants {
    TextView mTitleTextView;
    private View mRootView;
    private Context mContext;
    private RecyclerView notification_recycler;
    private SwipeRefreshLayout swipe_notification;
    private String mTitle;
    private String mTabId;
    private RelativeLayout relMain;
    private ImageView mBannerImage;
    ArrayList<PushNotificationModel> pushNotificationArrayList;
    PushNotificationListAdapter mPushNotificationListAdapter;
    Intent mIntent;
    String myFormatCalender = "yyyy-MM-dd HH:mm:ss";
    static SimpleDateFormat sdfcalender;
    boolean isFromBottom = false;
    boolean swiperefresh = false;
    String pagefrom = "";
    String scroll_to = "";

    int notificationSize = 0;

    public NotificationsFragment() {

    }

    public NotificationsFragment(String title, String tabId) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_notification_list, container,
                false);

//		setHasOptionsMenu(true);
        mContext = getActivity();
        Log.e("token",PreferenceManager.getFCMID(mContext));
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

        swipe_notification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("SWIPEREFRESH", "WORKING");
                int listSize = pushNotificationArrayList.size();
                //pagefrom=pushNotificationArrayList.get(listSize-1).getId();
                pagefrom = "";
                scroll_to = "new";
                System.out.println("Page From Refresh " + pagefrom);
                if (AppUtils.isNetworkConnected(mContext)) {
                    swiperefresh = true;
                    System.out.println("Refreshcrolldata " + pagefrom);

                    callPushNotification(URL_GET_NOTICATIONS_LIST, JTAG_ACCESSTOKEN, JTAG_DEVICE_iD, JTAG_DEVICE_tYPE, JTAG_USERS_ID, pagefrom, scroll_to);
                } else {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
                }

                swipe_notification.setRefreshing(false);

            }
        });
        return mRootView;
    }


    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Jan 12, 2015 Author : Vandana
     * Surendranath
     *****************************************************/
    private void initialiseUI() {
        mTitleTextView = (TextView) mRootView.findViewById(R.id.titleTextView);
        mTitleTextView.setText(NOTIFICATIONS);
        isFromBottom = false;
        swiperefresh = false;
        pushNotificationArrayList = new ArrayList<PushNotificationModel>();
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

                        if (pushNotificationArrayList.get(position).getPushType().equalsIgnoreCase("")) {
                            mIntent = new Intent(mContext, TextalertActivityNew.class);
                            mIntent.putExtra(POSITION, position);
                            mIntent.putExtra("PushID", pushNotificationArrayList.get(position).getId());
                            mIntent.putExtra("Day", pushNotificationArrayList.get(position).getDay());
                            mIntent.putExtra("Month", pushNotificationArrayList.get(position).getMonthString());
                            mIntent.putExtra("Year", pushNotificationArrayList.get(position).getYear());
                            mIntent.putExtra("PushDate", pushNotificationArrayList.get(position).getPushTime());
                            mContext.startActivity(mIntent);
                        }
                        if (pushNotificationArrayList.get(position).getPushType().equalsIgnoreCase("Image") || pushNotificationArrayList.get(position).getPushType().equalsIgnoreCase("Text") || pushNotificationArrayList.get(position).getPushType().equalsIgnoreCase("Text") || pushNotificationArrayList.get(position).getPushType().equalsIgnoreCase("Image")) {
                            mIntent = new Intent(mContext, ImageActivityNew.class);
                            mIntent.putExtra("PushID", pushNotificationArrayList.get(position).getId());
                            mIntent.putExtra("Day", pushNotificationArrayList.get(position).getDay());
                            mIntent.putExtra("Month", pushNotificationArrayList.get(position).getMonthString());
                            mIntent.putExtra("Year", pushNotificationArrayList.get(position).getYear());
                            mIntent.putExtra("PushDate", pushNotificationArrayList.get(position).getPushTime());
                            System.out.println("pushID" + pushNotificationArrayList.get(position).getId());
                            mContext.startActivity(mIntent);
                        }
                        if (pushNotificationArrayList.get(position).getPushType().equalsIgnoreCase("Voice")) {
                            mIntent = new Intent(mContext, AudioPlayerViewActivityNew.class);
                            mIntent.putExtra("PushID", pushNotificationArrayList.get(position).getId());
                            mIntent.putExtra("Day", pushNotificationArrayList.get(position).getDay());
                            mIntent.putExtra("Month", pushNotificationArrayList.get(position).getMonthString());
                            mIntent.putExtra("Year", pushNotificationArrayList.get(position).getYear());
                            mIntent.putExtra("PushDate", pushNotificationArrayList.get(position).getPushTime());
                            mContext.startActivity(mIntent);
                        }
                        if (pushNotificationArrayList.get(position).getPushType().equalsIgnoreCase("Video")) {
                            mIntent = new Intent(mContext, VideoWebViewActivityNew.class);
                            mIntent.putExtra("PushID", pushNotificationArrayList.get(position).getId());
                            mIntent.putExtra("Day", pushNotificationArrayList.get(position).getDay());
                            mIntent.putExtra("Month", pushNotificationArrayList.get(position).getMonthString());
                            mIntent.putExtra("Year", pushNotificationArrayList.get(position).getYear());
                            mIntent.putExtra("PushDate", pushNotificationArrayList.get(position).getPushTime());
                            mContext.startActivity(mIntent);
                        }

                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));

    }


    private PushNotificationModel getSearchValues(JSONObject Object)
            throws JSONException {
        PushNotificationModel mPushNotificationModel = new PushNotificationModel();
//        mPushNotificationModel.setPushType(Object.optString(JTAG_PUSH_TYPE));
//        mPushNotificationModel.setPushTitle(Object.optString(JTAG_PUSH_MESSAGE));
//        mPushNotificationModel.setPushUrl(Object.optString(JTAG_PUSH_URL));
//        mPushNotificationModel.setPushDate(Object.optString(JTAG_DATE));
        mPushNotificationModel.setPushType(Object.optString("alert_type"));
        mPushNotificationModel.setId(Object.optString("id"));
        mPushNotificationModel.setHeadTitle(Object.optString("title"));
        mPushNotificationModel.setPushDate(Object.optString("time_Stamp"));


        String mDate = mPushNotificationModel.getPushDate();
        Date mEventDate = new Date();

        try {
            mEventDate = sdfcalender.parse(mDate);
            SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            String startTime = format2.format(mEventDate);
            mPushNotificationModel.setPushTime(startTime);
            ;
        } catch (ParseException ex) {
            Log.e("Date", "Parsing error");
        }
        String dayOfTheWeek = (String) DateFormat.format("EEEE", mEventDate); // Thursday
        String day = (String) DateFormat.format("dd", mEventDate); // 20
        String monthString = (String) DateFormat.format("MMM", mEventDate); // June
        String monthNumber = (String) DateFormat.format("MM", mEventDate); // 06
        String year = (String) DateFormat.format("yyyy", mEventDate); // 2013
        mPushNotificationModel.setDayOfTheWeek(dayOfTheWeek);
        mPushNotificationModel.setDay(day);
        mPushNotificationModel.setMonthString(monthString);
        mPushNotificationModel.setMonthNumber(monthNumber);
        mPushNotificationModel.setYear(year);
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
//                                                Log.e("NotificationmodelItem", String.valueOf(getSearchValues(dataObject).getHeadTitle()));
                                                pushNotificationArrayList.add(getSearchValues(dataObject));

                                            } else {
                                                //Checking duplication items in arraylist
                                                boolean isFound = false;
                                                for (int j = 0; j < pushNotificationArrayList.size(); j++) {
                                                    String listId = dataObject.optString("id");
                                                    if (listId.equalsIgnoreCase(pushNotificationArrayList.get(j).getId())) {
                                                        isFound = true;
                                                    }
                                                }
                                                if (!isFound) {
                                                    pushNotificationArrayList.add(getSearchValues(dataObject));
                                                    System.out.println("Arraylistsize:" + pushNotificationArrayList.size());
                                                }

                                            }
                                        }

                                        mPushNotificationListAdapter = new PushNotificationListAdapter(mContext, pushNotificationArrayList);
                                        notification_recycler.setAdapter(mPushNotificationListAdapter);

                                        if (isFromBottom) {
                                            Log.e("Reachedbottomcondition", "working");
                                            notification_recycler.scrollToPosition(pushNotificationArrayList.size() - 16);
                                            //pushListView.smoothScrollToPosition(pushNotificationArrayList.size()-16);
                                        }
                                        if (swiperefresh) {
                                            notification_recycler.scrollToPosition(pushNotificationArrayList.size());
                                            Log.e("swiprefreshcondition", "working");

                                        }
                                        mPushNotificationListAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
                                            @Override
                                            public void onBottomReached(int position) {
                                                Log.e("Reachedbottom", "working");
                                                isFromBottom = true;
                                                int listSize = pushNotificationArrayList.size();
                                                pagefrom = pushNotificationArrayList.get(listSize - 1).getId();
                                                // scroll_to = "old";
                                                String scroll = "old";
                                                System.out.println("Page From Value" + pagefrom);
                                                if (notificationSize == 15) {
                                                    if (AppUtils.isNetworkConnected(mContext)) {
                                                        System.out.println("Scrolldata" + scroll);
                                                        callPushNotification(URL_GET_NOTICATIONS_LIST, JTAG_ACCESSTOKEN, JTAG_DEVICE_iD, JTAG_DEVICE_tYPE, JTAG_USERS_ID, pagefrom, scroll);

                                                    } else {
                                                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                                                    }
                                                }

                                            }
                                        });
                                        System.out.println("Swiperefreshdata:" + swiperefresh);
                                    } else {
                                        if (swiperefresh) {

                                        } else {
                                            Toast.makeText(mContext, "Currently you do not have any notification.", Toast.LENGTH_SHORT).show();

                                        }

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
                                    isFromBottom = false;
                                    swiperefresh = false;

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
                                isFromBottom = false;
                                swiperefresh = false;
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

//    private void callPushNotification() {
//        pushNotificationArrayList = new ArrayList<PushNotificationModel>();
//
//        try {
//
//            final VolleyWrapper manager = new VolleyWrapper(URL_GET_NOTICATIONS_LIST);
//            String[] name = {JTAG_ACCESSTOKEN, JTAG_DEVICE_iD, JTAG_DEVICE_tYPE, JTAG_USERS_ID};
//            System.out.print("useridnew:" + JTAG_USERS_ID);
//            System.out.println("USERID:" + PreferenceManager.getUserId(mContext));
//            String[] value = {PreferenceManager.getAccessToken(mContext), FirebaseInstanceId.getInstance().getToken(), "2", PreferenceManager.getUserId(mContext)};
//            manager.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {
//
//                @Override
//                public void responseSuccess(String successResponse) {
//                    String responsCode = "";
//                    System.out.println("NofifyRes:" + successResponse);
//
//                    if (successResponse != null) {
//                        try {
//                            JSONObject rootObject = new JSONObject(successResponse);
//                            String responseCode = rootObject.getString(JTAG_RESPONSECODE);
//                            if (responseCode.equalsIgnoreCase(RESPONSE_SUCCESS)) {
//                                JSONObject responseObject = rootObject.optJSONObject(JTAG_RESPONSE);
//                                String statusCode = responseObject.getString(JTAG_STATUSCODE);
//                                if (statusCode.equalsIgnoreCase(STATUS_SUCCESS)) {
//                                    JSONArray dataArray = responseObject.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);
//                                    if (dataArray.length() > 0) {
//                                        notificationSize = dataArray.length();
//                                        for (int i = 0; i < dataArray.length(); i++) {
//                                            JSONObject dataObject = dataArray.getJSONObject(i);
//                                            if (pushNotificationArrayList.size() == 0) {
//                                                pushNotificationArrayList.add(getSearchValues(dataObject));
//
//                                            } else {
//                                                //Checking duplication items in arraylist
//                                                boolean isFound = false;
//                                                for (int j = 0; j < pushNotificationArrayList.size(); j++) {
//                                                    String listId = dataObject.optString("id");
//                                                    if (listId.equalsIgnoreCase(pushNotificationArrayList.get(j).getId())) {
//                                                        isFound = true;
//                                                    }
//                                                }
//                                                if (!isFound) {
//                                                    pushNotificationArrayList.add(getSearchValues(dataObject));
//                                                }
//
//                                            }
//                                        }
//
//                                        mPushNotificationListAdapter = new PushNotificationListAdapter(mContext, pushNotificationArrayList);
//                                        notification_recycler.setAdapter(mPushNotificationListAdapter);
//
//                                        if (isFromBottom) {
//                                            Log.e("Reachedbottom", "working");
//                                            notification_recycler.scrollToPosition(pushNotificationArrayList.size() - 16);
//                                            //pushListView.smoothScrollToPosition(pushNotificationArrayList.size()-16);
//                                        }
//
//                                    } else {
//                                        Toast.makeText(mContext, "Currently you do not have any notification.", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else if (statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
//                                        statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)) {
//                                    AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
//                                        @Override
//                                        public void getAccessToken() {
//                                        }
//                                    });
//                                    callPushNotification();
//
//                                }
//                            } else if (responsCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
//                                    responsCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)) {
//                                AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
//                                    @Override
//                                    public void getAccessToken() {
//                                    }
//                                });
//                                callPushNotification();
//
//                            } else {
//                                Toast.makeText(mContext, "Some Error Occured", Toast.LENGTH_SHORT).show();
//
//                            }
//
//
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void responseFailure(String failureResponse) {
//                    // CustomStatusDialog(RESPONSE_FAILURE);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private void clearBadge() {
        pushNotificationArrayList = new ArrayList<PushNotificationModel>();

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
