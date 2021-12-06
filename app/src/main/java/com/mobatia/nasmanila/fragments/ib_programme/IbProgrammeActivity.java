package com.mobatia.nasmanila.fragments.ib_programme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.mobatia.nasmanila.fragments.absence.LeaveRequestSubmissionActivity;
import com.mobatia.nasmanila.fragments.primary.adapter.CustomPrimaryAdapter;
import com.mobatia.nasmanila.fragments.primary.model.PrimaryModel;
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

public class IbProgrammeActivity extends Activity implements AdapterView.OnItemClickListener,
        NaisTabConstants,CacheDIRConstants, URLConstants,JSONConstants,StatusConstants,
        IntentPassValueConstants,NaisClassNameConstants {
    TextView mTitleTextView;
    RelativeLayout relativeHeader;

    HeaderManager headermanager;
    ImageView back;
    ImageView home;
    private Context mContext;
    private ListView mListView;
    private String mTitle;
    private String mTabId;
    private RelativeLayout mProgressDialog;
    private ArrayList<PrimaryModel> mListViewArray;
    private ArrayList<PrimaryModel> mNewsModelArrayList;
    //	ViewPager bannerImagePager;
    ImageView bannerImagePager;
    ArrayList<String> bannerUrlImageArray;
    RelativeLayout relMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ibprogramme_list);
        mContext = this;
        initialiseUI();
    }
    private void initialiseUI() {
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        headermanager = new HeaderManager(IbProgrammeActivity.this, IB_PROGRAMME);
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
        mListView = (ListView) findViewById(R.id.mListView);
        bannerImagePager= (ImageView) findViewById(R.id.bannerImagePager);
//		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImagePager);
        mListView.setOnItemClickListener(this);
        if (AppUtils.checkInternet(mContext)) {

            getList();
        }
        else
        {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }
//		mProgressDialog = (RelativeLayout) mRootView
//				.findViewById(R.id.progressDialog);
//		mBannerImage = (ImageView) mRootView.findViewById(R.id.bannerImage);
//		mAboutUsList.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0)
        {
            if (mListViewArray.get(position).getmName().equalsIgnoreCase("News"))
            {
					/*if (mListViewArray.get(position).getmPrimaryModel().size()==1)
					{
						if (mListViewArray.get(position).getmPrimaryModel().get(0).getmFile().endsWith(".pdf")) {
							Intent intent = new Intent(mContext, PdfReaderActivity.class);
							intent.putExtra("pdf_url", mListViewArray.get(position).getmPrimaryModel().get(0).getmFile());
							startActivity(intent);
						}
						else
						{
							Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
							intent.putExtra("url",mListViewArray.get(position).getmPrimaryModel().get(0).getmFile());
							intent.putExtra("tab_type",IB_PROGRAMME);
							mContext.startActivity(intent);
						}
					}
					else*/ if (mListViewArray.get(position).getmPrimaryModel().size()>0)
            {
                Intent intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra("pdf_urls", mListViewArray.get(position).getmPrimaryModel());
                intent.putExtra("tab_type", IB_PROGRAMME);
                startActivity(intent);
            }
            }	else if (mListViewArray.get(position).getmName().equalsIgnoreCase("Assessment Link"))
            {
//					if (!PreferenceManager.getUserId(mContext).equals("")) {
//
//						Intent mIntent = new Intent(mContext, AssessmentLinkActivity.class);
//						mIntent.putExtra("tab_type",IB_PROGRAMME);
//
//						startActivity(mIntent);
//					}
//					else {
//						AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.avail_for_registered),R.drawable.exclamationicon,R.drawable.round);
//
//					}
                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.module_under_construction), R.drawable.exclamationicon, R.drawable.round);

            }
            else {
					/*if (mListViewArray.get(position).getmFile().endsWith(".pdf")) {
						Intent intent = new Intent(mContext, PdfReaderActivity.class);
						intent.putExtra("pdf_url", mListViewArray.get(position).getmFile());
						startActivity(intent);
					} else {
						Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
						intent.putExtra("url", mListViewArray.get(position).getmFile());
						intent.putExtra("tab_type", mListViewArray.get(position).getmName());
						mContext.startActivity(intent);
					}*/

                Intent intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra("pdf_urls", mListViewArray.get(position).getmPrimaryModel());
                intent.putExtra("tab_type", IB_PROGRAMME);
                startActivity(intent);
            }
        }
        else {

            Intent intent = new Intent(mContext, com.mobatia.nasmanila.activities.coming_up.ib_programme.IbProgrammeActivity.class);
            mContext.startActivity(intent);
        }
//		}
//		Fragment fragment = new BrowserWebContents(mAboutUsListArray.get(
//				position).getCategoryName(), TAB_ABOUT);
//		Bundle bundle = new Bundle();
//		bundle.putSerializable(URL_LINK, mAboutUsListArray.get(position)
//				.getCategoryUrl());
//		bundle.putSerializable(MESSAGE, mAboutUsListArray.get(position)
//				.getCategoryName());
//		bundle.putInt(POSITION, position);
//		fragment.setArguments(bundle);
//		if (fragment != null) {
//			FragmentManager fragmentManager = getActivity()
//					.getSupportFragmentManager();
//			fragmentManager.beginTransaction()
//					.add(R.id.frame_container, fragment, mTitle)
//					.addToBackStack(mTitle).commit();
//		}
    }
    public void getList() {

        try {
            mListViewArray = new ArrayList<>();
            //mNewsModelArrayList=new ArrayList<>();

            final VolleyWrapper manager = new VolleyWrapper(URL_IB_PROGRAMMME_LIST);
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
//											bannerImagePager.setBackgroundResource(R.drawable.ibprogrammebanner);

                                        }
                                        JSONArray dataArray = respObject.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);
                                        if (dataArray.length() > 0) {
                                            for (int i = 0; i <= dataArray.length(); i++) {
                                                if (i!=0) {
                                                    JSONObject dataObject = dataArray.getJSONObject(i-1);
                                                    mListViewArray.add(getSearchValues(dataObject));
                                                }
                                                else
                                                {
                                                    PrimaryModel mPrimaryModel = new PrimaryModel();
                                                    mPrimaryModel.setmId("0");
                                                    mPrimaryModel.setmFile("");
                                                    mPrimaryModel.setmName("Coming Up");
                                                    mListViewArray.add(mPrimaryModel);
                                                }
                                            }

                                            mListView.setAdapter(new CustomPrimaryAdapter(mContext, mListViewArray));

                                        } else {
                                            //CustomStatusDialog();
                                            Toast.makeText(mContext,"Failure",Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
//										CustomStatusDialog(RESPONSE_FAILURE);
                                        Toast.makeText(mContext,"Failure",Toast.LENGTH_SHORT).show();
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

                                }
                            } else if (responsCode.equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                                Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();

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
    private PrimaryModel getSearchValues(JSONObject Object)
            throws JSONException {
        PrimaryModel mPrimaryModel = new PrimaryModel();
        mPrimaryModel.setmId(Object.getString(JTAG_ID));
//		mPrimaryModel.setmFile(Object.getString(JTAG_TAB_FILE));
        mPrimaryModel.setmName(Object.getString(JTAG_TAB_NAME));

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
            mPrimaryModel.setmPrimaryModel(mNewsModelArrayList);

        }
        else
        {
            mPrimaryModel.setmFile(Object.getString(JTAG_TAB_FILE));
        }
        return mPrimaryModel;
    }

}
