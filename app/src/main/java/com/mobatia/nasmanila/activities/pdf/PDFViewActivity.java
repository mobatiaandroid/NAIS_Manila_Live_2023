package com.mobatia.nasmanila.activities.pdf;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PDFViewActivity extends AppCompatActivity {
    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    ImageView back, download,backImageView, print, share;
    PDFView pdf;
    String url, title, name;
    URL downloadURL = null;
    String downloadFileName;
    Bundle extras;
    Context mContext;
    ProgressDialog mProgressDialog;
    private String pdfUrl = null;
    private PrintManager printManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_pdfview2);
        mContext = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
        //  LocaleHelper.setLocale(getApplicationContext(), PrefUtils.getLanguageString(mContext));
        Log.e("Environment.DirctoryDoc",Environment.DIRECTORY_DOCUMENTS);
        Log.e("getexternalPublic", String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)));
        extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("pdf_url");
            title = extras.getString("title");
            name = extras.getString("filename");
           pdfUrl = AppUtils.replace(extras.getString("pdf_url").replace("&","%26"));
            Log.e("pdf",pdfUrl);
            try {
                downloadURL = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            downloadFileName = downloadURL.getPath();
//                Log.e("d1",downloadFileName);
            downloadFileName = downloadFileName.substring(downloadFileName.lastIndexOf('/') + 1);
//            startActivity(
//                    PdfViewerActivity.Companion.launchPdfFromUrl(
//                            this, url,
//                            "Title", "dir", true
//                            )
//            );



        }
        //resetDisconnectTimer();
        printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        relativeHeader = findViewById(R.id.relativeHeader);

        download = findViewById(R.id.pdfDownloadImgView);
        pdf = findViewById(R.id.pdfView);
        backImageView =  findViewById(R.id.backImageView);
        headermanager = new HeaderManager(PDFViewActivity.this, title);
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

//                mWebView.setDownloadListener(new DownloadListener() {
//
//                    @Override
//                    public void onDownloadStart(String url, String userAgent,
//                                                String contentDisposition, String mimetype,
//                                                long contentLength) {
//                        DownloadManager.Request request = new DownloadManager.Request(
//                                Uri.parse(url));
//
//                        request.allowScanningByMediaScanner();//http://mobicare2.mobatia.com/nais/media/images/NASDubai_logo_blk.pdf
//                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
//                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, AppUtils.replacePdf(mLoadUrl).trim());
//                        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                        dm.enqueue(request);
//                        Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
//                                Toast.LENGTH_LONG).show();
//
//                    }
//                });

            }
        });
      /*  share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopDisconnectTimer();
                if (AppUtils.isNetworkConnected(mContext)) {
                    Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                    File fileWithinMyDir = new File(getFilepath("document.pdf"));
                    if(fileWithinMyDir.exists()) {
                        intentShareFile.setType("application/pdf");
                        intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+getFilepath("document.pdf")));
                        intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "Sharing File...");
                        intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
                        startActivity(Intent.createChooser(intentShareFile, "Share File"));
                    }
                } else {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
                }
            }
        });*/


        PermissionListener permissionListenerGallery = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if (AppUtils.isNetworkConnected(mContext)) {
                    new loadPDF().execute();
                } else {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
                }

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(mContext, "Permission Denied\n", Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(mContext)
                .setPermissionListener(permissionListenerGallery)
                .setDeniedMessage("If you reject permission,you cannot use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

        //
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

   /* public void resetDisconnectTimer() {
        HomeActivity.disconnectHandler.removeCallbacks(HomeActivity.disconnectCallback);
        HomeActivity.disconnectHandler.postDelayed(HomeActivity.disconnectCallback, HomeActivity.DISCONNECT_TIMEOUT);
    }*/

   /* public void stopDisconnectTimer() {
        HomeActivity.disconnectHandler.removeCallbacks(HomeActivity.disconnectCallback);
    }*/

    /*@Override
    public void onUserInteraction() {
        resetDisconnectTimer();
    }*/

    /*@Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }*/

    private String getFilepath(String filename) {

        return new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Download/" + filename).getPath();

    }

    public class loadPDF extends AsyncTask<String, Void, Void> {

        private Exception exception;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(PDFViewActivity.this);
            dialog.setMessage(getResources().getString(R.string.pleasewait));//Please wait...
            dialog.show();
        }

        protected Void doInBackground(String... urls) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

//                Log.e("d2",downloadFileName);
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/"+downloadFileName);
                Log.e("existingFile",file.getPath());
                if(!file.exists()) {

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadURL + ""));
                    request.setTitle(downloadFileName);
                    request.setMimeType("application/pdf");
                    request.allowScanningByMediaScanner();
                    request.setAllowedOverMetered(true);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, "/" + downloadFileName);
//                request.setDestinationInExternalFilesDir(PDFViewActivity.this,String.valueOf(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)),downloadFileName);
                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }else{
                    Log.e("Downloaded","downloaded");
                }
            }else {
                URL u = null;
            try {
                String fileName = "document.pdf";
                u = new URL(url);
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
            }

            return null;


//            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + downloadFileName);
//                Log.e("Path", String.valueOf(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)));
//                Uri uri = FileProvider.getUriForFile(PDFViewActivity.this,getLocalClassName() + ".provider", file);
                Log.e("filepath",file.getPath());
                if (file.exists()){
                    pdf.fromFile(file).defaultPage(0).enableSwipe(true).load();
                }

            }else{
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "document.pdf");
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "document.pdf");
                // System.out.println("file.exists() = " + file.exists());
                // pdf.fromUri(uri);

                pdf.fromFile(file).defaultPage(0).enableSwipe(true).load();
            }
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "document.pdf");
//            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + downloadFileName);
//            Uri uri = FileProvider.getUriForFile(PDFViewActivity.this,getLocalClassName() + ".provider", file);
//            Log.e("URI", String.valueOf(uri));
//            File file = new File( + "/download/" + "document.pdf");

//            File file =new File(getexternalfilesdir);
//            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "document.pdf");
            // System.out.println("file.exists() = " + file.exists());
//             pdf.fromUri(uri);

//            pdf.fromFile(file).defaultPage(1).enableSwipe(true).load();
//            pdf.fromUri(uri).load();

            //web.loadUrl(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "test.pdf");
            // Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_LONG).show();
        }

    }

    public class DownloadPDF extends AsyncTask<String, Void, Void> {

        String filename = name.replace(" ", "_");
        String fileName = filename + ".pdf";
        private Exception exception;
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(PDFViewActivity.this);
            dialog.setMessage(getResources().getString(R.string.pleasewait));//Please wait...
            dialog.show();
        }

        protected Void doInBackground(String... urls) {
            URL u = null;
            try {

                u = new URL(url);
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

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + fileName);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + fileName);
            System.out.println("file.exists() = " + file.exists());
            if (file.exists()) {
                Toast.makeText(mContext, "File Downloaded to Downloads folder", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Something Went Wrong. Download failed", Toast.LENGTH_SHORT).show();
            }
            // pdf.fromUri(uri);

            // pdf.fromFile(file).defaultPage(1).enableSwipe(true).load();

            //web.loadUrl(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/" + "test.pdf");
            // Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_LONG).show();
        }


    }

}

