/**
 *
 */
package com.mobatia.nasmanila.fragments.sports;

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
import android.view.Gravity;
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
import com.mobatia.nasmanila.fragments.cca.adapter.CCARecyclerAdapter;
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
 * @author Gayathri
 */

public class SportsFragment extends Fragment implements OnItemClickListener,
        NaisTabConstants, CacheDIRConstants, URLConstants,
        IntentPassValueConstants, NaisClassNameConstants, JSONConstants, StatusConstants {
    TextView mTitleTextView;

    private View mRootView;
    private Context mContext;
    //	private ListView mListView;
    private String mTitle;
    private String mTabId;
    private String description = "";
    private RelativeLayout relMain;
    TextView descriptionTV;
    TextView descriptionTitle;
    RelativeLayout mtitleRel;
    RelativeLayout CCAFRegisterRel;
    private ArrayList<SecondaryModel> mListViewArray;
    ImageView bannerImagePager;
    //	ViewPager bannerImagePager;
    ArrayList<String> bannerUrlImageArray;
    private RecyclerView mListView;
    String contactEmail = "";
    EditText text_dialog;
    EditText text_content;
    ImageView sendEmail;

    public SportsFragment() {

    }

    public SportsFragment(String title, String tabId) {
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
        mRootView = inflater.inflate(R.layout.fragment_sportspdf_list, container,
                false);
        mContext = getActivity();
        initialiseUI();

        return mRootView;
    }

    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Jan 12, 2015 Author : Vandana
     * Surendranath
     *****************************************************/
    private void initialiseUI() {
        mTitleTextView = (TextView) mRootView.findViewById(R.id.titleTextView);
        mTitleTextView.setText(SPORTS);
//		mListView = (ListView) mRootView.findViewById(R.id.mListView);
        bannerImagePager = (ImageView) mRootView.findViewById(R.id.bannerImagePager);
//		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImagePager);
        mListView = (RecyclerView) mRootView.findViewById(R.id.mListView);
        descriptionTV = (TextView) mRootView.findViewById(R.id.descriptionTV);
        descriptionTitle = (TextView) mRootView.findViewById(R.id.descriptionTitle);
        sendEmail = (ImageView) mRootView.findViewById(R.id.sendEmail);
        mtitleRel = (RelativeLayout) mRootView.findViewById(R.id.title);
        CCAFRegisterRel = (RelativeLayout) mRootView.findViewById(R.id.CCAFRegisterRel);
        relMain = (RelativeLayout) mRootView.findViewById(R.id.relMain);
        relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CCAFRegisterRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceManager.getUserId(mContext).equalsIgnoreCase("")) {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "This feature is available only for registered users. Login/register to see contents.", R.drawable.exclamationicon, R.drawable.round);
                } else {
                    Intent intent = new Intent(mContext, SportsActivity.class);
                    intent.putExtra("tab_type", SPORTS);
                    mContext.startActivity(intent);
                }
//				AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.module_under_construction), R.drawable.exclamationicon, R.drawable.round);

            }
        });
//		mListView.setOnItemClickListener(this);
        if (AppUtils.checkInternet(mContext)) {

            getList();
        } else {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }
        mListView.setHasFixedSize(true);
