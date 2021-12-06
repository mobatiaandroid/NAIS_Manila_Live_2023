package com.mobatia.nasmanila.fragments.category2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.cca.CcaActivity;
import com.mobatia.nasmanila.fragments.communications.adapter.CommunicationsAdapter;
import com.mobatia.nasmanila.fragments.communications.model.CommunicationModel;
import com.mobatia.nasmanila.fragments.performing_arts.PerformingArtsActivity;
import com.mobatia.nasmanila.fragments.sports.SportsMainActivity;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by krishnaraj on 07/11/18.
 */

public class CategoryFragment extends Fragment implements AdapterView.OnItemClickListener,
        NaisTabConstants,CacheDIRConstants, URLConstants,
        IntentPassValueConstants,NaisClassNameConstants,JSONConstants,StatusConstants {

    private View mRootView;
    private Context mContext;
    private String mTitle;
    private String mTabId;
    private RelativeLayout relMain;
    private ImageView mBannerImage;
    private ListView mListView;
    TextView mTitleTextView;
    boolean isRegUser;

    private ArrayList<CommunicationModel> mListViewArray;
    ImageView bannerImagePager;
    //	ViewPager bannerImagePager;
    ArrayList<String> bannerUrlImageArray;
    ArrayList<String> dataArrayStrings=new ArrayList<String>(){
        {
            add("EL's");
            //add("Newsletters");
            add("Sports");
            add("Performing Arts");
        }};
    ArrayList<String> dataArrayStringsGuest=new ArrayList<String>(){
        {
            add("EL's");
            //add("Newsletters");
            add("Performing Arts");
        }};
    //			add("Parents' Association");
    //add("Chatter Box Café");

    public CategoryFragment() {

    }

    public CategoryFragment
            (String title, String tabId) {
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
        mRootView = inflater.inflate(R.layout.fragment_communications_list, container,
                false);
//		setHasOptionsMenu(true);
        mContext = getActivity();
//		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        initialiseUI();

        return mRootView;
    }

    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Jan 12, 2015 Author : Vandana
     * Surendranath
     *****************************************************/
    private void initialiseUI() {
        mTitleTextView= (TextView) mRootView.findViewById(R.id.titleTextView);
        mTitleTextView.setText(CATEGORY2);
        mListView = (ListView) mRootView.findViewById(R.id.mListView);
        bannerImagePager= (ImageView) mRootView.findViewById(R.id.bannerImagePager);
//		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImagePager);
        relMain = (RelativeLayout) mRootView.findViewById(R.id.relMain);
        relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mListViewArray = new ArrayList<>();
        if(PreferenceManager.getUserId(mContext).equals(""))
        {
            isRegUser = false;
            for (int i = 0; i < dataArrayStringsGuest.size(); i++) {
                CommunicationModel mCommunicationModel = new CommunicationModel();
                mCommunicationModel.setmId(String.valueOf(i));
                mCommunicationModel.setmFileName("");
                mCommunicationModel.setmTitle(dataArrayStringsGuest.get(i));
                if (!PreferenceManager.getUserId(mContext).equalsIgnoreCase("")) {
                    mListViewArray.add(mCommunicationModel);
                } else {
                    if (i != 4) {
                        mListViewArray.add(mCommunicationModel);

                    }
                }
            }


            mListView.setAdapter(new CommunicationsAdapter(getActivity(), mListViewArray));
        }
        else {

            isRegUser = true;
            for (int i = 0; i < dataArrayStrings.size(); i++) {
                CommunicationModel mCommunicationModel = new CommunicationModel();
                mCommunicationModel.setmId(String.valueOf(i));
                mCommunicationModel.setmFileName("");
                mCommunicationModel.setmTitle(dataArrayStrings.get(i));
                if (!PreferenceManager.getUserId(mContext).equalsIgnoreCase("")) {
                    mListViewArray.add(mCommunicationModel);
                } else {
                    if (i != 4) {
                        mListViewArray.add(mCommunicationModel);

                    }
                }
            }


            mListView.setAdapter(new CommunicationsAdapter(getActivity(), mListViewArray));
        }
        if (AppUtils.checkInternet(mContext)) {

            getBanner();
        }
        else
        {
            //Toast.makeText(mContext, "Network Error", Toast.LENGTH_SHORT).show();
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }
//		mBannerImage = (ImageView) mRootView.findViewById(R.id.bannerImage);
        mListView.setOnItemClickListener(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
     * .AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (isRegUser) {


            if (mListViewArray.get(position).getmId().equalsIgnoreCase("0")) {
                Intent intent = new Intent(mContext, CcaActivity.class);
                intent.putExtra("tab_type", "EL's");
                mContext.startActivity(intent);
            } else if (mListViewArray.get(position).getmId().equalsIgnoreCase("1")) {
                Intent intent = new Intent(mContext, SportsMainActivity.class);
                intent.putExtra("tab_type", "Sports");
                mContext.startActivity(intent);
            } else if (mListViewArray.get(position).getmId().equalsIgnoreCase("2")) {
                Intent intent = new Intent(mContext, PerformingArtsActivity.class);
                intent.putExtra("tab_type", "Performing Arts");
                mContext.startActivity(intent);
            }

        }
        else {
            if (mListViewArray.get(position).getmId().equalsIgnoreCase("0")) {
                Intent intent = new Intent(mContext, CcaActivity.class);
                intent.putExtra("tab_type", "EL's");
                mContext.startActivity(intent);
            }else if (mListViewArray.get(position).getmId().equalsIgnoreCase("1")) {
                Intent intent = new Intent(mContext, PerformingArtsActivity.class);
                intent.putExtra("tab_type", "Performing Arts");
                mContext.startActivity(intent);
            }

        }
    }
    public void getBanner() {

        try {

            final VolleyWrapper manager = new VolleyWrapper(URL_COMMUNICATION_BANNER);
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
//											bannerImagePager.setBackgroundResource(R.drawable.communications_banner);
                                            bannerImagePager.setBackgroundResource(R.drawable.default_bannerr);

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
                                    AppUtils.postInitParam(getActivity(), new AppUtils.GetAccessTokenInterface() {
                                        @Override
                                        public void getAccessToken() {
                                        }
                                    });
                                    getBanner();

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
                    // CustomStatusDialog(RESPONSE_FAILURE);
                    AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
