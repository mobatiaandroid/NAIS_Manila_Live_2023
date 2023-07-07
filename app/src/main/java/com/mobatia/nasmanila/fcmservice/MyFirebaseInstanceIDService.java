package com.mobatia.nasmanila.fcmservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyFirebaseInstanceIDService extends Service {
    //var mContext: Context


   /* override fun onTokenRefresh() {

        //val refreshedToken = FirebaseInstanceId.getInstance().token

        val refreshedToken = FirebaseInstanceId.getInstance().token.toString()

        Log.e("FIREBASETOKEN", refreshedToken)
        sendRegistrationToServer(refreshedToken)
        super.onTokenRefresh()
    }*/

    private void sendRegistrationToServer(String refreshedToken) {
       /* if (refreshedToken != null) {
            sharedprefs.setFcmID(mContext, refreshedToken)
        }*/

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
