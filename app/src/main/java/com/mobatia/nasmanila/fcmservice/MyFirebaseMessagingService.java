package com.mobatia.nasmanila.fcmservice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.manager.NotificationID;

import org.json.JSONException;
import org.json.JSONObject;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by RIJO K JOSE  on 20/10/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Intent intent;
    LayoutInflater mInflater;
  int badgecount=0;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {




        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

        }

        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


    }

    private void sendNotification(String messageBody) {
        ShortcutBadger.applyCount(this, badgecount); //for 1.1.4+

        intent = new Intent(this, HomeListAppCompatActivity.class);
        intent.setAction(Long.toString(System.currentTimeMillis()));

        intent.putExtra("Notification_Recieved",1);

        int notId= NotificationID.getID();

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(notId, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = getString(R.string.app_name)+"_01";// The id of the channel.
            CharSequence name = getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationBuilder.setChannelId(mChannel.getId());
            mChannel.setShowBadge(true);
            mChannel.canShowBadge();
            mChannel.enableLights(true);
            mChannel.setLightColor(getResources().getColor(R.color.split_bg));
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.notify_small);
            notificationBuilder.setColor(getResources().getColor(R.color.split_bg))
            ;
        } else {
            notificationBuilder.setSmallIcon(R.drawable.nas_large);

        }

        notificationManager.notify(notId, notificationBuilder.build());

    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("body");

            String badge = data.getString("badge");
            badgecount=Integer.valueOf(badge);
            String message = data.getString("message");
//            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            sendNotification(message);
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}