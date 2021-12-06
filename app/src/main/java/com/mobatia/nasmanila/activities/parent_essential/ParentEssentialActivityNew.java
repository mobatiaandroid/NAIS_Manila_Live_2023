package com.mobatia.nasmanila.activities.parent_essential;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.activities.parent_essential.adapter.ParentEssentialActivityListAdapterNew;
import com.mobatia.nasmanila.activities.pdf.PDFViewActivity;
import com.mobatia.nasmanila.activities.pdf.PdfViewActivityNew;
import com.mobatia.nasmanila.activities.web_view.LoadUrlWebViewActivity;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.ResultConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.parent_essentials.model.ParentEssentialsModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.ItemOffsetDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gayatri on 23/3/17.
 */
public class ParentEssentialActivityNew extends Activity
        implements JSONConstants,URLConstants,ResultConstants,StatusConstants,NaisClassNameConstants {
    Bundle extras;
    ArrayList<ParentEssentialsModel> list;
    String tab_type;
    String tab_typeName;
    Context mContext=this;
    RecyclerView mNewsLetterListView;
    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    ImageView back;
    ImageView home;
    ImageView mailImageView;
    TextView descriptionTV;
    TextView descriptionTitle;
    ImageView bannerImagePager;
//    ViewPager bannerImagePager;
    ArrayList<String> bannerUrlImageArray;
    String bannerImage="";
    String contactEmail="";
    String description="";
    EditText text_content;
    EditText text_dialog;
    URL downloadURL;
    String downloadFileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_parent_essential_sublist_new);
        initUI();
        setListAdapter();
       /* Toast.makeText(mContext, "aa", Toast.LENGTH_SHORT).show();*/

    }

    private void setListAdapter() {
        ParentEssentialActivityListAdapterNew newsDetailListAdapter=new ParentEssentialActivityListAdapterNew(mContext,list);
        mNewsLetterListView.setAdapter(newsDetailListAdapter);
    }

    private void initUI() {
        extras=getIntent().getExtras();
        if(extras!=null) {
            list = (ArrayList<ParentEssentialsModel>) extras.getSerializable("submenuArray");
            tab_type=extras.getString("tab_type");
            bannerImage=extras.getString("bannerImage");
            contactEmail=extras.getString("contactEmail");
            description=extras.getString("description");
            tab_typeName=extras.getString("tab_typeName");
        }
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        descriptionTV = (TextView) findViewById(R.id.descriptionTV);
        descriptionTitle = (TextView) findViewById(R.id.descriptionTitle);
        mailImageView = (ImageView) findViewById(R.id.mailImageView);
        bannerImagePager= (ImageView) findViewById(R.id.bannerImagePager);
//        bannerImagePager= (ViewPager) findViewById(R.id.bannerImagePager);
        mNewsLetterListView = (RecyclerView) findViewById(R.id.mListView);
        headermanager = new HeaderManager(ParentEssentialActivityNew.this, tab_type);
        headermanager.getHeader(relativeHeader, 0);
        back = headermanager.getLeftButton();
        headermanager.setButtonLeftSelector(R.drawable.back,
                R.drawable.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.hideKeyBoard(mContext);
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
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        mNewsLetterListView.setLayoutManager(llm);

        if (!bannerImage.equalsIgnoreCase("")) {
//            bannerUrlImageArray = new ArrayList<>();
//            bannerUrlImageArray.add(bannerImage);
//            bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray,mContext));
            Glide.with(mContext).load(AppUtils.replace(bannerImage)).centerCrop().placeholder(R.drawable.default_bannerr).error(R.drawable.default_bannerr).into(bannerImagePager);

        }
        else
        {
            bannerImagePager.setBackgroundResource(R.drawable.default_bannerr);
//            bannerImagePager.setBackgroundResource(R.drawable.demo);

        }
        if (!description.equalsIgnoreCase(""))
        {
            descriptionTV.setVisibility(View.VISIBLE);
            descriptionTV.setText(description);
            descriptionTitle.setVisibility(View.GONE);
//			descriptionTitle.setVisibility(View.VISIBLE);
        }
        else
        {
            descriptionTV.setVisibility(View.GONE);
            descriptionTitle.setVisibility(View.GONE);

        }
        if (!contactEmail.equalsIgnoreCase("") && !PreferenceManager.getUserId(mContext).equals(""))
        {
            mailImageView.setVisibility(View.VISIBLE);
        }
        else
        {
            mailImageView.setVisibility(View.INVISIBLE);

        }
        mailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_send_email_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button dialogCancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                Button submitButton= (Button) dialog.findViewById(R.id.submitButton);
                text_dialog= (EditText) dialog.findViewById(R.id.text_dialog);
                text_content= (EditText) dialog.findViewById(R.id.text_content);


                dialogCancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View v) {

                        dialog.dismiss();

                    }

                });

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("submit btn clicked");
//                        sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);
                        if (text_dialog.getText().toString().equals("")) {
                            AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), "Please enter subject", R.drawable.exclamationicon, R.drawable.round);

                        } else if (text_content.getText().toString().equals("")) {
                            AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), "Please enter content", R.drawable.exclamationicon, R.drawable.round);

                        } else {
                            if (AppUtils.isNetworkConnected(mContext)) {
                                sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);

                            } else {
                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", mContext.getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                            }
                        }
                    }
                });


                dialog.show();




            }
        });

        mNewsLetterListView.setHasFixedSize(true);
