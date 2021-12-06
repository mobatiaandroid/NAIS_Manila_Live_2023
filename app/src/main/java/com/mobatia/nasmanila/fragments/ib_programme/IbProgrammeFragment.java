/**
 * 
 */
package com.mobatia.nasmanila.fragments.ib_programme;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.coming_up.ib_programme.IbProgrammeActivity;
import com.mobatia.nasmanila.activities.news.NewsActivity;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.primary.adapter.CustomPrimaryAdapter;
import com.mobatia.nasmanila.fragments.primary.model.PrimaryModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Rijo K Jose
 * 
 */

public class IbProgrammeFragment extends Fragment implements OnItemClickListener,
		NaisTabConstants,CacheDIRConstants, URLConstants,JSONConstants,StatusConstants,
		IntentPassValueConstants,NaisClassNameConstants {
	TextView mTitleTextView;

	private View mRootView;
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

	public IbProgrammeFragment() {

	}

	public IbProgrammeFragment(String title, String tabId) {
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
		mRootView = inflater.inflate(R.layout.fragment_ibprogramme_list, container,
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
		mTitleTextView= (TextView) mRootView.findViewById(R.id.titleTextView);
		mTitleTextView.setText(IB_PROGRAMME);
		mListView = (ListView) mRootView.findViewById(R.id.mListView);
		bannerImagePager= (ImageView) mRootView.findViewById(R.id.bannerImagePager);
//		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImagePager);
		mListView.setOnItemClickListener(this);
		relMain = (RelativeLayout) mRootView.findViewById(R.id.relMain);
		relMain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
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

			if (position!=0)
			{
				if (mListViewArray.get(position).getmName().equalsIgnoreCase("News"))
				{
					 if (mListViewArray.get(position).getmPrimaryModel().size()>0)
					{
						Intent intent = new Intent(mContext, NewsActivity.class);
						intent.putExtra("pdf_urls", mListViewArray.get(position).getmPrimaryModel());
						intent.putExtra("tab_type", IB_PROGRAMME);
						startActivity(intent);
					}
				}	else if (mListViewArray.get(position).getmName().equalsIgnoreCase("Assessment Link"))
				{

					AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.module_under_construction), R.drawable.exclamationicon, R.drawable.round);

				}
				else {


					Intent intent = new Intent(mContext, NewsActivity.class);
					intent.putExtra("pdf_urls", mListViewArray.get(position).getmPrimaryModel());
					intent.putExtra("tab_type", IB_PROGRAMME);
					startActivity(intent);
				}
			}
			else {

				Intent intent = new Intent(mContext, IbProgrammeActivity.class);
				mContext.startActivity(intent);
			}

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

											mListView.setAdapter(new CustomPrimaryAdapter(getActivity(), mListViewArray));

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
									AppUtils.postInitParam(getActivity(), new AppUtils.GetAccessTokenInterface() {
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
