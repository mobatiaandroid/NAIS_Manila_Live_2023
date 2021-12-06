package com.mobatia.nasmanila.activities.cca;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.cca.adapter.CCAsListActivityAdapter;
import com.mobatia.nasmanila.activities.cca.model.CCADetailModel;
import com.mobatia.nasmanila.activities.cca.model.CCAModel;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.fragments.sports.adapter.StrudentSpinnerAdapter;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.ItemOffsetDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;
import com.mobatia.nasmanila.activities.cca.model.CCAchoiceModel;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.parents_evening.model.StudentModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CCA_Activity extends Activity implements URLConstants, JSONConstants {

    Context mContext;
    ArrayList<StudentModel> studentsModelArrayList;
    ArrayList<CCAModel> mCCAmodelArrayList;
    ArrayList<CCADetailModel> CCADetailModelArrayList;
    ArrayList<CCAchoiceModel> CCAchoiceModelArrayList;
    ArrayList<CCAchoiceModel> CCAchoiceModelArrayList2;
    TextView studentName;
    TextView textViewYear;
    String stud_id = "";
    String stud_class = "";
    String stud_name = "";
    String stud_img="";
    LinearLayout mStudentSpinner;
    HeaderManager headermanager;
    RelativeLayout relativeHeader;
    ImageView back;
    ImageView studImg;
    ImageView home;
    String tab_type = "CCAs";
    Bundle extras;
//    ArrayList<String> mCcaArrayList;
    RecyclerView recycler_review;
    GridLayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cca);
        initUI();
        mStudentSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSocialmediaList(studentsModelArrayList);
            }
        });
        if (AppUtils.isNetworkConnected(mContext)) {
            getStudentsListAPI(URL_GET_STUDENT_LIST);
        } else {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }
//        CCAsActivityAdapter mCCAsActivityAdapter = new CCAsActivityAdapter(CCA_Activity.this, mCcaArrayList);
//        recycler_review.setAdapter(mCCAsActivityAdapter);
    }

    public void initUI() {
        mContext = this;
        extras = getIntent().getExtras();
        if (extras != null) {
            tab_type = extras.getString("tab_type");
        }
//        mCcaArrayList = new ArrayList<>();
//        mCcaArrayList.add("Sunday CCA");
//        mCcaArrayList.add("Monday CCA");
//        mCcaArrayList.add("Tuesday CCA");
//        mCcaArrayList.add("Wednesday CCA");
//        mCcaArrayList.add("Thursday CCA");
//        mCcaArrayList.add("Friday CCA");
//        mCcaArrayList.add("Saturday CCA");
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        recycler_review = (RecyclerView) findViewById(R.id.recycler_view_cca);

        recycler_review.setHasFixedSize(true);
        recyclerViewLayoutManager = new GridLayoutManager(mContext, 1);
        int spacing = 5; // 50px
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, spacing);
        recycler_review.addItemDecoration(
                new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));
        recycler_review.addItemDecoration(itemDecoration);
        recycler_review.setLayoutManager(recyclerViewLayoutManager);
        headermanager = new HeaderManager(CCA_Activity.this, tab_type);
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
        mStudentSpinner = (LinearLayout) findViewById(R.id.studentSpinner);
        studentName = (TextView) findViewById(R.id.studentName);
        studImg=(ImageView)findViewById(R.id.studImg);
        textViewYear = (TextView) findViewById(R.id.textViewYear);
        recycler_review.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), recycler_review,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        if (mCCAmodelArrayList.get(position).getIsAttendee().equalsIgnoreCase("0")) {
                            if(mCCAmodelArrayList.get(position).getIsSubmissionDateOver().equalsIgnoreCase("0")) {
                                if ( mCCAmodelArrayList.get(position).getDetails().size()>0) {
                                    Intent intent = new Intent(mContext, CCASelectionActivity.class);
                                    intent.putExtra("CCA_Detail", mCCAmodelArrayList.get(position).getDetails());
                                    intent.putExtra("tab_type", tab_type);
                                    PreferenceManager.setStudIdForCCA(mContext, stud_id);
                                    PreferenceManager.setStudNameForCCA(mContext, stud_name);
                                    PreferenceManager.setStudClassForCCA(mContext, stud_class);
                                    PreferenceManager.setCCATitle(mContext, mCCAmodelArrayList.get(position).getTitle());
                                    PreferenceManager.setCCAItemId(mContext, mCCAmodelArrayList.get(position).getCca_days_id());
                                    startActivity(intent);
                                }
                                else
                                {
                                    AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "No data found.", R.drawable.exclamationicon, R.drawable.round);

                                }
                            }
                            else
                            {

                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "EL Sign-Up Closed.", R.drawable.exclamationicon, R.drawable.round);
