package com.mobatia.nasmanila.activities.photos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.activities.photos.adapter.PhotosRecyclerviewAdapter;
import com.mobatia.nasmanila.appcontroller.AppController;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NameValueConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.gallery.model.PhotosListModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.manager.PreferenceManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;
import com.mobatia.nasmanila.volleywrappermanager.VolleyWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rijo on 25/1/17.
 */
public class PhotosRecyclerViewActivity extends Activity implements URLConstants, StatusConstants, JSONConstants, NameValueConstants,NaisClassNameConstants {
    Context mContext;
    private RecyclerView recycler_view_photos;
    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    ImageView back;
    ImageView home;
    Intent intent;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<PhotosListModel> mPhotosModelArrayList;
    ArrayList<PhotosListModel> mPhotosModelUrlArrayList;
    PhotosRecyclerviewAdapter mPhotosRecyclerviewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
  Bundle extras;
    String photo_id="-1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_recyclerview_activity);
        mContext = this;
        mPhotosModelArrayList = new ArrayList<PhotosListModel>();
        AppController.mPhotosModelArrayListGallery = new ArrayList<PhotosListModel>();
        initUI();
        if (AppUtils.isNetworkConnected(mContext)) {
            photosListApiCall();
        } else {
            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
        }
    }

    private void initUI() {
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        headermanager = new HeaderManager(PhotosRecyclerViewActivity.this, "Photos");
        headermanager.getHeader(relativeHeader, 1);
        back = headermanager.getLeftButton();
        headermanager.setButtonLeftSelector(R.drawable.back,
                R.drawable.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        extras=getIntent().getExtras();
        if(extras!=null){
            photo_id=extras.getString("photo_id");
        }
        recycler_view_photos = (RecyclerView) findViewById(R.id.recycler_view_photos);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
//        mSwipeRefreshLayout.setRefreshing(false);
        recycler_view_photos.setHasFixedSize(true);
        recyclerViewLayoutManager = new GridLayoutManager(mContext, 1);
//        int spacing = 10; // 50px
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext,spacing);
//        recycler_view_photos.addItemDecoration(itemDecoration);
//        recycler_view_photos.setLayoutManager(recyclerViewLayoutManager);
        recycler_view_photos.addItemDecoration(
                new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));
//        recycler_view_photos.addItemDecoration(itemDecoration);
        recycler_view_photos.setLayoutManager(recyclerViewLayoutManager);
        recycler_view_photos.addOnItemTouchListener(new RecyclerItemListener(mContext, recycler_view_photos,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        if ( mPhotosModelArrayList.get(position).getmPhotosUrlArrayList().size()>0) {
                            Intent intent = new Intent(mContext, PhotosViewRecyclerViewActivity.class);
//                            intent.putExtra("photo_array", mPhotosModelArrayList);
                            AppController.mPhotosModelArrayListGallery=mPhotosModelArrayList;
                            intent.putExtra("pos", position);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(mContext,"No photos available in this album.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void onLongClickItem(View v, int position) {
                    }
                }));

