/**
 * 
 */
package com.mobatia.nasmanila.fragments.sports;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.sports.adapter.SportsEventListAdapter;
import com.mobatia.nasmanila.fragments.sports.model.SportsModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SportsActivity  extends Activity implements
		NaisTabConstants,CacheDIRConstants, URLConstants,
		IntentPassValueConstants,NaisClassNameConstants,JSONConstants {

	private Context mContext;
	RecyclerView mSportListView;
	EditText searchEditText;
	ImageView btnImgsearch;
	ArrayList<SportsModel> filtered;
	LinearLayout searchLinear;
	ArrayList<SportsModel> mSportsModelArrayList = new ArrayList<>();
	HeaderManager headermanager;
	RelativeLayout relativeHeader;
	ImageView back;
	ImageView home;
	String tab_type = "Sports";
	Bundle extras;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sports_event_list);
		mContext=this;
		extras = getIntent().getExtras();
		if (extras != null) {
			tab_type = extras.getString("tab_type");
		}
		initialiseUI();
		btnImgsearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AppUtils.hideKeyBoard(mContext);


			}
		});
		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start,
									  int before, int count) {
				// TODO Auto-generated method stub
				if (mSportsModelArrayList.size() > 0) {
					filtered = new ArrayList<SportsModel>();
					for (int i = 0; i < mSportsModelArrayList.size(); i++) {
						if (mSportsModelArrayList.get(i).getSports_name()
								.toLowerCase().contains(s.toString().toLowerCase())) {
							filtered.add(mSportsModelArrayList.get(i));
						}
					}
					SportsEventListAdapter sportsEventListAdapters = new SportsEventListAdapter(mContext, filtered);
					mSportListView.setAdapter(sportsEventListAdapters);
					if (searchEditText.getText().toString()
							.equalsIgnoreCase("")) {
						SportsEventListAdapter sportsEventListAdapter=new SportsEventListAdapter(mContext,mSportsModelArrayList);
						mSportListView.setAdapter(sportsEventListAdapter);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		if(AppUtils.isNetworkConnected(mContext)) {
			callSportsEventListAPI(URL_GET_SPORTSEVENT_LIST);
		}else{
			AppUtils.showDialogAlertDismiss((Activity)mContext,"Network Error",getString(R.string.no_internet),R.drawable.nonetworkicon,R.drawable.roundred);

		}

	}
	private void initialiseUI() {
		relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
		headermanager = new HeaderManager(SportsActivity.this, tab_type);
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
		searchLinear= (LinearLayout) findViewById(R.id.searchLinear);
		searchEditText= (EditText) findViewById(R.id.searchEditText);
		btnImgsearch= (ImageView) findViewById(R.id.btnImgsearch);

		mSportListView = (RecyclerView) findViewById(R.id.mSporsEventListView);
		//mSportListView.addItemDecoration(new DividerItemDecoration(getDrawable(R.drawable.list_divider)));
		mSportListView.setHasFixedSize(true);

		LinearLayoutManager llm = new LinearLayoutManager(mContext);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		mSportListView.setLayoutManager(llm);
	}


	private void callSportsEventListAPI(String URL) {
		mSportsModelArrayList = new ArrayList<>();
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
							JSONArray data = secobj.getJSONArray("data");

							if (data.length() > 0) {
								for (int i = 0; i < data.length(); i++) {
									JSONObject dataObject = data.getJSONObject(i);
									mSportsModelArrayList.add(addSportsListDetails(dataObject));
								}
								SportsEventListAdapter sportsEventListAdapter=new SportsEventListAdapter(mContext,mSportsModelArrayList);
								mSportListView.setAdapter(sportsEventListAdapter);
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
							}
						});
						callSportsEventListAPI(URL_GET_SPORTSEVENT_LIST);

					} else if (response_code.equalsIgnoreCase("401")) {
						AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
							@Override
							public void tokenrenewed() {
							}
						});
						callSportsEventListAPI(URL_GET_SPORTSEVENT_LIST);

					} else if (response_code.equalsIgnoreCase("402")) {
						AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
							@Override
							public void tokenrenewed() {
							}
						});
						callSportsEventListAPI(URL_GET_SPORTSEVENT_LIST);

					} else {

						AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

					}
				} catch (Exception ex) {
					//System.out.println("The Exception in edit profile is" + ex.toString());
				}

			}

			@Override
			public void responseFailure(String failureResponse) {

				AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

			}
		});


	}

	public SportsModel addSportsListDetails(JSONObject obj) {
		SportsModel sportsModel = new SportsModel();
		sportsModel.setSports_id(obj.optString(JTAG_ID));
		sportsModel.setSports_name(obj.optString(JTAG_TAB_NAME));
		sportsModel.setSports_start_date(obj.optString(JTAG_EVENT_STARTDATE));
		sportsModel.setSports_end_date(obj.optString(JTAG_EVENT_ENDDATE));
		return sportsModel;
	}


}


