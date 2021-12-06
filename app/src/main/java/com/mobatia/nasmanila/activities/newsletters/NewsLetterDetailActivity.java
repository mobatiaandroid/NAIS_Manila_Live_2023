package com.mobatia.nasmanila.activities.newsletters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.activities.newsletters.adapter.NewsDetailListAdapter;
import com.mobatia.nasmanila.activities.newsletters.model.NewsLetterModel;
import com.mobatia.nasmanila.activities.pdf.PDFViewActivity;
import com.mobatia.nasmanila.activities.pdf.PdfViewActivityNew;
import com.mobatia.nasmanila.activities.web_view.LoadUrlWebViewActivity;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.ResultConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;

import java.util.ArrayList;

/**
 * Created by gayatri on 23/3/17.
 */
public class NewsLetterDetailActivity extends Activity
        implements JSONConstants,URLConstants,ResultConstants,StatusConstants {
    Bundle extras;
    ArrayList<NewsLetterModel> list;
    String tab_type;
    Context mContext=this;
    RecyclerView mNewsLetterListView;
    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    ImageView back;
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newslettercategory_layout);
        initUI();
        setListAdapter();

    }

    private void setListAdapter() {
        NewsDetailListAdapter newsDetailListAdapter=new NewsDetailListAdapter(mContext,list);
                mNewsLetterListView.setAdapter(newsDetailListAdapter);
    }

    private void initUI() {
        extras=getIntent().getExtras();
        if(extras!=null) {
            list = (ArrayList<NewsLetterModel>) extras.getSerializable("submenuArray");
            tab_type=extras.getString("tab_type");
        }
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        mNewsLetterListView = (RecyclerView) findViewById(R.id.mnewsLetterListView);
        mNewsLetterListView.setHasFixedSize(true);
        mNewsLetterListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
        headermanager = new HeaderManager(NewsLetterDetailActivity.this, tab_type);//"Newsletters");//rijo cmmented
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
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mNewsLetterListView.setLayoutManager(llm);

        mNewsLetterListView.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), mNewsLetterListView,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        if(list.get(position).getFilename().endsWith(".pdf")){
                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getFilename())));
                            }else{
                                Intent intent = new Intent(mContext, PDFViewActivity.class);
                                intent.putExtra("pdf_url", list.get(position).getFilename());
                                startActivity(intent);
                            }
//                            Intent intent = new Intent(mContext, PDFViewActivity.class);
//                            intent.putExtra("pdf_url", list.get(position).getFilename());
//                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
                            intent.putExtra("url",list.get(position).getFilename());
                            intent.putExtra("tab_type","Newsletters");
                            //intent.putExtra("tab_type",list.get(position).getSubmenu());
                            startActivity(intent);
                        }
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
    }
}
