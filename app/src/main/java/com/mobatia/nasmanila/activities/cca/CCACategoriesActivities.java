package com.mobatia.nasmanila.activities.cca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.cca.adapter.CCAsCategoryAdapter;
import com.mobatia.nasmanila.activities.cca.model.CCAActivityModel;
import com.mobatia.nasmanila.activities.cca.model.CCACategoryModel;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mobatia on 14/08/18.
 */

public class CCACategoriesActivities extends Activity implements URLConstants, JSONConstants {

    RecyclerView recycler_review;
    HeaderManager headermanager;
    RelativeLayout relativeHeader;
    ImageView back;
    ImageView home;
    String tab_type = "CCAs";
    Bundle extras;
    Context mContext;
    GridLayoutManager recyclerViewLayoutManager;
    ArrayList<CCAActivityModel> mCCACategoriesActivitiesList;
    ArrayList<CCACategoryModel> mCCACategoriesModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_cca_category_activity);
        mContext = this;
        extras = getIntent().getExtras();
        if (extras != null) {
            tab_type = extras.getString("tab_type");
        }
        mCCACategoriesModelList = new ArrayList<>();
        relativeHeader = findViewById(R.id.relativeHeader);
        recycler_review = findViewById(R.id.recycler_view_cca);
        headermanager = new HeaderManager(CCACategoriesActivities.this, tab_type);
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
        recycler_review.setHasFixedSize(true);
        recyclerViewLayoutManager = new GridLayoutManager(mContext, 1);
        recycler_review.setLayoutManager(recyclerViewLayoutManager);

        if (AppUtils.isNetworkConnected(mContext)) {
            ccaCategoryActivity();
        } else {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

        }
    }

    private void ccaCategoryActivity() {
        VolleyWrapper volleyWrapper = new VolleyWrapper(URL_CCAS_LIST);
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
                            JSONArray data = secobj.optJSONArray("data");

                            if (data.length() > 0) {
                                mCCACategoriesModelList = new ArrayList<>();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObjectData = data.optJSONObject(i);
                                    CCACategoryModel mCategoryModel = new CCACategoryModel();
                                    mCategoryModel.setId(jsonObjectData.optString("id"));
                                    mCategoryModel.setName(jsonObjectData.optString("name"));
                                    mCCACategoriesActivitiesList = new ArrayList<>();
                                    JSONArray activitiesArray = jsonObjectData.optJSONArray("activities");
                                    if (activitiesArray.length() > 0) {
                                        for (int j = 0; j < activitiesArray.length(); j++) {
                                            JSONObject jsonObjectActivities = activitiesArray.optJSONObject(j);
                                            CCAActivityModel mCCAActivityModel = new CCAActivityModel();
                                            mCCAActivityModel.setItemId(jsonObjectActivities.optString("id"));
                                            mCCAActivityModel.setItem_name(jsonObjectActivities.optString("item_name"));
                                            mCCAActivityModel.setDescription(jsonObjectActivities.optString("description"));
                                            mCCACategoriesActivitiesList.add(mCCAActivityModel);
                                        }
                                    }
                                    mCategoryModel.setmCCAActivityModelList(mCCACategoriesActivitiesList);
                                    mCCACategoriesModelList.add(mCategoryModel);

                                    CCAsCategoryAdapter mCCAsCategoryAdapter=new CCAsCategoryAdapter(mContext,mCCACategoriesModelList);
                                    recycler_review.setAdapter(mCCAsCategoryAdapter);
                                }

                            } else {
                                Toast.makeText(CCACategoriesActivities.this, "No CCA Categories available.", Toast.LENGTH_SHORT).show();
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
                        ccaCategoryActivity();

                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        ccaCategoryActivity();

                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        ccaCategoryActivity();

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
                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

            }
        });


    }


}
