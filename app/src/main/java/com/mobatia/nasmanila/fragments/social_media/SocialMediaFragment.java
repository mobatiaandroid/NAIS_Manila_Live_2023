/**
 *
 */
package com.mobatia.nasmanila.fragments.social_media;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.web_view.FullscreenWebViewActivityNoHeader;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.social_media.adapter.SocialMediaAdapter;
import com.mobatia.nasmanila.fragments.social_media.model.SocialMediaModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Rijo K Jose
 */

public class SocialMediaFragment extends Fragment implements OnItemClickListener,
        NaisTabConstants, CacheDIRConstants, URLConstants,
        IntentPassValueConstants, NaisClassNameConstants, JSONConstants, View.OnClickListener {
    TextView mTitleTextView;

    private View mRootView;
    private Context mContext;
    private ListView mAboutUsList;
    private String mTitle;
    private String mTabId;
    private RelativeLayout mProgressDialog;
    private ImageView mBannerImage;
    private ImageView facebook, twitter, instagram;
    String type;
    //	private CustomAboutUsAdapter mAdapter;
    private ArrayList<SocialMediaModel> mSocialMediaArraylistFacebook = new ArrayList<>();
    private ArrayList<SocialMediaModel> mSocialMediaArraylistTwitter = new ArrayList<>();
    private ArrayList<SocialMediaModel> mSocialMediaArraylistInstagram = new ArrayList<>();
    private ArrayList<SocialMediaModel> mSocialMediaArray = new ArrayList<>();
    //ViewPager bannerImagePager;
    ImageView bannerImagePager;
    ArrayList<String> bannerUrlImageArray;

    public SocialMediaFragment() {

    }

    public SocialMediaFragment(String title, String tabId) {
        this.mTitle = title;
        this.mTabId = tabId;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_social_media, container,
                false);
//		setHasOptionsMenu(true);
        mContext = getActivity();
//		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        initialiseUI();
        if (AppUtils.isNetworkConnected(mContext)) {
            callSocialMediaListAPI(URL_GET_SOCIALMEDIA_LIS);
        } else {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }
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
        mTitleTextView = (TextView) mRootView.findViewById(R.id.titleTextView);
        mTitleTextView.setText(SOCIAL_MEDIA);
        facebook = (ImageView) mRootView.findViewById(R.id.facebookButton);
        twitter = (ImageView) mRootView.findViewById(R.id.twitterButton);
        instagram = (ImageView) mRootView.findViewById(R.id.instagramButton);
        bannerImagePager = (ImageView) mRootView.findViewById(R.id.bannerImageViewPager);
//		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImageViewPager);

        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        instagram.setOnClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }


    public void showSocialmediaList(ArrayList<SocialMediaModel> mSocialMediaArray, final String type) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_social_media_list);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogDismiss = (Button) dialog.findViewById(R.id.btn_dismiss);
        ImageView iconImageView = (ImageView) dialog.findViewById(R.id.iconImageView);
        RecyclerView socialMediaList = (RecyclerView) dialog.findViewById(R.id.recycler_view_social_media);
        //if(mSocialMediaArray.get())
        if (type.equals("facebook")) {
            iconImageView.setImageResource(R.drawable.facebookiconmedia);
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                iconImageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.roundfb));
                dialogDismiss.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.buttonfb));

            } else {
                iconImageView.setBackground(mContext.getResources().getDrawable(R.drawable.roundfb));
                dialogDismiss.setBackground(mContext.getResources().getDrawable(R.drawable.buttonfb));

            }
        } else if (type.equals("twitter")) {
            iconImageView.setImageResource(R.drawable.twittericon);
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                iconImageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.roundtw));
                dialogDismiss.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.buttontwi));

            } else {
                iconImageView.setBackground(mContext.getResources().getDrawable(R.drawable.roundtw));
                dialogDismiss.setBackground(mContext.getResources().getDrawable(R.drawable.buttontwi));

            }
        } else {
            iconImageView.setImageResource(R.drawable.instagramicon);
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                iconImageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.roundins));
                dialogDismiss.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.buttonins));

            } else {
                iconImageView.setBackground(mContext.getResources().getDrawable(R.drawable.roundins));
                dialogDismiss.setBackground(mContext.getResources().getDrawable(R.drawable.buttonins));

            }
        }
        socialMediaList.addItemDecoration(new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));

        socialMediaList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        socialMediaList.setLayoutManager(llm);

        SocialMediaAdapter socialMediaAdapter = new SocialMediaAdapter(mContext, mSocialMediaArray);
        socialMediaList.setAdapter(socialMediaAdapter);
        dialogDismiss.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                dialog.dismiss();

            }

        });

        socialMediaList.addOnItemTouchListener(new RecyclerItemListener(mContext, socialMediaList,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        if (type.equals("facebook")) {
                            Intent mintent = new Intent(mContext, FullscreenWebViewActivityNoHeader.class);
                            mintent.putExtra("url", mSocialMediaArraylistFacebook.get(position).getUrl());
                            startActivity(mintent);
                        }
                        else if(type.equals("twitter")){
//						Intent i = new Intent(Intent.ACTION_VIEW);
//						i.setData(Uri.parse(mSocialMediaArraylistTwitter.get(position).getUrl()));
//						startActivity(i);
                            Intent mintent = new Intent(mContext, FullscreenWebViewActivityNoHeader.class);
                            mintent.putExtra("url",mSocialMediaArraylistTwitter.get(position).getUrl());
                            startActivity(mintent);
                        }else if (type.equals("instagram")) {
                            Intent mintent = new Intent(mContext, FullscreenWebViewActivityNoHeader.class);
                            mintent.putExtra("url", mSocialMediaArraylistInstagram.get(position).getUrl());
                            startActivity(mintent);
                        }
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
        dialog.show();
    }

    private void callSocialMediaListAPI(String URL) {
        VolleyWrapper volleyWrapper = new VolleyWrapper(URL);
        String[] name = {"access_token"};
        String[] value = {PreferenceManager.getAccessToken(mContext)};
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
                            String bannerImage = secobj.optString(JTAG_BANNER_IMAGE);
                            if (!bannerImage.equalsIgnoreCase("")) {
                                Glide.with(mContext).load(AppUtils.replace(bannerImage)).centerCrop().into(bannerImagePager);

                            } else {
                                bannerImagePager.setBackgroundResource(R.drawable.socialbanner);

                            }
                            JSONArray data = secobj.getJSONArray("data");
                            mSocialMediaArraylistInstagram.clear();
                            mSocialMediaArraylistFacebook.clear();
                            mSocialMediaArraylistTwitter.clear();
                            if (data.length() > 0) {
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataObject = data.getJSONObject(i);
                                    SocialMediaModel socialMediaModel = new SocialMediaModel();
                                    socialMediaModel.setId(dataObject.optString(JTAG_ID));
                                    socialMediaModel.setUrl(dataObject.optString(JTAG_URL));
                                    socialMediaModel.setTab_type(dataObject.optString(JTAG_TAB_TYPE));
                                    socialMediaModel.setImage(dataObject.optString(JTAG_IMAGE));
                                    if (dataObject.optString(JTAG_TAB_TYPE).contains("Facebook")) {
                                        mSocialMediaArraylistFacebook.add(socialMediaModel);
                                    }
                                    else if(dataObject.optString(JTAG_TAB_TYPE).contains("Twitter")){
                                        mSocialMediaArraylistTwitter.add(socialMediaModel);
                                        //mSocialMediaArray=mSocialMediaArraylistTwitter;
                                    }else if (dataObject.optString(JTAG_TAB_TYPE).contains("Instagram")) {
                                        mSocialMediaArraylistInstagram.add(socialMediaModel);
                                    }

                                }

                                //mSocialMediaArray=mSocialMediaArraylistFacebook;


                            } else {
                                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (response_code.equalsIgnoreCase("500")) {
                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                    } else if (response_code.equalsIgnoreCase("400")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                callSocialMediaListAPI(URL_GET_SOCIALMEDIA_LIS);
                            }
                        });
                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                callSocialMediaListAPI(URL_GET_SOCIALMEDIA_LIS);
                            }
                        });
                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                                callSocialMediaListAPI(URL_GET_SOCIALMEDIA_LIS);
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

    @Override
    public void onClick(View v) {
        if ((v == facebook)) {
            type = "facebook";
			/*if(AppUtils.isNetworkConnected(mContext)) {
				callSocialMediaListAPI(URL_GET_SOCIALMEDIA_LIS,type);
			}else{
				AppUtils.showDialogAlertDismiss((Activity)mContext,"Network Error",getString(R.string.no_internet),R.drawable.nonetworkicon,R.drawable.roundred);

			}*/
            if (mSocialMediaArraylistFacebook.size() <=0) {
                AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), "Link not available.", R.drawable.exclamationicon, R.drawable.round);

            }else if (mSocialMediaArraylistFacebook.size() == 1) {
//				Intent i = new Intent(Intent.ACTION_VIEW);
//				i.setData(Uri.parse(mSocialMediaArraylistFacebook.get(0).getUrl()));
//				startActivity(i);
                Intent mintent = new Intent(mContext, FullscreenWebViewActivityNoHeader.class);
                mintent.putExtra("url", mSocialMediaArraylistFacebook.get(0).getUrl());
                startActivity(mintent);
            } else {
                showSocialmediaList(mSocialMediaArraylistFacebook, type);

            }

        }
        else if(v==twitter){
            type="twitter";
            if(mSocialMediaArraylistTwitter.size()==1) {
                Intent mintent = new Intent(mContext, FullscreenWebViewActivityNoHeader.class);
                mintent.putExtra("url", mSocialMediaArraylistTwitter.get(0).getUrl());
                startActivity(mintent);
            }else{
                showSocialmediaList(mSocialMediaArraylistTwitter, type);

            }
			/*if(AppUtils.isNetworkConnected(mContext)) {
				callSocialMediaListAPI(URL_GET_SOCIALMEDIA_LIS,type);
			}else{
				AppUtils.showDialogAlertDismiss((Activity)mContext,"Network Error",getString(R.string.no_internet),R.drawable.nonetworkicon,R.drawable.roundred);

			}*/
        }
        else if (v == instagram) {
            type = "instagram";
            if (mSocialMediaArraylistInstagram.size() <=0) {
                AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), "Link not available.", R.drawable.exclamationicon, R.drawable.round);

            }else if (mSocialMediaArraylistInstagram.size() == 1) {
//				Intent i = new Intent(Intent.ACTION_VIEW);
//				i.setData(Uri.parse(mSocialMediaArraylistInstagram.get(0).getUrl()));
//				startActivity(i);
                Intent mintent = new Intent(mContext, FullscreenWebViewActivityNoHeader.class);
                mintent.putExtra("url", mSocialMediaArraylistInstagram.get(0).getUrl());
                startActivity(mintent);
            } else {
                showSocialmediaList(mSocialMediaArraylistInstagram, type);

            }

        }
    }
}