/*extends Fragment implements
		NaisTabConstants,CacheDIRConstants, URLConstants,
		IntentPassValueConstants,NaisClassNameConstants,JSONConstants {

	private View mRootView;
	private Context mContext;
	TextView mTitleTextView;
	RecyclerView mSportListView;
	private String mTitle;
	private String mTabId;
	EditText searchEditText;
	ImageView btnImgsearch;
	ArrayList<SportsModel> filtered;
	LinearLayout searchLinear;

	ArrayList<SportsModel> mSportsModelArrayList = new ArrayList<>();
//	private CustomAboutUsAdapter mAdapter;
//	private ArrayList<AboutUsModel> mAboutUsListArray;
RelativeLayout relMain;
	public SportsActivity() {

	}

	public SportsActivity(String title, String tabId) {
		this.mTitle = title;
		this.mTabId = tabId;
	}

	*//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu,
	 * android.view.MenuInflater)
	 *//*
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.activity_sports_event_list, container,
				false);
//		setHasOptionsMenu(true);
		mContext = getActivity();
//		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
		initialiseUI();
		btnImgsearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AppUtils.hideKeyBoard(mContext);


			}
		});
		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start,
									  int before, int count) {
				// TODO Auto-generated method stub
				if (mSportsModelArrayList.size() > 0) {
					filtered = new ArrayList<SportsModel>();
					for (int i = 0; i < mSportsModelArrayList.size(); i++) {
						if (mSportsModelArrayList.get(i).getSports_name()
								.toLowerCase().contains(s.toString().toLowerCase())) {
							filtered.add(mSportsModelArrayList.get(i));
						}
					}
					SportsEventListAdapter sportsEventListAdapters=new SportsEventListAdapter(mContext,filtered);
					mSportListView.setAdapter(sportsEventListAdapters);
					if (searchEditText.getText().toString()
							.equalsIgnoreCase("")) {
						SportsEventListAdapter sportsEventListAdapter=new SportsEventListAdapter(mContext,mSportsModelArrayList);
						mSportListView.setAdapter(sportsEventListAdapter);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		if(AppUtils.isNetworkConnected(mContext)) {
			callSportsEventListAPI(URL_GET_SPORTSEVENT_LIST);
		}else{
			AppUtils.showDialogAlertDismiss((Activity)mContext,"Network Error",getString(R.string.no_internet),R.drawable.nonetworkicon,R.drawable.roundred);

		}
		return mRootView;
	}

	*//*******************************************************
	 * Method name : initialiseUI Description : initialise UI elements
	 * Parameters : nil Return type : void Date : Jan 12, 2015 Author : Vandana
	 * Surendranath
	 *****************************************************//*
	private void initialiseUI() {
		mTitleTextView = (TextView) mRootView.findViewById(R.id.titleTextView);
		relMain = (RelativeLayout) mRootView.findViewById(R.id.relMain);
		relMain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		mTitleTextView.setText(getString(R.string.sports_title));//getString(R.string.sports_title)
		searchLinear= (LinearLayout) mRootView.findViewById(R.id.searchLinear);
		searchEditText= (EditText) mRootView.findViewById(R.id.searchEditText);
		btnImgsearch= (ImageView) mRootView.findViewById(R.id.btnImgsearch);

		mSportListView = (RecyclerView) mRootView.findViewById(R.id.mSporsEventListView);
		//mSportListView.addItemDecoration(new DividerItemDecoration(getDrawable(R.drawable.list_divider)));
		mSportListView.setHasFixedSize(true);

		LinearLayoutManager llm = new LinearLayoutManager(mContext);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		mSportListView.setLayoutManager(llm);
	}


	private void callSportsEventListAPI(String URL) {
		mSportsModelArrayList = new ArrayList<>();
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
							JSONArray data = secobj.getJSONArray("data");

							if (data.length() > 0) {
								for (int i = 0; i < data.length(); i++) {
									JSONObject dataObject = data.getJSONObject(i);
									mSportsModelArrayList.add(addSportsListDetails(dataObject));
								}
								SportsEventListAdapter sportsEventListAdapter=new SportsEventListAdapter(mContext,mSportsModelArrayList);
								mSportListView.setAdapter(sportsEventListAdapter);
							} else {
								Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
							}
						}
					} else if (response_code.equalsIgnoreCase("500")) {
						AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

					} else if (response_code.equalsIgnoreCase("400")) {
						AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
							@Override
							public void tokenrenewed() {
								callSportsEventListAPI(URL_GET_SPORTSEVENT_LIST);
							}
						});
					} else if (response_code.equalsIgnoreCase("401")) {
						AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
							@Override
							public void tokenrenewed() {
								callSportsEventListAPI(URL_GET_SPORTSEVENT_LIST);
							}
						});
					} else if (response_code.equalsIgnoreCase("402")) {
						AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
							@Override
							public void tokenrenewed() {
								callSportsEventListAPI(URL_GET_SPORTSEVENT_LIST);
							}
						});
					} else {
						*//*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();*//*
						AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

					}
				} catch (Exception ex) {
					//System.out.println("The Exception in edit profile is" + ex.toString());
				}

			}

			@Override
			public void responseFailure(String failureResponse) {
				*//*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
						, getResources().getString(R.string.ok));
				dialog.show();*//*
				AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

			}
		});


	}

	public SportsModel addSportsListDetails(JSONObject obj) {
		SportsModel sportsModel = new SportsModel();
		sportsModel.setSports_id(obj.optString(JTAG_ID));
		sportsModel.setSports_name(obj.optString(JTAG_TAB_NAME));
		sportsModel.setSports_start_date(obj.optString(JTAG_EVENT_STARTDATE));
		sportsModel.setSports_end_date(obj.optString(JTAG_EVENT_ENDDATE));
		return sportsModel;
	}



}
*/