//        mNewsLetterListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
        GridLayoutManager recyclerViewLayout= new GridLayoutManager(mContext, 4);
        int spacing = 5; // 50px
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext,spacing);
        mNewsLetterListView.addItemDecoration(itemDecoration);
        mNewsLetterListView.setLayoutManager(recyclerViewLayout);
        mNewsLetterListView.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), mNewsLetterListView,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        if (list.size()<=1) {
                            if (list.get(position).getFilename().endsWith(".pdf")) {
                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getFilename())));
                                }else{
                                    Intent intent = new Intent(mContext, PDFViewActivity.class);
                                    intent.putExtra("pdf_url", list.get(position).getFilename());
                                    startActivity(intent);
                                }
                            } else {
                                Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
                                intent.putExtra("url", list.get(position).getFilename());
                                intent.putExtra("tab_type", tab_type);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            if (list.get(position).getFilename().endsWith(".pdf") && tab_typeName.equalsIgnoreCase("Lunch Menu")) {

                                /*intent.putExtra("position", position);*/
//                                downloadFromUrl(list.get(position).getFilename());
                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getFilename())));
                                }else{
                                    Intent intent = new Intent(mContext, PDFViewActivity.class);
                                    intent.putExtra("pdf_url", list.get(position).getFilename());
                                    Log.e("pdf_url",list.get(position).getFilename());
                                    startActivity(intent);
                                }

                            }
                            else  if (list.get(position).getFilename().endsWith(".pdf")) {
                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getFilename())));
                                }else{
                                    Intent intent = new Intent(mContext, PDFViewActivity.class);
                                    intent.putExtra("pdf_url", list.get(position).getFilename());
                                    startActivity(intent);
                                }

                            }
                            else {
                                Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
                                intent.putExtra("url", list.get(position).getFilename());
                                intent.putExtra("tab_type", tab_type);
                                startActivity(intent);
                            }
                        }
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
    }

    private void downloadFromUrl(String url) {
        try {
            downloadURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        downloadFileName = downloadURL.getPath();
        downloadFileName = downloadFileName.substring(downloadFileName.lastIndexOf('/') + 1);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadURL+""));
        request.setTitle(downloadFileName);
        request.setMimeType("application/pdf");
        request.allowScanningByMediaScanner();
        request.setAllowedOverMetered(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS,"/"+downloadFileName);
//                request.setDestinationInExternalFilesDir(PDFViewActivity.this,String.valueOf(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)),downloadFileName);
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    private void sendEmailToStaff(String URL) {
        VolleyWrapper volleyWrapper=new VolleyWrapper(URL);
        String[] name={"access_token","email","users_id","title","message"};
        String[] value={PreferenceManager.getAccessToken(mContext),contactEmail,PreferenceManager.getUserId(mContext),text_dialog.getText().toString(),text_content.getText().toString()};//contactEmail

        //String[] value={PreferenceManager.getAccessToken(mContext),mStaffList.get(pos).getStaffEmail(),JTAG_USERS_ID_VALUE,text_dialog.getText().toString(),text_content.getText().toString()};
        volleyWrapper.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {
            @Override
            public void responseSuccess(String successResponse) {
                System.out.println("The response is" + successResponse);
                try {
                    JSONObject obj = new JSONObject(successResponse);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase("200")) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                        String status_code = secobj.getString(JTAG_STATUSCODE);
                        if (status_code.equalsIgnoreCase("303")) {
                            Toast toast = Toast.makeText(mContext,"Successfully sent email to staff", Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            Toast toast = Toast.makeText(mContext,"Email not sent", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else if (response_code.equalsIgnoreCase("500")) {
                    } else if (response_code.equalsIgnoreCase("400")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);

                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);
                            }
                        });
                        sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);

                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);

                    } else {
						/*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();*/
                        AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",mContext.getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

                    }
                } catch (Exception ex) {
                    System.out.println("The Exception in edit profile is" + ex.toString());
                }

            }

            @Override
            public void responseFailure(String failureResponse) {
				/*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
						, getResources().getString(R.string.ok));
				dialog.show();*/
                AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",mContext.getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

            }
        });


    }

}