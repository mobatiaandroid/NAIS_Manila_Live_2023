package com.mobatia.nasmanila.fragments.nae_programmes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.activities.news.NewsActivity;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.primary.model.PrimaryModel;
import com.mobatia.nasmanila.fragments.secondary.adapter.CustomSecondaryAdapter;
import com.mobatia.nasmanila.fragments.secondary.model.SecondaryModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by krishnaraj on 08/11/18.
 */

public class NaeProgrammesActivity extends Activity implements AdapterView.OnItemClickListener,
        NaisTabConstants,CacheDIRConstants, URLConstants,
        IntentPassValueConstants,NaisClassNameConstants,JSONConstants,StatusConstants {
    RelativeLayout relativeHeader;

    HeaderManager headermanager;
    ImageView back;
    ImageView home;
    private Context mContext;
    private ListView mListView;
    private String mTitle;
    private String mTabId;
    private RelativeLayout relMain;
    private ArrayList<SecondaryModel> mListViewArray;
    ImageView bannerImagePager;
    //	ViewPager bannerImagePager;
    ArrayList<String> bannerUrlImageArray;
    private ArrayList<PrimaryModel> mNewsModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_naeprogrammes_list);
        mContext = this;
        initialiseUI();
    }
    private void initialiseUI() {
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        headermanager = new HeaderManager(NaeProgrammesActivity.this, NAE_PROGRAMMES);
        headermanager.getHeader(relativeHeader, 0);
        back = headermanager.getLeftButton();
        home = headermanager.getLogoButton();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, HomeListAppCompatActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
        });
        headermanager.setButtonLeftSelector(R.drawable.back,
                R.drawable.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        mListView = (ListView)findViewById(R.id.mListView);
//		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImagePager);
        bannerImagePager= (ImageView)findViewById(R.id.bannerImagePager);
        relMain = (RelativeLayout)findViewById(R.id.relMain);
        relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mListView.setOnItemClickListener(this);
        if (AppUtils.checkInternet(mContext)) {

            getList();
        }
        else
        {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(mContext, NewsActivity.class);
        intent.putExtra("pdf_urls", mListViewArray.get(position).getmPrimaryModel());
        intent.putExtra("tab_type", NAE_PROGRAMMES);
        startActivity(intent);
    }

    public void getList() {

        try {
            mListViewArray = new ArrayList<>();
            final VolleyWrapper manager = new VolleyWrapper(URL_NAE_PROGRAMMES_LIST);
            String[] name = new String[]{JTAG_ACCESSTOKEN};
            String[] value = new String[]{PreferenceManager.getAccessToken(mContext)};


            manager.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {

                @Override
                public void responseSuccess(String successResponse) {
                    String responsCode = "";
                    if (successResponse != null) {
                        try {
                            JSONObject rootObject = new JSONObject(successResponse);
                            if (rootObject.optString(JTAG_RESPONSE) != null) {
                                responsCode = rootObject.optString(JTAG_RESPONSECODE);
                                if (responsCode.equals(RESPONSE_SUCCESS)) {
                                    JSONObject respObject = rootObject.getJSONObject(JTAG_RESPONSE);
                                    String statusCode = respObject.optString(JTAG_STATUSCODE);
                                    if (statusCode.equals(STATUS_SUCCESS)) {
                                        String bannerImage=respObject.optString(JTAG_BANNER_IMAGE);
                                        if (!bannerImage.equalsIgnoreCase("")) {
                                            Glide.with(mContext).load(AppUtils.replace(bannerImage)).centerCrop().into(bannerImagePager);

//											bannerUrlImageArray = new ArrayList<>();
//											bannerUrlImageArray.add(bannerImage);
//											bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, getActivity()));
                                        }
                                        else
                                        {
                                            bannerImagePager.setBackgroundResource(R.drawable.default_bannerr);
//											bannerImagePager.setBackgroundResource(R.drawable.nae_programmes_banner);

                                        }
                                        JSONArray dataArray = respObject.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);
                                        if (dataArray.length() > 0) {
                                            for (int i = 0; i <dataArray.length(); i++) {
                                                JSONObject dataObject = dataArray.getJSONObject(i);
                                                mListViewArray.add(getSearchValues(dataObject));

                                            }

                                            mListView.setAdapter(new CustomSecondaryAdapter(mContext, mListViewArray));

                                        } else {
                                            //CustomStatusDialog();
                                            //Toast.makeText(mContext,"Failure",Toast.LENGTH_SHORT).show();
                                            AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.nodatafound),R.drawable.exclamationicon,R.drawable.round);

                                        }
                                    } else {
//										CustomStatusDialog(RESPONSE_FAILURE);
                                        //Toast.makeText(mContext,"Failure",Toast.LENGTH_SHORT).show();
                                        AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

                                    }
                                }
                                else if (responsCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                                        responsCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                        responsCode.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)) {
                                    AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                        @Override
                                        public void getAccessToken() {
                                        }
                                    });
                                    getList();

                                } else if (responsCode.equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                                    //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                                    AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

                                }
                            } else {
//								CustomStatusDialog(RESPONSE_FAILURE);
                                //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                                AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void responseFailure(String failureResponse) {
                    AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private SecondaryModel getSearchValues(JSONObject Object)
            throws JSONException {
        SecondaryModel mSecondaryModel = new SecondaryModel();
        mSecondaryModel.setmId(Object.getString(JTAG_ID));
//		mSecondaryModel.setmFile(Object.getString(JTAG_TAB_FILE));
        mSecondaryModel.setmName(Object.getString(JTAG_TAB_NAME));
        if (Object.get(JTAG_TAB_FILE) instanceof JSONArray)
        {
            JSONArray fileArray = Object.getJSONArray(JTAG_TAB_FILE);
            mNewsModelArrayList=new ArrayList<>();
            for (int i=0;i<fileArray.length();i++)
            {
                JSONObject newsObject=fileArray.optJSONObject(i);
                PrimaryModel mNewsModel=new PrimaryModel();
                mNewsModel.setmId(newsObject.optString(JTAG_ID));
                mNewsModel.setmFile(newsObject.optString(JTAG_TAB_FILE));
                mNewsModel.setmTitle(newsObject.optString(JTAG_TITLE));
                mNewsModelArrayList.add(mNewsModel);
            }
            mSecondaryModel.setmPrimaryModel(mNewsModelArrayList);

        }
        else
        {
            mSecondaryModel.setmFile(Object.getString(JTAG_TAB_FILE));
        }
        return mSecondaryModel;
    }


}