//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recycler_view_photos.setLayoutManager(llm);

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                photosListApiCall();
//                // Refresh items
//                refreshItems();
//            }
//        });


    }

    void refreshItems() {
        // Load items
        // ...
        // Load complete
        onItemsLoadComplete();
    }


    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }



    String getmonth(String month) {
        String mMonth = "";
        switch (month) {
            case "0":
                mMonth = "January";
                break;

            case "1":
                mMonth = "February";
                break;
            case "2":
                mMonth = "March";
                break;
            case "3":
                mMonth = "April";
                break;
            case "4":
                mMonth = "May";
                break;
            case "5":
                mMonth = "June";
                break;
            case "6":
                mMonth = "July";
                break;
            case "7":
                mMonth = "August";
                break;
            case "8":
                mMonth = "September";
                break;
            case "9":
                mMonth = "October";
                break;
            case "10":
                mMonth = "November";
                break;
            case "11":
                mMonth = "December";
                break;


        }
        return mMonth;
    }







    private void photosListApiCall() {
        try {
            mPhotosModelArrayList = new ArrayList<PhotosListModel>();
            String[] name = {NAME_ACCESS_TOKEN};
            String[] values = {PreferenceManager.getAccessToken(mContext)};
            final VolleyWrapper manager = new VolleyWrapper(URL_GET_PHOTOS_LIST);
            manager.getResponsePOST(mContext, 11, name, values,
                    new VolleyWrapper.ResponseListener() {

                        @Override
                        public void responseSuccess(String successResponse) {
                            if (successResponse != null) {
                                try {
                                    System.out.println("successResponse::" + successResponse);
                                    JSONObject rootObject = new JSONObject(successResponse);
                                    String responseCode = rootObject.getString(JTAG_RESPONSECODE);
                                    if (responseCode.equalsIgnoreCase(RESPONSE_SUCCESS)) {
                                        JSONObject responseObject = rootObject.optJSONObject(JTAG_RESPONSE);
                                        String statusCode = responseObject.getString(JTAG_STATUSCODE);
                                        if (statusCode.equalsIgnoreCase(STATUS_SUCCESS)) {

                                            JSONArray data = responseObject.optJSONArray(JTAG_RESPONSE_IMAGES_ARRAY);
                                            for (int i = 0; i < data.length(); i++) {
                                                JSONObject imageDetail = data.optJSONObject(i);
                                                PhotosListModel mPhotosModel = new PhotosListModel();
                                                mPhotosModel.setPhotoId(imageDetail.optString(JTAG_ID));
                                                mPhotosModel.setPhotoUrl(imageDetail.optString(JTAG_IMAGE));
                                                mPhotosModel.setTitle(imageDetail.optString(JTAG_TITLE));
                                                mPhotosModel.setDescription(imageDetail.optString(JTAG_DESCRIPTION));
                                                JSONArray st = imageDetail.optJSONArray("gallery_images");
                                                mPhotosModelUrlArrayList = new ArrayList<PhotosListModel>();
                                                for(int j=0;j<st.length();j++)
                                                {
                                                    //String photosUrl = st.getString(j);
                                                    String photosUrl = st.getJSONObject(j).optString(JTAG_IMAGE);
                                                    PhotosListModel mPhotosModelUrl = new PhotosListModel();
                                                    mPhotosModelUrl.setPhotoUrl(photosUrl);
                                                    mPhotosModelUrlArrayList.add(mPhotosModelUrl);
                                                    // loop and add it to array or arraylist
                                                }
                                                mPhotosModel.setmPhotosUrlArrayList(mPhotosModelUrlArrayList);
                                                mPhotosModelArrayList.add(mPhotosModel);
                                            }
                                            mPhotosRecyclerviewAdapter = new PhotosRecyclerviewAdapter(mContext, mPhotosModelArrayList,photo_id);
                                            recycler_view_photos.setAdapter(mPhotosRecyclerviewAdapter);
                                        } else if (statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                                statusCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING)) {
                                            AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                                @Override
                                                public void getAccessToken() {
                                                }
                                            });
                                            photosListApiCall();

                                        } else {
                                            Toast.makeText(PhotosRecyclerViewActivity.this, "Status Failure:" + rootObject.getString("Status"), Toast.LENGTH_SHORT).show();

                                        }

                                    }else if (responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                                            responseCode.equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                                            responseCode.equalsIgnoreCase(RESPONSE_INVALID_TOKEN)) {
                                        AppUtils.postInitParam(mContext, new AppUtils.GetAccessTokenInterface() {
                                            @Override
                                            public void getAccessToken() {
                                            }
                                        });
                                        photosListApiCall();

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
                            //CustomStatusDialog(RESPONSE_FAILURE);
                        }

                    });
        } catch (Exception e) {
            // CustomStatusDialog(RESPONSE_FAILURE);
            e.printStackTrace();
        }
    }
}
