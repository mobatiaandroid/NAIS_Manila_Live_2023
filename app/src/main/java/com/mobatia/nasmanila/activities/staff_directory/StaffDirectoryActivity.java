package com.mobatia.nasmanila.activities.staff_directory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.activities.staff_directory.adapter.CustomStaffDirectoryAdapter;
import com.mobatia.nasmanila.activities.staff_directory.model.StaffModel;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class StaffDirectoryActivity extends Activity implements
		 URLConstants,JSONConstants{

	private Context mContext;

	RelativeLayout relativeHeader;
	HeaderManager headermanager;
	ImageView back;
	ImageView home;
	ArrayList<StaffModel> mStaffDirectoryListArray = new ArrayList<StaffModel>();
	//private ListView mStaffDirectoryList;
	RecyclerView mStaffDirectoryListView;
	ArrayList<String>bannerUrlImageArray;
	ImageView bannerImagePager;
//	ViewPager bannerImagePager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_staffdirectory_list);

		mContext = this;
		initialiseUI();
		if(AppUtils.isNetworkConnected(mContext)) {
			callStaffDirectoryListAPI(URL_STAFFDIRECTORY_LIST);
		}else{
			AppUtils.showDialogAlertDismiss((Activity)mContext,"Network Error",getString(R.string.no_internet),R.drawable.nonetworkicon,R.drawable.roundred);

		}

	}

	private void callStaffDirectoryListAPI(String URL) {
		mStaffDirectoryListArray = new ArrayList<StaffModel>();
		VolleyWrapper volleyWrapper=new VolleyWrapper(URL);
		String[] name={"access_token"};
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
							JSONArray data = secobj.getJSONArray("data");
							String bannerImage=secobj.optString(JTAG_BANNER_IMAGE);
							System.out.println("banner img---"+bannerImage);
							if (!bannerImage.equalsIgnoreCase("")) {
								Glide.with(mContext).load(AppUtils.replace(bannerImage)).centerCrop().into(bannerImagePager);

//								bannerUrlImageArray = new ArrayList<>();
//								bannerUrlImageArray.add(bannerImage);
//								bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, mContext));
							}
							else
							{
								bannerImagePager.setBackgroundResource(R.drawable.staffdirectory);

							}
							if (data.length() > 0) {
								for (int i = 0; i < data.length(); i++) {
									JSONObject dataObject = data.getJSONObject(i);
									mStaffDirectoryListArray.add(addStaffDetails(dataObject));
								}

								CustomStaffDirectoryAdapter customStaffDirectoryAdapter = new CustomStaffDirectoryAdapter(mContext, mStaffDirectoryListArray);
								mStaffDirectoryListView.setAdapter(customStaffDirectoryAdapter);
							} else {
								Toast.makeText(StaffDirectoryActivity.this, "No data found", Toast.LENGTH_SHORT).show();
							}
						}
					} else if (response_code.equalsIgnoreCase("500")) {
						AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

					} else if (response_code.equalsIgnoreCase("400")) {
						AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
							@Override
							public void tokenrenewed() {
							}
						});
						callStaffDirectoryListAPI(URL_STAFFDIRECTORY_LIST);

					} else if (response_code.equalsIgnoreCase("401")) {
						AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
							@Override
							public void tokenrenewed() {
							}
						});
						callStaffDirectoryListAPI(URL_STAFFDIRECTORY_LIST);

					} else if (response_code.equalsIgnoreCase("402")) {
						AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
							@Override
							public void tokenrenewed() {
							}
						});
						callStaffDirectoryListAPI(URL_STAFFDIRECTORY_LIST);

					} else {
						/*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();*/
						AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

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
				AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);

			}
		});


	}

	/*******************************************************
	 * Method name : initialiseUI Description : initialise UI elements
	 * Parameters : nil Return type : void Date : March 8, 2017 Author : RIJO K JOSE
	 *****************************************************/
	@SuppressWarnings("deprecation")
	public void initialiseUI() {
		relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
//		bannerImagePager= (ViewPager) findViewById(R.id.bannerImageViewPager);
		bannerImagePager= (ImageView) findViewById(R.id.bannerImageViewPager);
		mStaffDirectoryListView = (RecyclerView) findViewById(R.id.mStaffDirectoryListView);
		mStaffDirectoryListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
		mStaffDirectoryListView.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		mStaffDirectoryListView.setLayoutManager(llm);
		headermanager = new HeaderManager(StaffDirectoryActivity.this, "Staff Directory");
		headermanager.getHeader(relativeHeader, 0);
		back = headermanager.getLeftButton();
		headermanager.setButtonLeftSelector(R.drawable.back,
				R.drawable.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AppUtils.hideKeyBoard(mContext);
				finish();
			}
		});
		home = headermanager.getLogoButton();
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(mContext, HomeListAppCompatActivity.class);
				in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
		});
		mStaffDirectoryListView.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), mStaffDirectoryListView,
				new RecyclerItemListener.RecyclerTouchListener() {
					public void onClickItem(View v, int position) {
						Intent intent=new Intent(StaffDirectoryActivity.this,StaffListActivity.class);
						intent.putExtra("category_id",mStaffDirectoryListArray.get(position).getStaffCategoryId());
						intent.putExtra("title","Staff Directory");
						startActivity(intent);
					}

					public void onLongClickItem(View v, int position) {
						System.out.println("On Long Click Item interface");
					}
				}));
		/*mStaffDirectoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent=new Intent(StaffDirectoryActivity.this,StaffListActivity.class);
				intent.putExtra("category_id",mStaffDirectoryListArray.get(position).getStaffCategoryId());
				startActivity(intent);
			}
		});*/
		}

	private StaffModel addStaffDetails(JSONObject obj) {
		StaffModel model = new StaffModel();
		try {
			model.setStaffCategoryId(obj.optString("id"));
			model.setStaffCategoryName(obj.optString("category_name"));
		} catch (Exception ex) {
			System.out.println("Exception is" + ex);
		}

		return model;
	}

}
