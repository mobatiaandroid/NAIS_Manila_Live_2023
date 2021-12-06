package com.mobatia.nasmanila.activities.pdf;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.manager.HeaderManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class pdfview extends AppCompatActivity implements View.OnClickListener {
    PDFView pdf;
    String URL;
    HeaderManager headermanager;
    LinearLayout relativeHeader;
    private ImageView mImageBack;
    private Handler handler;
    private Runnable runnable;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        mContext = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            URL = extras.getString("pdf_url");
            // and get whatever type user account id is
        }
        //===============AutoLogout Timer=========================
       // handler = new Handler();
        /*runnable = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(pdfview.this, HomeActivityNew.class);
                MobiCarePreferenceManager.setIsLogin(mContext, "no");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        };*/

       // startTimer(); //will start the timer. This must be withing onCreate() scope.

        //==========================================================
        relativeHeader = (LinearLayout) findViewById(R.id.relativeHeader);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        /*if (MobiCarePreferenceManager.getLanguage(pdfview.this).equals("1")) {
            headermanager = new HeaderManager(pdfview.this, getResources().getString(R.string.medicalreport));
        } else {
            headermanager = new HeaderManager(pdfview.this, getResources().getString(R.string.medicalreport_arabic));
        }*/
        //headermanager.getHeader(relativeHeader, 1);
        /*mImageBack = headermanager.getLeftButton();
        headermanager.setButtonLeftSelector(R.drawable.back,
                R.drawable.back);
        mImageBack.setOnClickListener(this);*/
        pdf = (PDFView) findViewById(R.id.pdfView);
        new DownloadPDF().execute();
    }

    class DownloadPDF extends AsyncTask<String, Void, Void> {

        private Exception exception;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(pdfview.this);
            dialog.setMessage(getResources().getString(R.string.pleasewait));//Please wait...
            dialog.show();
        }

        protected Void doInBackground(String... urls) {
            java.net.URL u = null;
            try {
                String fileName = "test.pdf";
                u = new URL(URL);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                // c.setDoOutput(true);


                String auth = "SGHCXFTPUser" + ":" + "cXFTPu$3r";
                String encodedAuth = new String(Base64.encodeToString(auth.getBytes(), Base64.DEFAULT));
                encodedAuth = encodedAuth.replace("\n", "");

                c.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                c.addRequestProperty("Authorization", "Basic " + encodedAuth);
                //c.setRequestProperty("Accept", "application/json");
                // c.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                c.connect();

                int response = c.getResponseCode();
                String PATH = Environment.getExternalStorageDirectory()
                        + "/download/";
                // Log.d("Abhan", "PATH: " + PATH);
                File file = new File(PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File outputFile = new File(file, fileName);
                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "test.pdf");
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "test.pdf");
            // System.out.println("file.exists() = " + file.exists());
            // pdf.fromUri(uri);

            pdf.fromFile(file).defaultPage(1).enableSwipe(true).load();

            //web.loadUrl(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "test.pdf");
            // Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                onBackPressed();
                break;
        }
    }

   /* @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            stopTimer();
            Intent mIntent = new Intent(pdfview.this, LabAndRadiologyActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mIntent);
        } else {

            stopTimer();
            Intent mIntent = new Intent(pdfview.this, LabAndRadiologyActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mIntent);
        }
    }*/
    /*@Override
    public void onUserInteraction() {
        super.onUserInteraction();
        resetTimer();
    }*/
  /*  @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){

            resetTimer();
        }else{
            stopTimer();

        }
    }*/
    /*@Override
    protected void onPause() {
        super.onPause();
        Log.e( "onPause: ","MIN" );
        stopTimer();
    }*/
    @Override
    protected void onStop() {
        super.onStop();
 /*   Log.e( "onStop: ","stop" );
    Date date = new Date();
    String strDateFormat = "HH:mm";
    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
    String formattedDate= dateFormat.format(date);
    MobiCarePreferenceManager.setLastLogoutTime(mContext,formattedDate);*/

    }
   /* public void stopTimer() {
        handler.removeCallbacks(runnable);
    }

    public void startTimer() {
        handler.postDelayed(runnable, 600000); //for 15 minutes 15*60*1000
    }

    public void resetTimer() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 600000); //for 15 minutes 15*60*1000
    }*/

}

