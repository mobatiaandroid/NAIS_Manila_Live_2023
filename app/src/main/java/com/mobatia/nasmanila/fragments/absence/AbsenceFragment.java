/**
 *
 */
package com.mobatia.nasmanila.fragments.absence;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NaisTabConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.absence.adapter.AbsenceRecyclerAdapter;
import com.mobatia.nasmanila.fragments.absence.model.LeavesModel;
import com.mobatia.nasmanila.fragments.parents_evening.model.StudentModel;
import com.mobatia.nasmanila.fragments.sports.adapter.StrudentSpinnerAdapter;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.ItemOffsetDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author RIJO K JOSE
 */

public class AbsenceFragment extends Fragment implements
        NaisTabConstants, CacheDIRConstants, URLConstants,
        IntentPassValueConstants, NaisClassNameConstants, JSONConstants, StatusConstants {

    private View mRootView;
    private Context mContext;
    private RecyclerView mAbsenceListView;
    private String mTitle;
    private String mTabId;
    TextView mTitleTextView;
    TextView studentName;
    TextView newRequest;
    private RelativeLayout relMain;
    private RelativeLayout belowViewRelative;
    ArrayList<StudentModel> studentsModelArrayList = new ArrayList<>();
    private ArrayList<LeavesModel> mAbsenceListViewArray;
    LinearLayout mStudentSpinner;
    String stud_id = "";
    String studClass = "";
    String stud_img="";
    ImageView studImg;
    ArrayList<String> studentList = new ArrayList<>();
boolean firstVisit;
    public AbsenceFragment() {

    }

    public AbsenceFragment(String title, String tabId) {
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
        mRootView = inflater.inflate(R.layout.fragment_absence_list, container,
                false);
        setHasOptionsMenu(true);
        mContext = getActivity();
        firstVisit=true;
        initialiseUI();
        if (AppUtils.checkInternet(mContext)) {
            getStudentsListFirstAPI(URL_GET_STUDENT_LIST);
        } else {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }
        return mRootView;
    }

    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Jan 12, 2015 Author : Vandana
     * Surendranath
     *****************************************************/
    private void initialiseUI() {
        mTitleTextView = (TextView) mRootView.findViewById(R.id.titleTextView);
        mAbsenceListView = (RecyclerView) mRootView.findViewById(R.id.mAbsenceListView);
        relMain = (RelativeLayout) mRootView.findViewById(R.id.relMain);
        studImg=(ImageView)mRootView.findViewById(R.id.studImg);
        mStudentSpinner = (LinearLayout) mRootView.findViewById(R.id.studentSpinner);
        belowViewRelative = (RelativeLayout) mRootView.findViewById(R.id.belowViewRelative);
        relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        studentName = (TextView) mRootView.findViewById(R.id.studentName);
        newRequest = (TextView) mRootView.findViewById(R.id.newRequest);

        mTitleTextView.setText(ABSENCE);
        mAbsenceListView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mAbsenceListView.setLayoutManager(llm);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, 2);
        mAbsenceListView.addItemDecoration(itemDecoration);
        //mAbsenceListView.setLayoutManager(recyclerViewLayoutManager);
        mAbsenceListView.addItemDecoration(
                new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));

        mAbsenceListView.addOnItemTouchListener(new RecyclerItemListener(mContext, mAbsenceListView,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        if (mAbsenceListViewArray.size() > 0) {
                            Intent mIntent = new Intent(mContext, LeavesDetailActivity.class);
                            mIntent.putExtra("studentName", studentName.getText().toString());
                            mIntent.putExtra("studentClass", studClass);
                            mIntent.putExtra("studentImage", stud_img);
                            mIntent.putExtra("fromDate", mAbsenceListViewArray.get(position).getFrom_date());
                            mIntent.putExtra("toDate", mAbsenceListViewArray.get(position).getTo_date());
                            mIntent.putExtra("reasonForAbsence", mAbsenceListViewArray.get(position).getReason());
                            mContext.startActivity(mIntent);
                        }
                    }

                    public void onLongClickItem(View v, int position) {
                    }
                }));
        mStudentSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentsModelArrayList.size() > 0) {
                    showSocialmediaList(studentsModelArrayList);
                } else {
                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.student_not_available), R.drawable.exclamationicon, R.drawable.round);

                }
            }
        });
        newRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, LeaveRequestSubmissionActivity.class);
                mIntent.putExtra("studentName", studentName.getText().toString());
                mIntent.putExtra("studentId", PreferenceManager.getLeaveStudentId(mContext));
                mIntent.putExtra("StudentModelArray", studentsModelArrayList);
                mIntent.putExtra("studentImage", stud_img);
                mContext.startActivity(mIntent);
            }
        });
    }

    public void showSocialmediaList(final ArrayList<StudentModel> mStudentArray) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_student_media_list);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button dialogDismiss = (Button) dialog.findViewById(R.id.btn_dismiss);
        ImageView iconImageView = (ImageView) dialog.findViewById(R.id.iconImageView);
        iconImageView.setImageResource(R.drawable.boy);
        RecyclerView socialMediaList = (RecyclerView) dialog.findViewById(R.id.recycler_view_social_media);
        //if(mSocialMediaArray.get())
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            dialogDismiss.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_new));

        } else {
            dialogDismiss.setBackground(mContext.getResources().getDrawable(R.drawable.button_new));

        }
        socialMediaList.addItemDecoration(new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));

        socialMediaList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        socialMediaList.setLayoutManager(llm);

        StrudentSpinnerAdapter studentAdapter = new StrudentSpinnerAdapter(mContext, mStudentArray);
        socialMediaList.setAdapter(studentAdapter);
        dialogDismiss.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                dialog.dismiss();

            }

        });

        socialMediaList.addOnItemTouchListener(new RecyclerItemListener(mContext, socialMediaList,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        dialog.dismiss();
                        studentName.setText(mStudentArray.get(position).getmName());
                        stud_id = mStudentArray.get(position).getmId();
                        studClass = mStudentArray.get(position).getmClass();
                        PreferenceManager.setLeaveStudentId(mContext, stud_id);
                        PreferenceManager.setLeaveStudentName(mContext, mStudentArray.get(position).getmName());
                        stud_img= mStudentArray.get(position).getmPhoto();
                        if (!(stud_img.equals(""))) {

                            Picasso.with(mContext).load(AppUtils.replace(stud_img)).placeholder(R.drawable.student).fit().into(studImg);
                        }
                        else

                        {

                            studImg.setImageResource(R.drawable.student);
                        }
                        if (AppUtils.isNetworkConnected(mContext)) {
                            getList(URL_GET_LEAVEREQUEST_LIST, mStudentArray.get(position).getmId());
                        } else {
                            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                        }
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
        dialog.show();
    }


    public void getList(final String URL, final String student_id) {

        try {
            mAbsenceListViewArray = new ArrayList<>();
            final VolleyWrapper manager = new VolleyWrapper(URL);
            String[] name = new String[]{JTAG_ACCESSTOKEN, "users_id",
                    "student_id"};
            String[] value = new String[]{PreferenceManager.getAccessToken(mContext), PreferenceManager.getUserId(mContext), student_id};


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

                                        JSONArray dataArray = respObject.optJSONArray(JTAG_REQUESTS);

                                        if (dataArray.length() > 0) {
                                            for (int i = 0; i < dataArray.length(); i++) {
                                                JSONObject dataObject = dataArray.optJSONObject(i);
                                                LeavesModel mLeavesModel = new LeavesModel();
                                                mLeavesModel.setTo_date(dataObject.optString(JTAG_TODATE));
                                                mLeavesModel.setFrom_date(dataObject.optString(JTAG_FROMDATE));
                                                mLeavesModel.setReason(dataObject.optString(JTAG_REASON));
                                                mLeavesModel.setStatus(dataObject.optString(JTAG_STATUs));
                                                mAbsenceListViewArray.add(mLeavesModel);
                                            }
                                            AbsenceRecyclerAdapter mAbsenceRecyclerAdapter=new AbsenceRecyclerAdapter(mContext,mAbsenceListViewArray);
                                            mAbsenceListView.setAdapter(mAbsenceRecyclerAdapter);
                                        }
                                        else
                                        {
                                            AbsenceRecyclerAdapter mAbsenceRecyclerAdapter=new AbsenceRecyclerAdapter(mContext,mAbsenceListViewArray);
                                            mAbsenceListView.setAdapter(mAbsenceRecyclerAdapter);
                                            AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "No data available.", R.drawable.exclamationicon, R.drawable.round);

                                        }
//											for (int i = 0; i <= dataArray.length(); i++) {
//												if (i!=0) {
//													JSONObject dataObject = dataArray.getJSONObject(i-1);
//													mAbsenceListViewArray.add(getSearchValues(dataObject));
//												}
//												else
//												{
//													AboutusModel mAboutUsModel = new AboutusModel();
//													mAboutUsModel.setId("0");
//													mAboutUsModel.setUrl("");
//													mAboutUsModel.setTabType("Staff Directory");
//													mAbsenceListViewArray.add(mAboutUsModel);
//												}
//											}
//
//											mAbsenceListView.setAdapter(new CustomAboutUsAdapter(getActivity(), mAbsenceListViewArray));
//
//
//										} else {
//											AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);
//
//										}
                                    } else {
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
                                    getList(URL, student_id);

                                } else if (responsCode.equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                                    //Toast.makeText(mContext,"Failure",Toast.LENGTH_SHORT).show();
                                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                                }
                            } else {
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

    private void getStudentsListAPI(final String URLHEAD) {
        VolleyWrapper volleyWrapper = new VolleyWrapper(URLHEAD);
        String[] name = {"access_token", "users_id"};
        String[] value = {PreferenceManager.getAccessToken(mContext), PreferenceManager.getUserId(mContext)};
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
                            studentsModelArrayList.clear();
                            studentList.clear();
                            if (data.length() > 0) {
                                //studentsModelArrayList.add(0,);
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataObject = data.getJSONObject(i);
                                    studentsModelArrayList.add(addStudentDetails(dataObject));
                                    studentList.add(studentsModelArrayList.get(i).getmName());
                                }
                                studentName.setText(studentsModelArrayList.get(0).getmName());
                                stud_id = studentsModelArrayList.get(0).getmId();
                                PreferenceManager.setLeaveStudentId(mContext, stud_id);
                                PreferenceManager.setLeaveStudentName(mContext, studentsModelArrayList.get(0).getmName());

                                studClass = studentsModelArrayList.get(0).getmClass();
                                belowViewRelative.setVisibility(View.VISIBLE);
                                newRequest.setVisibility(View.VISIBLE);
                                if (AppUtils.isNetworkConnected(mContext)) {
                                    getList(URL_GET_LEAVEREQUEST_LIST, stud_id);
                                } else {
                                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                                }

                                // studentList.add("Select a child");

                                /*CustomSpinnerAdapter dataAdapter = new CustomSpinnerAdapter(mContext,
                                        R.layout.spinnertextwithoutbg, studentList,-1);
                                mStudentSpinner.setAdapter(dataAdapter);*/

                            } else {
                                belowViewRelative.setVisibility(View.INVISIBLE);
                                newRequest.setVisibility(View.INVISIBLE);

                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.student_not_available), R.drawable.exclamationicon, R.drawable.round);
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
                        getStudentsListAPI(URLHEAD);

                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getStudentsListAPI(URLHEAD);

                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getStudentsListAPI(URLHEAD);

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

    private StudentModel addStudentDetails(JSONObject dataObject) {
        StudentModel studentModel = new StudentModel();
        studentModel.setmId(dataObject.optString(JTAG_ID));
        studentModel.setmName(dataObject.optString(JTAG_TAB_NAME));
        studentModel.setmClass(dataObject.optString(JTAG_TAB_CLASS));
        studentModel.setmSection(dataObject.optString(JTAG_TAB_SECTION));
        studentModel.setmHouse(dataObject.optString("house"));
        studentModel.setmPhoto(dataObject.optString("photo"));
        return studentModel;
    }

    @Override
    public void onResume() {
        super.onResume();
        //other stuff
        if (firstVisit) {
            //do stuff for first visit only

            firstVisit = false;
        }
        else
        {
            if (AppUtils.isNetworkConnected(mContext)) {
                studentName.setText(PreferenceManager.getLeaveStudentName(mContext));
                getList(URL_GET_LEAVEREQUEST_LIST, PreferenceManager.getLeaveStudentId(mContext));
            } else {
                AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

            }
        }
    }
    private void getStudentsListFirstAPI(final String URLHEAD) {
        VolleyWrapper volleyWrapper = new VolleyWrapper(URLHEAD);
        String[] name = {"access_token", "users_id"};
        String[] value = {PreferenceManager.getAccessToken(mContext), PreferenceManager.getUserId(mContext)};
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
                            studentsModelArrayList.clear();
                            studentList.clear();
                            if (data.length() > 0) {
                                //studentsModelArrayList.add(0,);
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataObject = data.getJSONObject(i);
                                    studentsModelArrayList.add(addStudentDetails(dataObject));
                                    studentList.add(studentsModelArrayList.get(i).getmName());
                                }
                                studentName.setText(studentsModelArrayList.get(0).getmName());
                                stud_id = studentsModelArrayList.get(0).getmId();
                                stud_img= studentsModelArrayList.get(0).getmPhoto();
                                if (!(stud_img.equals(""))) {

                                    Picasso.with(mContext).load(AppUtils.replace(stud_img)).placeholder(R.drawable.student).fit().into(studImg);
                                }
                                else

                                {

                                    studImg.setImageResource(R.drawable.student);
                                }

                                PreferenceManager.setLeaveStudentId(mContext, stud_id);
                                PreferenceManager.setLeaveStudentName(mContext, studentsModelArrayList.get(0).getmName());

                                studClass = studentsModelArrayList.get(0).getmClass();
                                belowViewRelative.setVisibility(View.VISIBLE);
                                newRequest.setVisibility(View.VISIBLE);
                                if (AppUtils.isNetworkConnected(mContext)) {
                                    getListFirst(URL_GET_LEAVEREQUEST_LIST, stud_id);
                                } else {
                                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                                }

                                // studentList.add("Select a child");

                                /*CustomSpinnerAdapter dataAdapter = new CustomSpinnerAdapter(mContext,
                                        R.layout.spinnertextwithoutbg, studentList,-1);
                                mStudentSpinner.setAdapter(dataAdapter);*/

                            } else {
                                belowViewRelative.setVisibility(View.INVISIBLE);
                                newRequest.setVisibility(View.INVISIBLE);

                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.student_not_available), R.drawable.exclamationicon, R.drawable.round);
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
                        getStudentsListFirstAPI(URLHEAD);

                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getStudentsListFirstAPI(URLHEAD);

                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getStudentsListFirstAPI(URLHEAD);

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

    public void getListFirst(final String URL, final String student_id) {

        try {
            mAbsenceListViewArray = new ArrayList<>();
            final VolleyWrapper manager = new VolleyWrapper(URL);
            String[] name = new String[]{JTAG_ACCESSTOKEN, "users_id",
                    "student_id"};
            String[] value = new String[]{PreferenceManager.getAccessToken(mContext), PreferenceManager.getUserId(mContext), student_id};


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

                                        JSONArray dataArray = respObject.optJSONArray(JTAG_REQUESTS);

                                        if (dataArray.length() > 0) {
                                            for (int i = 0; i < dataArray.length(); i++) {
                                                JSONObject dataObject = dataArray.optJSONObject(i);
                                                LeavesModel mLeavesModel = new LeavesModel();
                                                mLeavesModel.setTo_date(dataObject.optString(JTAG_TODATE));
                                                mLeavesModel.setFrom_date(dataObject.optString(JTAG_FROMDATE));
                                                mLeavesModel.setReason(dataObject.optString(JTAG_REASON));
                                                mLeavesModel.setStatus(dataObject.optString(JTAG_STATUs));
                                                mAbsenceListViewArray.add(mLeavesModel);
                                            }
                                            AbsenceRecyclerAdapter mAbsenceRecyclerAdapter=new AbsenceRecyclerAdapter(mContext,mAbsenceListViewArray);
                                            mAbsenceListView.setAdapter(mAbsenceRecyclerAdapter);
                                        }
                                        else
                                        {
                                            AbsenceRecyclerAdapter mAbsenceRecyclerAdapter=new AbsenceRecyclerAdapter(mContext,mAbsenceListViewArray);
                                            mAbsenceListView.setAdapter(mAbsenceRecyclerAdapter);
//                                            AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "No data available.", R.drawable.exclamationicon, R.drawable.round);

                                        }
//											for (int i = 0; i <= dataArray.length(); i++) {
//												if (i!=0) {
//													JSONObject dataObject = dataArray.getJSONObject(i-1);
//													mAbsenceListViewArray.add(getSearchValues(dataObject));
//												}
//												else
//												{
//													AboutusModel mAboutUsModel = new AboutusModel();
//													mAboutUsModel.setId("0");
//													mAboutUsModel.setUrl("");
//													mAboutUsModel.setTabType("Staff Directory");
//													mAbsenceListViewArray.add(mAboutUsModel);
//												}
//											}
//
//											mAbsenceListView.setAdapter(new CustomAboutUsAdapter(getActivity(), mAbsenceListViewArray));
//
//
//										} else {
//											AppUtils.showDialogAlertDismiss((Activity)mContext,"Alert",getString(R.string.common_error),R.drawable.exclamationicon,R.drawable.round);
//
//										}
                                    } else {
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
                                    getListFirst(URL, student_id);

                                } else if (responsCode.equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                                    //Toast.makeText(mContext,"Failure",Toast.LENGTH_SHORT).show();
                                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                                }
                            } else {
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

}
