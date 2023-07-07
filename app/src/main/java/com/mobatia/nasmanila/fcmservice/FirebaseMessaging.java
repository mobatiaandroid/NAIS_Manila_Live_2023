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

/*** Class not used **/


public class FirebaseMessaging extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Intent intent;
    LayoutInflater mInflater;
    int badgecount=0;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                String mess = remoteMessage.getData().get("message");
                sendNotification(mess);
//                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        try {
            String message = json.getString("message");
            sendNotification(message);
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void sendNotification(String message) {
        ShortcutBadger.applyCount(this, badgecount); //for 1.1.4+
//        ShortcutBadger.with(this).count(badgeCount); //for 1.1.3

        intent = new Intent(this, HomeListAppCompatActivity.class);
        intent.setAction(Long.toString(System.currentTimeMillis()));

        intent.putExtra("Notification_Recieved",1);


        int notId= NotificationID.getID();

// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
// Get the PendingIntent containing the entire back stack
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(notId, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message)
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
}
