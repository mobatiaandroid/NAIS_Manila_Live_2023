package com.mobatia.nasmanila.activities.notificationssplitting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.notifications.model.PushNotificationModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import tcking.github.com.giraffeplayer.GiraffePlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;

import static com.mobatia.nasmanila.constants.JSONConstants.JTAG_ACCESSTOKEN;
import static com.mobatia.nasmanila.constants.JSONConstants.JTAG_NOTIFICATION_ID;
import static com.mobatia.nasmanila.constants.JSONConstants.JTAG_RESPONSE;
import static com.mobatia.nasmanila.constants.JSONConstants.JTAG_RESPONSECODE;
import static com.mobatia.nasmanila.constants.JSONConstants.JTAG_RESPONSE_DATA_ARRAY;
import static com.mobatia.nasmanila.constants.JSONConstants.JTAG_STATUSCODE;
import static com.mobatia.nasmanila.constants.StatusConstants.RESPONSE_ACCESSTOKEN_EXPIRED;
import static com.mobatia.nasmanila.constants.StatusConstants.RESPONSE_ACCESSTOKEN_MISSING;
import static com.mobatia.nasmanila.constants.StatusConstants.RESPONSE_INVALID_TOKEN;
import static com.mobatia.nasmanila.constants.StatusConstants.RESPONSE_SUCCESS;
import static com.mobatia.nasmanila.constants.StatusConstants.STATUS_SUCCESS;
import static com.mobatia.nasmanila.constants.URLConstants.URL_GET_NOTICATIONS_MESSAGE;

/**
 * Created by Rijo on 25/1/17.
 */
public class AudioPlayerViewActivityNew extends Activity implements IntentPassValueConstants {
    Bundle extras;
    String url;
    Context mContext;
    GiraffePlayer player;
    ArrayList<PushNotificationModel> alertlist;
    Activity activity;
    int position;
    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    ImageView backImg;
    ImageView home;
    String id = "";
    String title = "";
    String message = "";
    String date = "";
    String Day = "";
    String Month = "";
    String Year = "";
    String PushDate = "";
    String PushID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_player_push_activity);
        mContext = this;
        activity = this;

        extras = getIntent().getExtras();
        if (extras != null) {
            Day = extras.getString("Day");
            Month = extras.getString("Month");
            Year = extras.getString("Year");
            PushDate = extras.getString("PushDate");
            PushID = extras.getString("PushID");

        }

        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        headermanager = new HeaderManager(activity, "Notification");
        headermanager.getHeader(relativeHeader, 0);
        backImg = headermanager.getLeftButton();
        headermanager.setButtonLeftSelector(R.drawable.back,
                R.drawable.back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        player = new GiraffePlayer(this);
        if (AppUtils.isNetworkConnected(mContext)) {
            callPushNotification(PushID);
        } else {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
        }
        player.onComplete(new Runnable() {
            @Override
            public void run() {
//callback when video is finish
                Toast.makeText(getApplicationContext(), "Play completed", Toast.LENGTH_SHORT).show();
            }
        }).onInfo(new GiraffePlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
//do something when buffering start
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
//do something when buffering end
                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
//download speed
//                        ((TextView) findViewById(R.id.tv_speed)).setText(Formatter.formatFileSize(getApplicationContext(), extra)+"/s");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//do something when video rendering
//                        findViewById(R.id.tv_speed).setVisibility(View.GONE);
                        break;
                }
            }
        }).onError(new GiraffePlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                System.out.println("ERRORDATA:" + what);
                System.out.println("ERRORDATA:" + extra);

//UtilityMethods.showAlertFinish(activity,"Can't play this video","","OK",false);
                Toast.makeText(getApplicationContext(), "Can't play this audio", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
// Find your VideoView in your video_main.xml layout
       /* videoview = (VideoView) findViewById(R.id.VideoView);
        // Execute StreamVideo AsyncTask


        final CustomProgressBar pDialog = new CustomProgressBar(activity,
                R.drawable.spinner);

        if (!activity.isFinishing()) {
            if (pDialog != null) {
                pDialog.show();
            }
        }

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    Watch_WebView_Activity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(url);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Alert", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
//                pDialog.dismiss();
                try {
                    if (!activity.isFinishing()) {
                        if (pDialog != null) {
                            if (pDialog.isShowing()) {
                                pDialog.dismiss();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((Activity) activity).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomDialog customDialog = new CustomDialog
                                    (activity, activity.getResources().getString(R.string.error_heading), activity.getResources().getString(R.string.common_error));
                            customDialog.show();
                        }
                    });
                }
                videoview.start();
            }
        });
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                Log.i("VideoView", "onCompletion()");
                finish();
            }
        });*/


    public String replace(String str) {
        return str.replaceAll(" ", "%20");
    }

    private void callPushNotification(final String pushID) {
        try {

            final VolleyWrapper manager = new VolleyWrapper(URL_GET_NOTICATIONS_MESSAGE);
            String[] name = {JTAG_ACCESSTOKEN, JTAG_NOTIFICATION_ID};
            String[] value = {PreferenceManager.getAccessToken(mContext), pushID};
            manager.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {

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
                                        url = dataObject.optString("url");
                                        date = dataObject.optString("time_Stamp");
                                        player.play(url);
                                    }

                                } else if (statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                        statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                                        statusCode.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)) {
                                    AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                        @Override
                                        public void getAccessToken() {
                                        }
                                    });
                                    callPushNotification(pushID);

                                }
                            } else if (responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                    responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                                    responseCode.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)) {
                                AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                    @Override
                                    public void getAccessToken() {
                                    }
                                });
                                callPushNotification(pushID);

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
}