//                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "Date of submission expired.", R.drawable.exclamationicon, R.drawable.round);

                            }
                        }
                        else if(mCCAmodelArrayList.get(position).getIsAttendee().equalsIgnoreCase("2"))
                        {
                            AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "Your EL choices have been submitted and are currently being processed", R.drawable.exclamationicon, R.drawable.round);
//                            AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "Already submitted your choices for this CCA term. You can view the details, once they are approved by BISAD.", R.drawable.exclamationicon, R.drawable.round);

                        }else {
                            Intent intent = new Intent(mContext, CCAsReviewAfterSubmissionActivity.class);
                            intent.putExtra("tab_type", tab_type);
                            PreferenceManager.setStudIdForCCA(mContext, stud_id);
                            PreferenceManager.setStudNameForCCA(mContext, stud_name);
                            PreferenceManager.setStudClassForCCA(mContext, stud_class);
                            PreferenceManager.setCCATitle(mContext, mCCAmodelArrayList.get(position).getTitle());
                            PreferenceManager.setCCAItemId(mContext, mCCAmodelArrayList.get(position).getCca_days_id());
                            startActivity(intent);
                        }

                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
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

    private void getStudentsListAPI(final String URLHEAD) {
        VolleyWrapper volleyWrapper = new VolleyWrapper(URLHEAD);
        String[] name = {"access_token", "users_id"};
        String[] value = {PreferenceManager.getAccessToken(mContext), PreferenceManager.getUserId(mContext)};
        volleyWrapper.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {
            @Override
            public void responseSuccess(String successResponse) {
                System.out.println("The response is student list" + successResponse);
                try {
                    studentsModelArrayList = new ArrayList<>();
                    JSONObject obj = new JSONObject(successResponse);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase("200")) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                        String status_code = secobj.getString(JTAG_STATUSCODE);
                        if (status_code.equalsIgnoreCase("303")) {
                            JSONArray data = secobj.getJSONArray("data");
//                            studentsModelArrayList.clear();
//                            studentList.clear();
                            if (data.length() > 0) {
                                //studentsModelArrayList.add(0,);
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataObject = data.getJSONObject(i);
                                    studentsModelArrayList.add(addStudentDetails(dataObject));
//                                    studentList.add(studentsModelArrayList.get(i).getmName());
                                }
                                if (PreferenceManager.getStudIdForCCA(mContext).equalsIgnoreCase("")) {
                                    studentName.setText(studentsModelArrayList.get(0).getmName());
                                    stud_id = studentsModelArrayList.get(0).getmId();
                                    stud_name = studentsModelArrayList.get(0).getmName();
                                    stud_class = studentsModelArrayList.get(0).getmClass();
                                    stud_img= studentsModelArrayList.get(0).getmPhoto();
                                    if (!(stud_img.equals(""))) {

                                        Picasso.with(mContext).load(AppUtils.replace(stud_img)).placeholder(R.drawable.student).fit().into(studImg);
                                    }
                                    else

                                    {

                                        studImg.setImageResource(R.drawable.student);
                                    }


                                    textViewYear.setText("Class : " + studentsModelArrayList.get(0).getmClass());
                                    PreferenceManager.setCCAStudentIdPosition(mContext, "0");
                                }
                                else
                                {
                                    int studentSelectPosition=Integer.valueOf(PreferenceManager.getCCAStudentIdPosition(mContext));

                                    studentName.setText(studentsModelArrayList.get(studentSelectPosition).getmName());
                                    stud_id = studentsModelArrayList.get(studentSelectPosition).getmId();
                                    stud_name = studentsModelArrayList.get(studentSelectPosition).getmName();
                                    stud_class = studentsModelArrayList.get(studentSelectPosition).getmClass();
                                    stud_img= studentsModelArrayList.get(0).getmPhoto();
                                    if (!(stud_img.equals(""))) {

                                        Picasso.with(mContext).load(AppUtils.replace(stud_img)).placeholder(R.drawable.student).fit().into(studImg);
                                    }
                                    else

                                    {

                                        studImg.setImageResource(R.drawable.student);
                                    }
                                    textViewYear.setText("Class : " + studentsModelArrayList.get(studentSelectPosition).getmClass());

                                }
                                getCCAListAPI(stud_id);


                            } else {
                                Toast.makeText(CCA_Activity.this, "No Student found.", Toast.LENGTH_SHORT).show();
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

    public void showSocialmediaList(final ArrayList<StudentModel> mStudentArray) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_student_list);
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
                        stud_name = mStudentArray.get(position).getmName();
                        stud_class = mStudentArray.get(position).getmClass();
                        stud_img= mStudentArray.get(position).getmPhoto();
                        if (!(stud_img.equals(""))) {

                            Picasso.with(mContext).load(AppUtils.replace(stud_img)).placeholder(R.drawable.student).fit().into(studImg);
                        }
                        else

                        {

                            studImg.setImageResource(R.drawable.student);
                        }
                        textViewYear.setText("Class : " + mStudentArray.get(position).getmClass());
                        PreferenceManager.setCCAStudentIdPosition(mContext, position+"");
                        getCCAListAPI(stud_id);

                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
        dialog.show();
    }

    private void getCCAListAPI(final String studentId) {
        VolleyWrapper volleyWrapper = new VolleyWrapper(URL_CCA_DETAILS);
        String[] name = {"access_token", "student_id"};
        String[] value = {PreferenceManager.getAccessToken(mContext), studentId};
        volleyWrapper.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {
            @Override
            public void responseSuccess(String successResponse) {
                System.out.println("The response is" + successResponse);
                try {
                    mCCAmodelArrayList = new ArrayList<>();
                    JSONObject obj = new JSONObject(successResponse);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase("200")) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                        String status_code = secobj.getString(JTAG_STATUSCODE);
                        if (status_code.equalsIgnoreCase("303")) {
                            JSONArray data = secobj.optJSONArray("data");
                            if (data.length() > 0) {
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject dataObject = data.getJSONObject(i);
                                    mCCAmodelArrayList.add(addCCAlist(dataObject));
                                }
                                if (mCCAmodelArrayList.size()>0) {
                                    CCAsListActivityAdapter mCCAsActivityAdapter = new CCAsListActivityAdapter(CCA_Activity.this, mCCAmodelArrayList);
                                    recycler_review.setAdapter(mCCAsActivityAdapter);
                                }

                            } else {
                                Toast.makeText(CCA_Activity.this, "No EL available.", Toast.LENGTH_SHORT).show();
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
                        getCCAListAPI(studentId);

                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getCCAListAPI(studentId);

                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getCCAListAPI(studentId);

                    } else {
						/*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();*/
                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                    }
                } catch (Exception ex) {
                    System.out.println("The Exception in CCA profile is" + ex.toString());
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

    private CCAModel addCCAlist(JSONObject dataObject) {
        CCAModel mCCAModel = new CCAModel();
        mCCAModel.setCca_days_id(dataObject.optString("cca_days_id"));
        mCCAModel.setTitle(dataObject.optString("title"));
        mCCAModel.setFrom_date(dataObject.optString("from_date"));
        mCCAModel.setTo_date(dataObject.optString("to_date"));
        mCCAModel.setIsAttendee(dataObject.optString("isAttendee"));
        mCCAModel.setSubmission_dateTime(dataObject.optString("submission_dateTime"));
        mCCAModel.setIsSubmissionDateOver(dataObject.optString("isSubmissiondateOver"));
        JSONArray jsonCCADetailArray = dataObject.optJSONArray("details");
        CCADetailModelArrayList = new ArrayList<>();
        if (jsonCCADetailArray.length()>0) {
            for (int i = 0; i < jsonCCADetailArray.length(); i++) {
                JSONObject objectCCA = jsonCCADetailArray.optJSONObject(i);
                CCADetailModel mCCADetailModel = new CCADetailModel();
                mCCADetailModel.setDay(objectCCA.optString("day"));
                JSONArray jsonCCAChoiceArray = objectCCA.optJSONArray("choice1");
                JSONArray jsonCCAChoiceArray2 = objectCCA.optJSONArray("choice2");

                CCAchoiceModelArrayList = new ArrayList<>();
                if (jsonCCAChoiceArray.length() > 0) {
                    for (int j = 0; j <= jsonCCAChoiceArray.length(); j++) {
                        CCAchoiceModel mCCADetailModelchoice = new CCAchoiceModel();
                        if ( jsonCCAChoiceArray.length()!=j) {
                            JSONObject objectCCAchoice = jsonCCAChoiceArray.optJSONObject(j);
                            mCCADetailModelchoice.setCca_item_name(objectCCAchoice.optString("cca_item_name"));
                            mCCADetailModelchoice.setCca_details_id(objectCCAchoice.optString("cca_details_id"));
                            mCCADetailModelchoice.setIsattending(objectCCAchoice.optString("isAttendee"));
                            mCCADetailModelchoice.setCca_item_start_time(objectCCAchoice.optString("cca_item_start_time"));
                            mCCADetailModelchoice.setCca_item_description(objectCCAchoice.optString("cca_item_description"));
                            mCCADetailModelchoice.setVenue(objectCCAchoice.optString("venue"));
                            mCCADetailModelchoice.setCca_item_end_time(objectCCAchoice.optString("cca_item_end_time"));
                            mCCADetailModelchoice.setStatus("0");
                            mCCADetailModelchoice.setDayChoice(objectCCAchoice.optString("day"));

                        }
                        else
                        {
                            mCCADetailModelchoice.setCca_item_name("None");
                            mCCADetailModelchoice.setCca_details_id("-541");
                            mCCADetailModelchoice.setIsattending("0");
                            mCCADetailModelchoice.setStatus("0");
                            mCCADetailModelchoice.setCca_item_description("0");
                            mCCADetailModelchoice.setVenue("0");
                            mCCADetailModelchoice.setDayChoice(objectCCA.optString("day"));

                        }
                        CCAchoiceModelArrayList.add(mCCADetailModelchoice);
                    }
                }
                mCCADetailModel.setCcaChoiceModel(CCAchoiceModelArrayList);
                CCAchoiceModelArrayList2 = new ArrayList<>();
                if (jsonCCAChoiceArray2.length() > 0) {
                    for (int j = 0; j <= jsonCCAChoiceArray2.length(); j++) {
                        CCAchoiceModel mCCADetailModelchoice = new CCAchoiceModel();
                        if (jsonCCAChoiceArray2.length()!=j) {
                            JSONObject objectCCAchoice = jsonCCAChoiceArray2.optJSONObject(j);
                            mCCADetailModelchoice.setCca_item_name(objectCCAchoice.optString("cca_item_name"));
                            mCCADetailModelchoice.setCca_details_id(objectCCAchoice.optString("cca_details_id"));
                            mCCADetailModelchoice.setIsattending(objectCCAchoice.optString("isAttendee"));
                            mCCADetailModelchoice.setCca_item_start_time(objectCCAchoice.optString("cca_item_start_time"));
                            mCCADetailModelchoice.setCca_item_end_time(objectCCAchoice.optString("cca_item_end_time"));
                            mCCADetailModelchoice.setCca_item_description(objectCCAchoice.optString("cca_item_description"));
                            mCCADetailModelchoice.setVenue(objectCCAchoice.optString("venue"));
                            mCCADetailModelchoice.setDayChoice(objectCCAchoice.optString("day"));
                            mCCADetailModelchoice.setStatus("0");
                        }
                        else
                        {
                            mCCADetailModelchoice.setCca_item_name("None");
                            mCCADetailModelchoice.setCca_details_id("-541");
                            mCCADetailModelchoice.setIsattending("0");
                            mCCADetailModelchoice.setStatus("0");
                            mCCADetailModelchoice.setDayChoice(objectCCA.optString("day"));
                            mCCADetailModelchoice.setCca_item_description("0");
                            mCCADetailModelchoice.setVenue("0");
                        }
                        CCAchoiceModelArrayList2.add(mCCADetailModelchoice);
                    }


                }
                mCCADetailModel.setCcaChoiceModel2(CCAchoiceModelArrayList2);
                CCADetailModelArrayList.add(mCCADetailModel);
            }
        }
        mCCAModel.setDetails(CCADetailModelArrayList);
        return mCCAModel;
    }
}
