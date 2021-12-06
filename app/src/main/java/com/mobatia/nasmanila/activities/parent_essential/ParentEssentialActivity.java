package com.mobatia.nasmanila.activities.parent_essential;

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

import com.bumptech.glide.Glide;
import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.activities.parent_essential.adapter.ParentEssentialActivityListAdapter;
import com.mobatia.nasmanila.activities.pdf.PDFViewActivity;
import com.mobatia.nasmanila.activities.pdf.PdfReaderActivity;
import com.mobatia.nasmanila.activities.pdf.PdfReaderNextActivity;
import com.mobatia.nasmanila.activities.pdf.PdfViewActivityNew;
import com.mobatia.nasmanila.activities.web_view.LoadUrlWebViewActivity;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.NaisClassNameConstants;
import com.mobatia.nasmanila.constants.ResultConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.parent_essentials.model.ParentEssentialsModel;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;
import com.mobatia.nasmanila.recyclerviewmanager.DividerItemDecoration;
import com.mobatia.nasmanila.recyclerviewmanager.RecyclerItemListener;

import java.util.ArrayList;

/**
 * Created by gayatri on 23/3/17.
 */
public class ParentEssentialActivity extends Activity
        implements JSONConstants,URLConstants,ResultConstants,StatusConstants,NaisClassNameConstants {
    Bundle extras;
    ArrayList<ParentEssentialsModel> list;
    String tab_type;
    String tab_typeName;
    String bannerImage="";
    Context mContext=this;
    RecyclerView mNewsLetterListView;
    RelativeLayout relativeHeader;
    HeaderManager headermanager;
    ImageView back;
    ImageView home;
//    ViewPager bannerImagePager;
    ImageView bannerImagePager;
    ArrayList<String> bannerUrlImageArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_parent_essential_sublist);
        initUI();
        setListAdapter();


    }

    private void setListAdapter() {
        ParentEssentialActivityListAdapter newsDetailListAdapter=new ParentEssentialActivityListAdapter(mContext,list);
                mNewsLetterListView.setAdapter(newsDetailListAdapter);
    }

    private void initUI() {
        extras=getIntent().getExtras();
        if(extras!=null) {
            list = (ArrayList<ParentEssentialsModel>) extras.getSerializable("submenuArray");
            tab_type=extras.getString("tab_type");
            bannerImage=extras.getString("bannerImage");
            tab_typeName=extras.getString("tab_typeName");

        }
        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        bannerImagePager= (ImageView) findViewById(R.id.bannerImagePager);
//        bannerImagePager= (ViewPager) findViewById(R.id.bannerImagePager);
        if (!bannerImage.equalsIgnoreCase("")) {
//            bannerUrlImageArray = new ArrayList<>();
//            bannerUrlImageArray.add(bannerImage);
//            bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, mContext));
            Glide.with(mContext).load(AppUtils.replace(bannerImage)).centerCrop().placeholder(R.drawable.default_bannerr).error(R.drawable.default_bannerr).into(bannerImagePager);

        }
        else
        {
//            bannerImagePager.setBackgroundResource(R.drawable.demo);
            bannerImagePager.setImageResource(R.drawable.default_bannerr);

        }
        mNewsLetterListView = (RecyclerView) findViewById(R.id.mListView);
        mNewsLetterListView.setHasFixedSize(true);
        mNewsLetterListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
        headermanager = new HeaderManager(ParentEssentialActivity.this, tab_type);
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
                        if (list.size()<=1) {
                            if (list.get(position).getFilename().endsWith(".pdf")) {
                                Intent intent = new Intent(mContext, PdfReaderActivity.class);
                                intent.putExtra("pdf_url", list.get(position).getFilename());
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
                                intent.putExtra("url", list.get(position).getFilename());
                                intent.putExtra("tab_type",tab_type);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            if (list.get(position).getFilename().endsWith(".pdf") && tab_typeName.equalsIgnoreCase("Lunch Menu")) {
                                Intent intent = new Intent(mContext, PdfReaderNextActivity.class);
                                intent.putExtra("position", position);
                                intent.putExtra("submenuArray", list);
                                startActivity(intent);
                            }
                            else  if (list.get(position).getFilename().endsWith(".pdf")) {
                                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getFilename())));
                                }else{
                                    Intent intent = new Intent(mContext, PDFViewActivity.class);
                                    intent.putExtra("pdf_url", list.get(position).getFilename());
                                    startActivity(intent);
                                }
//                                Intent intent = new Intent(mContext, PDFViewActivity.class);
//                                intent.putExtra("pdf_url", list.get(position).getFilename());
//                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
                                intent.putExtra("url", list.get(position).getFilename());
                                intent.putExtra("tab_type",tab_type);
                                startActivity(intent);
                            }
                        }
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
    }
}
