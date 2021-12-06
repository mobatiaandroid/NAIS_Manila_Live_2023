package com.mobatia.nasmanila.activities.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.widget.Toast;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.activities.login.LoginActivity;
import com.mobatia.nasmanila.activities.tutorial.TutorialActivity;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.service.OnClearFromRecentService;


import java.util.HashMap;
import java.util.Map;


public class SplashActivity extends Activity implements
        IntentPassValueConstants, JSONConstants, URLConstants {

    private Context mContext;

//    String[] permissions = new String[]{
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.READ_CALENDAR
//            , Manifest.permission.WRITE_CALENDAR ,
//            Manifest.permission.CALL_PHONE,
//            Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this,
//                SplashActivity.class));
        mContext = this;
//        goToNextView();

        // push notification service call
        /*if (WissPreferenceManager.getDeviceGCMID(mContext).equals("")
                || WissPreferenceManager.getProfileID(mContext).equals("")) {*/
        if (AppUtils.checkInternet(mContext)) {
            AppUtils.postInitParams(mContext, new AppUtils.GetAccessTokenInterface() {
                @Override
                public void getAccessToken() {
                }
            });
            goToNextView();

//            if (Build.VERSION.SDK_INT < 23) {
//                //Do not need to check the permission
//                goToNextView();
//
//            } else {
//
//                if (hasPermissions(mContext, permissions)) {
//                    goToNextView();
//
//                } else {
//                    ActivityCompat.requestPermissions(this, permissions, 100);
//                }
//            }
        }else{
            AppUtils.showDialogAlertDismiss((Activity)mContext,"Network Error",getString(R.string.no_internet),R.drawable.nonetworkicon,R.drawable.roundred);

        }

        //}

    }

/*private  boolean checkAndRequestPermissions() {
int permissionSendMessage = ContextCompat.checkSelfPermission(this,
Manifest.permission.CAMERA);
int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
List<String> listPermissionsNeeded = new ArrayList<>();
if (locationPermission != PackageManager.PERMISSION_GRANTED) {
listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
}
if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
listPermissionsNeeded.add(Manifest.permission.CAMERA);
}
if (!listPermissionsNeeded.isEmpty()) {
ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
return false;
}
return true;
}*/

/*private boolean checkAndRequestPermissions() {
int result;
List<String> listPermissionsNeeded = new ArrayList<>();
for (String p:permissions) {
result = ContextCompat.checkSelfPermission(mContext,p);
if (result != PackageManager.PERMISSION_GRANTED) {
listPermissionsNeeded.add(p);
}
}
if (!listPermissionsNeeded.isEmpty()) {
ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS );
return false;
}else{
goToNextView();
}
return true;
}*/

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /*******************************************************
     * Method name : goToNextView() Description : go to next view after a delay
     * Parameters : nil Return type : void Date : March 8, 2017 Author : RIJO K JOSE
     *****************************************************/
    private void goToNextView() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
                if (PreferenceManager.getIsFirstLaunch(mContext)&& PreferenceManager.getUserId(mContext).equals("")) {
                    Intent tutorialIntent = new Intent(mContext,
                            TutorialActivity.class);
                    tutorialIntent.putExtra(TYPE, 1);
                    startActivity(tutorialIntent);
                    finish();
                }
                else  if(PreferenceManager.getUserId(mContext).equals("")) {
                    Intent loginIntent = new Intent(mContext,
                            LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }else{
//                    Intent loginIntent = new Intent(mContext,
//                            HomeListActivity.class);
                    Intent loginIntent = new Intent(mContext,
                            HomeListAppCompatActivity.class);
                    startActivity(loginIntent);
                    finish();
                }

            }
        }, 5000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
// TODO Auto-generated method stub
//super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 100: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
// Initial
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CALENDAR, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CALENDAR, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
// Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            showRationale = shouldShowRequestPermissionRationale( permission );
                        }
                        if (! showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            Toast.makeText(mContext, "Unable to continue without granting permissions", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", mContext.getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }
                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                        &&perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED&& (grantResults.length > 0)) {
// All Permissions Granted
                    goToNextView();


                } else {

                    ActivityCompat.requestPermissions(this, permissions, 100);


                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}