package com.mobatia.nasmanila.activities.bus_routes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.bus_routes.adapter.BusRouteListRecyclerviewAdapter;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.NameValueConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.sports.model.BusModel;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.recyclerviewmanager.ItemOffsetDecoration;

import java.util.ArrayList;

/**
 * Created by Rijo on 25/1/17.
 */
public class BusRoutesRecyclerViewActivity extends Activity implements URLConstants, StatusConstants, JSONConstants, NameValueConstants, NaisClassNameConstants {


    Context mContext;
    private RecyclerView recycler_view_photos;
    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    ImageView back;
    ImageView home;
    Intent intent;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<BusModel> mSportsModelArrayList;
    //    RecyclerView.LayoutManager recyclerViewLayoutManager;
    Bundle extras;
    String tab_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participation_recyclerview);
        mContext = this;
        initUI();
//        if (AppUtils.isNetworkConnected(mContext)) {
//            photosListApiCall();
//        } else {
//            AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
//        }
    }

    private void initUI() {
        extras = getIntent().getExtras();
        if (extras != null) {
            tab_type = extras.getString("tab_type");
            mSportsModelArrayList = (ArrayList<BusModel>) extras.getSerializable("bus_route_array");
        }
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        headermanager = new HeaderManager(BusRoutesRecyclerViewActivity.this, tab_type);
        headermanager.getHeader(relativeHeader, 1);
        back = headermanager.getLeftButton();
        home = headermanager.getLogoButton();
        headermanager.setButtonLeftSelector(R.drawable.back,
                R.drawable.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, HomeListAppCompatActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
        });

        recycler_view_photos = (RecyclerView) findViewById(R.id.recycler_view_photos);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
//        mSwipeRefreshLayout.setRefreshing(false);
        recycler_view_photos.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        int spacing = 5; // 50px
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, spacing);
        recycler_view_photos.addItemDecoration(itemDecoration);
        recycler_view_photos.setLayoutManager(llm);
//        recycler_view_photos.setLayoutManager(recyclerViewLayoutManager);
        recycler_view_photos.setAdapter(new BusRouteListRecyclerviewAdapter(mContext, mSportsModelArrayList));
//        recycler_view_photos.addOnItemTouchListener(new RecyclerItemListener(mContext, recycler_view_photos,
//                new RecyclerItemListener.RecyclerTouchListener() {
//                    public void onClickItem(View v, int position) {
////                        Intent intent = new Intent(mContext, PhotosViewPagerActivity.class);
////                        intent.putExtra("photo_array", mSportsModelArrayList.get(position).getSportsModelParticipantsArrayList());
////                        intent.putExtra("pos", position);
////                        startActivity(intent);
//                    }
//
//                    public void onLongClickItem(View v, int position) {
//                    }
//                }));
    }
}
