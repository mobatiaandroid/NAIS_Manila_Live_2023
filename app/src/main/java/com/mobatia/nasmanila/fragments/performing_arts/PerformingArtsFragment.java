/**
 * 
 */
package com.mobatia.nasmanila.fragments.performing_arts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.pdf.PDFViewActivity;
import com.mobatia.nasmanila.activities.pdf.PdfViewActivityNew;
import com.mobatia.nasmanila.activities.web_view.LoadUrlWebViewActivity;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.performing_arts.adapter.PerformingArtsListAdapter;
import com.mobatia.nasmanila.fragments.secondary.model.SecondaryModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.ItemOffsetDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Rijo K Jose
 * 
 */

public class PerformingArtsFragment extends Fragment implements OnItemClickListener,
		NaisTabConstants,CacheDIRConstants, URLConstants,
		IntentPassValueConstants,NaisClassNameConstants,JSONConstants,StatusConstants {
	TextView mTitleTextView;
	TextView descriptionTV;
	TextView descriptionTitle;
	TextView text_content;
	TextView text_dialog;

	private View mRootView;
	private Context mContext;
//	private ListView mListView;
	private RecyclerView mListView;
	private String mTitle;
	private String mTabId;
	private RelativeLayout relMain,mtitleRel;
	private ArrayList<SecondaryModel> mListViewArray;
//	ViewPager bannerImagePager;
	ImageView bannerImagePager;
	ArrayList<String> bannerUrlImageArray;
   ImageView mailImageView;
	String description="";
	String contactEmail="";
	public PerformingArtsFragment() {

	}

	public PerformingArtsFragment(String title, String tabId) {
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
		mRootView = inflater.inflate(R.layout.fragment_performingart_list, container,
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

//		mListView = (ListView) mRootView.findViewById(R.id.mPerformingArtListView);
		mListView = (RecyclerView) mRootView.findViewById(R.id.mPerformingArtListView);
		bannerImagePager= (ImageView) mRootView.findViewById(R.id.bannerImagePager);
//		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImagePager);
		descriptionTV = (TextView) mRootView.findViewById(R.id.descriptionTV);
		descriptionTitle = (TextView) mRootView.findViewById(R.id.descriptionTitle);
		mailImageView = (ImageView) mRootView.findViewById(R.id.mailImageView);
		mtitleRel = (RelativeLayout) mRootView.findViewById(R.id.title);

		relMain = (RelativeLayout) mRootView.findViewById(R.id.relMain);
		relMain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
//		mListView.setOnItemClickListener(this);
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
//						sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);
                        if (text_dialog.getText().equals("")) {
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

		mListView.setHasFixedSize(true);
//        mNewsLetterListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
		GridLayoutManager recyclerViewLayout= new GridLayoutManager(mContext, 4);
		int spacing = 5; // 50px
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext,spacing);
		mListView.addItemDecoration(itemDecoration);
		mListView.setLayoutManager(recyclerViewLayout);
		mListView.addOnItemTouchListener(new RecyclerItemListener(getActivity(), mListView,
				new RecyclerItemListener.RecyclerTouchListener() {
					public void onClickItem(View v, int position) {

						if (mListViewArray.get(position).getmFile().endsWith(".pdf")) {
							if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
								startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mListViewArray.get(position).getmFile())));
							}else{
								Intent intent = new Intent(mContext, PDFViewActivity.class);
								intent.putExtra("pdf_url", mListViewArray.get(position).getmFile());
								startActivity(intent);
							}
//							Intent intent = new Intent(mContext, PDFViewActivity.class);
//							intent.putExtra("pdf_url", mListViewArray.get(position).getmFile());
//							startActivity(intent);
						}
						else
						{
							Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
							intent.putExtra("url",mListViewArray.get(position).getmFile());
							intent.putExtra("tab_type",PERFORMING_ARTS);
							mContext.startActivity(intent);
						}

					}

					public void onLongClickItem(View v, int position) {
						System.out.println("On Long Click Item interface");
					}
				}));
		if (AppUtils.checkInternet(mContext)) {

			getList();
		}
		else
		{
			AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

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
		if (mListViewArray.get(position).getmFile().endsWith(".pdf")) {
			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mListViewArray.get(position).getmFile())));
			}else{
				Intent intent = new Intent(mContext, PDFViewActivity.class);
				intent.putExtra("pdf_url", mListViewArray.get(position).getmFile());
				startActivity(intent);
			}
//			Intent intent = new Intent(mContext, PDFViewActivity.class);
//			intent.putExtra("pdf_url", mListViewArray.get(position).getmFile());
//			startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
			intent.putExtra("url",mListViewArray.get(position).getmFile());
			intent.putExtra("tab_type",mListViewArray.get(position).getmName());
			mContext.startActivity(intent);
		}

	}

	public void getList() {

		try {
			mListViewArray = new ArrayList<>();
			final VolleyWrapper manager = new VolleyWrapper(URL_PERFORMING_ARTS_LIST);
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
										 description=respObject.optString(JTAG_DESCRIPTION);
										 contactEmail=respObject.optString(JTAG_CONTACT_EMAIL);

										if (!bannerImage.equalsIgnoreCase("")) {
//											bannerUrlImageArray = new ArrayList<>();
//											bannerUrlImageArray.add(bannerImage);
//											bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, getActivity()));
											Glide.with(mContext).load(AppUtils.replace(bannerImage)).centerCrop().into(bannerImagePager);

										}
										else
										{
											bannerImagePager.setBackgroundResource(R.drawable.default_bannerr);
//											bannerImagePager.setBackgroundResource(R.drawable.performigart);

										}
										if (description.equalsIgnoreCase("")&&contactEmail.equalsIgnoreCase(""))
										{
											mtitleRel.setVisibility(View.GONE);
										}
										else
										{
											mtitleRel.setVisibility(View.VISIBLE);
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
											mtitleRel.setVisibility(View.VISIBLE);
										}
										if (contactEmail.equalsIgnoreCase(""))
										{
											mailImageView.setVisibility(View.GONE);
										}else
										{
											mtitleRel.setVisibility(View.VISIBLE);
											mailImageView.setVisibility(View.VISIBLE);
										}
										JSONArray dataArray = respObject.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);
										if (dataArray.length() > 0) {
											for (int i = 0; i <dataArray.length(); i++) {
													JSONObject dataObject = dataArray.getJSONObject(i);
													mListViewArray.add(getSearchValues(dataObject));

											}
//											mListView.setAdapter(new CustomSecondaryAdapter(getActivity(), mListViewArray));
											mListView.setAdapter(new PerformingArtsListAdapter(getActivity(), mListViewArray));

										} else {
											//CustomStatusDialog();
											Toast.makeText(mContext,"No Data Found", Toast.LENGTH_SHORT).show();

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
                                    getList();

                                }else if (responsCode.equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
									//Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
									AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

								}
							} else  {
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
					AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);
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
		mSecondaryModel.setmFile(Object.getString(JTAG_TAB_FILE));
		mSecondaryModel.setmName(Object.getString(JTAG_TAB_NAME));
		return mSecondaryModel;
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
							}
						});
						sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);

					} else if (response_code.equalsIgnoreCase("401")) {
						AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
							@Override
							public void tokenrenewed() {
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