//        mNewsLetterListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
        GridLayoutManager recyclerViewLayout = new GridLayoutManager(mContext, 4);
        int spacing = 5; // 50px
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, spacing);
        mListView.addItemDecoration(itemDecoration);
        mListView.setLayoutManager(recyclerViewLayout);

        mListView.addOnItemTouchListener(new RecyclerItemListener(mContext, mListView,
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
                            Intent intent = new Intent(mContext, PDFViewActivity.class);
                            intent.putExtra("pdf_url", mListViewArray.get(position).getmFile());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
                            intent.putExtra("url", mListViewArray.get(position).getmFile());
                            intent.putExtra("tab_type", mListViewArray.get(position).getmName());
                            mContext.startActivity(intent);
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
                                text_dialog.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
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
                            System.out.println("submit btn clicked");
//                            sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF);
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

                } else {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, mContext.getString(R.string.alert_heading), "This feature is available only for registered users. Login/register to see contents.", R.drawable.exclamationicon, R.drawable.round);

                }
            }
        });
        PreferenceManager.setStudIdForCCA(mContext, "");
        PreferenceManager.setCCAStudentIdPosition(mContext, "0");
        PreferenceManager.setStudNameForCCA(mContext, "");
        PreferenceManager.setStudClassForCCA(mContext, "");
        PreferenceManager.setCCATitle(mContext, "");
        PreferenceManager.setCCAItemId(mContext, "");
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
//            Intent intent = new Intent(mContext, PDFViewActivity.class);
//            intent.putExtra("pdf_url", mListViewArray.get(position).getmFile());
//            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
            intent.putExtra("url", mListViewArray.get(position).getmFile());
            intent.putExtra("tab_type", mListViewArray.get(position).getmName());
            mContext.startActivity(intent);
        }

    }

    public void getList() {

        try {
            mListViewArray = new ArrayList<>();
            final VolleyWrapper manager = new VolleyWrapper(URL_SPORTS_PDF);
            String[] name = new String[]{JTAG_ACCESSTOKEN};
            String[] value = new String[]{PreferenceManager.getAccessToken(mContext)};


            manager.getResponseGET(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {

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
                                        String bannerImage = respObject.optString(JTAG_BANNER_IMAGE);
                                        description = respObject.optString(JTAG_DESCRIPTION);
                                        contactEmail = respObject.optString(JTAG_CONTACT_EMAIL);
                                        if (!bannerImage.equalsIgnoreCase("")) {
                                            Glide.with(mContext).load(AppUtils.replace(bannerImage)).centerCrop().into(bannerImagePager);

//											bannerUrlImageArray = new ArrayList<>();
//											bannerUrlImageArray.add(bannerImage);
//											bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, getActivity()));
                                        } else {
                                            bannerImagePager.setBackgroundResource(R.drawable.default_bannerr);
//											bannerImagePager.setBackgroundResource(R.drawable.ccas_banner);

                                        }
                                        if (description.equalsIgnoreCase("") && contactEmail.equalsIgnoreCase("")) {
                                            mtitleRel.setVisibility(View.GONE);
                                        } else {
                                            mtitleRel.setVisibility(View.VISIBLE);
                                        }
                                        if (description.equalsIgnoreCase("")) {
                                            descriptionTV.setVisibility(View.GONE);
                                            descriptionTitle.setVisibility(View.GONE);
                                        } else {
                                            descriptionTV.setText(description);
                                            descriptionTitle.setVisibility(View.GONE);
//											descriptionTitle.setVisibility(View.VISIBLE);
                                            descriptionTV.setVisibility(View.VISIBLE);
                                            mtitleRel.setVisibility(View.VISIBLE);
                                        }
                                        if (contactEmail.equalsIgnoreCase("")) {
                                            sendEmail.setVisibility(View.GONE);
                                        } else {
                                            mtitleRel.setVisibility(View.VISIBLE);
                                            sendEmail.setVisibility(View.VISIBLE);
                                        }
                                        CCAFRegisterRel.setVisibility(View.VISIBLE);
                                        JSONArray dataArray = respObject.getJSONArray(JTAG_RESPONSE_DATA_ARRAY);
                                        if (dataArray.length() > 0) {
                                            for (int i = 0; i < dataArray.length(); i++) {
                                                JSONObject dataObject = dataArray.getJSONObject(i);
                                                mListViewArray.add(getSearchValues(dataObject));

                                            }

//											mListView.setAdapter(new CustomSecondaryAdapter(getActivity(), mListViewArray));
                                            mListView.setAdapter(new CCARecyclerAdapter(mContext, mListViewArray));

                                        }
                                    } else {
//										CustomStatusDialog(RESPONSE_FAILURE);
                                        //Toast.makeText(mContext,"Failure",Toast.LENGTH_SHORT).show();
                                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                                    }
                                } else if (responsCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                                        responsCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                        responsCode.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)) {
                                    AppUtils.postInitParam(getActivity(), new AppUtils.GetAccessTokenInterface() {
                                        @Override
                                        public void getAccessToken() {
                                        }
                                    });
                                    getList();

                                } else if (responsCode.equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                                    //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                                }
                            } else {
//								CustomStatusDialog(RESPONSE_FAILURE);
                                //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

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
        VolleyWrapper volleyWrapper = new VolleyWrapper(URL);
        String[] name = {"access_token", "email", "users_id", "title", "message"};
        String[] value = {PreferenceManager.getAccessToken(mContext), contactEmail, PreferenceManager.getUserId(mContext), text_dialog.getText().toString(), text_content.getText().toString()};//contactEmail

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





/*extends Fragment implements OnItemClickListener,
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
	public SportsFragment() {

	}

	public SportsFragment(String title, String tabId) {
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

	*//*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 *//*
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
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

	//	private class GetAboutUsListAsyncTask extends
//			AsyncTask<Void, Integer, Void> {
//		private String tabId;
//		private String url;
//		private int isCache;
//		private AboutUsApi aboutApi;
//		private String dirName;
//		private InternetManager manager;
//
//		public GetAboutUsListAsyncTask(String url, String dirName, int cache,
//				String tabId) {
//			this.url = url;
//			this.isCache = cache;
//			this.dirName = dirName;
//			this.tabId = tabId;
//			manager = new InternetManager(url, mContext, dirName, isCache);
//			aboutApi = new AboutUsApi(mContext, manager, tabId);
//		}
//
//		*//*
//		 * (non-Javadoc)
//		 *
//		 * @see android.os.AsyncTask#onPreExecute()
//		 *//*
//		@Override
//		protected void onPreExecute() {
//			mProgressDialog.setVisibility(View.VISIBLE);
//			super.onPreExecute();
//		}
//
//		*//*
//		 * (non-Javadoc)
//		 *
//		 * @see android.os.AsyncTask#doInBackground(Params[])
//		 *//*
//		@Override
//		protected Void doInBackground(Void... params) {
//			mAboutUsListArray = aboutApi.getAboutUsList(mBannerImage);
//			return null;
//		}
//
//		*//*
//		 * (non-Javadoc)
//		 *
//		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
//		 *//*
//		@Override
//		protected void onPostExecute(Void result) {
//			mProgressDialog.setVisibility(View.GONE);
//			mAdapter = new CustomAboutUsAdapter(mContext, mAboutUsListArray,
//					mTitle, mTabId);
//			mAboutUsList.setAdapter(mAdapter);
//		}
//	}
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