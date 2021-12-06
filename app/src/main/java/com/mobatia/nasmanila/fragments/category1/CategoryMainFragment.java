package com.mobatia.nasmanila.fragments.category1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.web_view.LoadUrlWebViewActivity;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.ResultConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.cca.CcaActivity;
import com.mobatia.nasmanila.fragments.parent_essentials.adapter.ParentEssentialsListAdapter;
import com.mobatia.nasmanila.fragments.parent_essentials.model.ParentEssentialsModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by krishnaraj on 07/11/18.
 */

public class CategoryMainFragment extends Fragment implements
        NaisTabConstants,CacheDIRConstants, URLConstants,
        IntentPassValueConstants,NaisClassNameConstants,JSONConstants,ResultConstants,StatusConstants {
    TextView mTitleTextView;

    private View mRootView;
    private Context mContext;
    private RecyclerView mListView;
    private String mTitle;
    private String mTabId;
    //	 ViewPager bannerImagePager;
    ImageView bannerImagePager;
    private ImageView mBannerImage;
    ArrayList<String> bannerUrlImageArray;
    private ArrayList<ParentEssentialsModel> newsLetterModelArrayList=new ArrayList<>();;
    RelativeLayout relMain;
    RelativeLayout mtitle;
    TextView text_content;
    TextView text_dialog;
    String description="";
    String contactEmail="";
    TextView descriptionTV;
    TextView descriptionTitle;
    ImageView sendEmail;

    public CategoryMainFragment() {

    }

    public CategoryMainFragment(String title, String tabId) {
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
        mRootView = inflater.inflate(R.layout.fragment_parent_essentials_list, container,
                false);
//		setHasOptionsMenu(true);
        mContext = getActivity();
//		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        initialiseUI();
//		GetAboutUsListAsyncTask aboutUsTask = new GetAboutUsListAsyncTask(
//				URL_ABOUTUS_LIST, ABOUT_US_DIR, 1, mTabId);
//		aboutUsTask.execute();
        return mRootView;
    }

    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Jan 12, 2015 Author : Vandana
     * Surendranath
     *****************************************************/
    private void initialiseUI() {
        mtitle = (RelativeLayout) mRootView.findViewById(R.id.title);
        descriptionTV= (TextView) mRootView.findViewById(R.id.descriptionTV);
        descriptionTitle= (TextView) mRootView.findViewById(R.id.descriptionTitle);
        sendEmail= (ImageView) mRootView.findViewById(R.id.sendEmail);
        mTitleTextView= (TextView) mRootView.findViewById(R.id.titleTextView);
        mTitleTextView.setText("Enrichment Lessons");
        mListView = (RecyclerView) mRootView.findViewById(R.id.mListView);
        mListView.setNestedScrollingEnabled(false);
        bannerImagePager= (ImageView) mRootView.findViewById(R.id.bannerImagePager);
//		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImagePager);
        relMain = (RelativeLayout) mRootView.findViewById(R.id.relMain);
        relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));

        mListView.setLayoutManager(llm);
        if(AppUtils.isNetworkConnected(mContext)){
            getNewslettercategory();
        }else{
            AppUtils.showDialogAlertDismiss((Activity)mContext,"Network Error",getString(R.string.no_internet),R.drawable.nonetworkicon,R.drawable.roundred);

        }
        mListView.addOnItemTouchListener(new RecyclerItemListener(mContext, mListView,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
//						Intent intent = new Intent(mContext, ParentEssentialActivity.class);
                        if (newsLetterModelArrayList.get(position).getTitle().equalsIgnoreCase("Enrichment Lessons")||newsLetterModelArrayList.get(position).getTitle().equalsIgnoreCase("Enrichment Lesson")) {
                            Intent intent = new Intent(mContext, CcaActivity.class);
                            intent.putExtra("tab_type", "Enrichment Lessons");
                            mContext.startActivity(intent);
                        }
                        else if(newsLetterModelArrayList.get(position).getTitle().equalsIgnoreCase("Edmodo"))
                        {
                            Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
                            intent.putExtra("url", "https://www.edmodo.com/?go2url=%2Fhome");
                            intent.putExtra("tab_type", "Edmodo");
                            startActivity(intent);
                        }

                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!PreferenceManager.getUserId(mContext).equalsIgnoreCase("")) {

                    final Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.alert_send_email_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Button dialogCancelButton = (Button) dialog.findViewById(R.id.cancelButton);
                    Button submitButton = (Button) dialog.findViewById(R.id.submitButton);
                    text_dialog = (EditText) dialog.findViewById(R.id.text_dialog);
                    text_content = (EditText) dialog.findViewById(R.id.text_content);
                    text_dialog.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                text_dialog.setHint("");
                                text_dialog.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
                                text_dialog.setPadding(5, 5, 0, 0);
                            } else {
                                text_dialog.setHint("Enter your subject here...");

                                text_dialog.setGravity(Gravity.CENTER);

                            }
                        }
                    });
                    text_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                text_content.setGravity(Gravity.LEFT);
                            } else {
                                text_content.setGravity(Gravity.CENTER);

                            }
                        }
                    });

                    dialogCancelButton.setOnClickListener(new View.OnClickListener() {

                        @Override

                        public void onClick(View v) {

                            dialog.dismiss();

                        }

                    });

                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//						sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);
                            if (text_dialog.getText().equals("")) {
                                AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), "Please enter  subject", R.drawable.exclamationicon, R.drawable.round);

                            } else if (text_content.getText().toString().equals("")) {
                                AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), "Please enter  content", R.drawable.exclamationicon, R.drawable.round);

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


                }else {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), "This feature is available only for registered users. Login/register to see contents.", R.drawable.exclamationicon, R.drawable.round);

                }


            }
        });

    }

    private void getNewslettercategory() {

        VolleyWrapper volleyWrapper=new VolleyWrapper(URL_GET_ENRICHMENT_LESSONS);
        String[] name={JTAG_ACCESSTOKEN};
        String[] value={PreferenceManager.getAccessToken(mContext)};
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
                            String bannerImage=secobj.optString(JTAG_BANNER_IMAGE);
                            description = secobj.optString(JTAG_DESCRIPTION);
                            contactEmail = secobj.optString(JTAG_CONTACT_EMAIL);
                            if (!bannerImage.equalsIgnoreCase("")) {
                                Glide.with(mContext).load(AppUtils.replace(bannerImage)).centerCrop().placeholder(R.drawable.default_bannerr).error(R.drawable.default_bannerr).into(bannerImagePager);

//								bannerUrlImageArray = new ArrayList<>();
//								bannerUrlImageArray.add(bannerImage);
//								bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray,mContext));
                            }
                            else
                            {
                                bannerImagePager.setImageResource(R.drawable.default_bannerr);
//								bannerImagePager.setBackgroundResource(R.drawable.aboutbanner);

                            }
                            if (description.equalsIgnoreCase("")&&contactEmail.equalsIgnoreCase(""))
                            {
                                mtitle.setVisibility(View.GONE);
                            }
                            else
                            {
                                mtitle.setVisibility(View.VISIBLE);
                            }
                            if (description.equalsIgnoreCase(""))
                            {
                                descriptionTV.setVisibility(View.GONE);
                                descriptionTitle.setVisibility(View.GONE);
                            }else
                            {
                                descriptionTV.setText(description);
                                descriptionTitle.setVisibility(View.GONE);
//			descriptionTitle.setVisibility(View.VISIBLE);
                                descriptionTV.setVisibility(View.VISIBLE);
                                mtitle.setVisibility(View.VISIBLE);
                            }
                            if (contactEmail.equalsIgnoreCase(""))
                            {
                                sendEmail.setVisibility(View.GONE);
                            }else
                            {
                                sendEmail.setVisibility(View.VISIBLE);
                                mtitle.setVisibility(View.VISIBLE);
                            }
                            JSONArray responses = secobj.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);

                            if (responses.length() > 0) {
                                for (int i = 0; i < responses.length(); i++) {
                                    JSONObject dataObject = responses.getJSONObject(i);

                                    newsLetterModelArrayList.add(addNewsLetterDetails(dataObject));
                                }

                                ParentEssentialsListAdapter newsLetterAdapter = new ParentEssentialsListAdapter(mContext, newsLetterModelArrayList);
                                mListView.setAdapter(newsLetterAdapter);
                            } else {
                                Toast.makeText(mContext, "No data found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (response_code.equalsIgnoreCase("500")) {
                        AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

                    } else if (response_code.equalsIgnoreCase("400")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                getNewslettercategory();
                            }
                        });
                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                getNewslettercategory();
                            }
                        });
                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                getNewslettercategory();
                            }
                        });
                    } else {
						/*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();*/
                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

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
                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

            }
        });


    }
    private ParentEssentialsModel addNewsLetterDetails(JSONObject obj) {
        ParentEssentialsModel model = new ParentEssentialsModel();
        try {
            model.setTitle(obj.optString(JTAG_TAB_NAME));
            if (obj.has(JTAG_BANNER_IMAGE)) {
                model.setBannerImage(obj.optString(JTAG_BANNER_IMAGE));
            }
            else
            {
                model.setBannerImage("");

            }
            if (obj.has(JTAG_DESCRIPTION)) {
                model.setDescription(obj.optString(JTAG_DESCRIPTION));
            }
            else
            {
                model.setDescription("");

            }
            if (obj.has(JTAG_CONTACT_EMAIL)) {
                model.setContactEmail(obj.optString(JTAG_CONTACT_EMAIL));
            }
            else
            {
                model.setContactEmail("");

            }
            if(obj.has("link"))
            {
                model.setLink(obj.optString("link"));
            }
            else
            {
                model.setLink("");
            }
            JSONArray submenuArray=obj.getJSONArray(JTAG_TAB_SUBMENU_ARRAY);
            ArrayList<ParentEssentialsModel> subMenNewsLetterModels=new ArrayList<>();
            for(int i=0;i<submenuArray.length();i++) {
                JSONObject subObj=submenuArray.getJSONObject(i);
                ParentEssentialsModel newsModel=new ParentEssentialsModel();
//				newsModel.setNewLetterSubId(subObj.optString("id"));
                newsModel.setFilename(subObj.optString("filename"));
                newsModel.setSubmenu(subObj.optString("submenu"));
                subMenNewsLetterModels.add(newsModel);
            }
            model.setNewsLetterModelArrayList(subMenNewsLetterModels);
        } catch (Exception ex) {
            System.out.println("Exception is" + ex);
        }

        return model;
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
                            Toast toast = Toast.makeText(mContext, "Successfully sent email to staff", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(mContext, "Email not sent", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else if (response_code.equalsIgnoreCase("500")) {
                    } else if (response_code.equalsIgnoreCase("400")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);
                            }
                        });
                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);
                            }
                        });
                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);
                            }
                        });
                    } else {
						/*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();*/
                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", mContext.getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

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
                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", mContext.getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

            }
        });


    }

}

/* if (isRegUser) {
            *//*if (mListViewArray.get(position).getmId().equalsIgnoreCase("0")) {
                Intent intent = new Intent(mContext, IbProgrammeActivity.class);
                intent.putExtra("tab_type","IGCSE Programmes");
                mContext.startActivity(intent);
            }
            else if(mListViewArray.get(position).getmId().equalsIgnoreCase("1")){
                Intent intent = new Intent(mContext, NaeProgrammesActivity.class);
                intent.putExtra("tab_type", "NAE Programmes");
                mContext.startActivity(intent);
            }

           else*//* if (mListViewArray.get(position).getmId().equalsIgnoreCase("0")) {
           Intent intent = new Intent(mContext, CcaActivity.class);
        intent.putExtra("tab_type", "Enrichment Lessons");
        mContext.startActivity(intent);
        } *//*else if (mListViewArray.get(position).getmId().equalsIgnoreCase("3")) {
                Intent intent = new Intent(mContext, PerformingArtsActivity.class);
                intent.putExtra("tab_type", "Performing Arts");
                mContext.startActivity(intent);
            }
            else if (mListViewArray.get(position).getmId().equalsIgnoreCase("4")) {
                Intent intent = new Intent(mContext, SportsMainActivity.class);
                intent.putExtra("tab_type", "Sports");
                mContext.startActivity(intent);
            }*//*
        else if (mListViewArray.get(position).getmId().equalsIgnoreCase("1")) {
        Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
        intent.putExtra("url", "https://www.edmodo.com/?go2url=%2Fhome");
        intent.putExtra("tab_type", "Edmodo");
        startActivity(intent);
        }

        }*/