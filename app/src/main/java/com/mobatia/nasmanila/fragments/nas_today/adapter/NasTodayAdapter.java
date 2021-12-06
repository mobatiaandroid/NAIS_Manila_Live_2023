/**
 *
 */
package com.mobatia.nasmanila.fragments.nas_today.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.nas_today.nas_today_detail.NasTodayDetailWebViewActivityNew;
import com.mobatia.nasmanila.constants.CacheDIRConstants;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.fragments.nas_today.model.NasTodayModel;
import com.mobatia.nasmanila.activities.nas_today.nas_today_detail.NasTodayDetailWebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Rijo K Jose
 */
public class NasTodayAdapter extends BaseAdapter implements
        CacheDIRConstants, IntentPassValueConstants {

    private Context mContext;
    //	private ArrayList<AboutUsModel> mAboutusList;
    private ArrayList<NasTodayModel> mNasTodayList;
    private View view;
    private TextView mTitleTxt;
    private ImageView mImageView;
    private String mTitle;
    private String mTabId;
    ViewHolder mViewHolder;

    //	public CustomAboutUsAdapter(Context context,
//								ArrayList<AboutUsModel> arrList, String title, String tabId) {
//		this.mContext = context;
//		this.mAboutusList = arrList;
//		this.mTitle = title;
//		this.mTabId = tabId;
//	}
    public NasTodayAdapter(Context context,
                           ArrayList<NasTodayModel> mNasTodayList, String title, String tabId) {
        this.mContext = context;
        this.mNasTodayList = mNasTodayList;
        this.mTitle = title;
        this.mTabId = tabId;
    }

    public NasTodayAdapter(Context context,
                           ArrayList<NasTodayModel> arrList) {
        this.mContext = context;
        this.mNasTodayList = arrList;

    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mNasTodayList.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mNasTodayList.get(position);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflate = LayoutInflater.from(mContext);
            mViewHolder = new ViewHolder();
            convertView = inflate.inflate(R.layout.custom_nastoday_list_adapter, null);
            mViewHolder.listTxtTitle = (TextView) convertView.findViewById(R.id.listTxtTitle);
            mViewHolder.listTxtDate = (TextView) convertView.findViewById(R.id.listTxtDate);
            mViewHolder.listTxtDescription = (TextView) convertView.findViewById(R.id.listTxtDescription);
            mViewHolder.readMoreTextView = (TextView) convertView.findViewById(R.id.readMoreTextView);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            mViewHolder.listTxtTitle.setText(mNasTodayList.get(position).getTitle().trim());
            mViewHolder.listTxtDescription.setText(mNasTodayList.get(position).getDescription().trim());
            mViewHolder.listTxtDate.setText(mNasTodayList.get(position).getDay() + "-" + mNasTodayList.get(position).getMonth() + "-" + mNasTodayList.get(position).getYear() + " " + mNasTodayList.get(position).getTime());
            mViewHolder.readMoreTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String webViewComingUpDetail = "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head><meta name=\"viewport\" content=\"user-scalable=no, \n" +
                            "initial-scale=1.0, maximum-scale=1.0, width=device-width\">" +
                            "<style>\n" +
                            "\n" +
                            "@font-face {\n" +
                            "font-family: SourceSansPro-Semibold;" +
                            "src: url(SourceSansPro-Semibold.ttf);" +

                            "font-family: SourceSansPro-Regular;" +
                            "src: url(SourceSansPro-Regular.ttf);" +
                            "}" +
                            ".title {" +
                            "font-family: SourceSansPro-Regular;" +
                            "font-size:16px;" +
                            "text-align:left;" +
                            "color:	#46C1D0;" +
                            "}" +
                            "a {" +
                            "font-family: SourceSansPro-Regular;" +
                            "color:	#46C1D0;"+
                            "}" +
                            ".date {" +
                            "font-family: SourceSansPro-Regular;" +
                            "font-size:12px;" +
                            "text-align:left;" +
                            "color:	#46C1D0;" +
                            "}" +
                            ".description {"+
                            "font-family: SourceSansPro-Light;"+
                            "src: url(SourceSansPro-Light.otf);"+
                            "font-size:14px;" +
                            "word-wrap:break-word;"+
                            "color: #808080;" +//black removed
                            "}" +
                            "</style>\n" + "</head>" +
                            "<body>" +
                            "<p class='title'>" + mNasTodayList.get(position).getTitle() + "</p>" +
                            "<p class='date'>" + mNasTodayList.get(position).getDay() + "-" + mNasTodayList.get(position).getMonth() + "-" + mNasTodayList.get(position).getYear() +
                            " " + mNasTodayList.get(position).getTime() + "</p>";
                    if (!mNasTodayList.get(position).getImage().equalsIgnoreCase("")) {
                        webViewComingUpDetail = webViewComingUpDetail + "<center><img src='" + mNasTodayList.get(position).getImage() + "'width='100%', height='auto'></center>";
                    }
                  String desc="";
                    desc=mNasTodayList.get(position).getDescription().replaceAll(" ","&nbsp;");
                            List<String> pullLinks=new ArrayList<>();
                    pullLinks=pullLinks( mNasTodayList.get(position).getDescription());
                    if (pullLinks.size()>0) {
                        for (int i = 0; i < pullLinks.size(); i++) {
                            desc = desc.replaceAll(pullLinks.get(i), "<a href=\"" + pullLinks.get(i)+ "\">" + pullLinks.get(i)+ " </a>");

                        }
                    }
                    else
                    {
                        desc=mNasTodayList.get(position).getDescription().replaceAll(" ","&nbsp;");
                    }
                    System.out.println("decription::"+desc);
                    webViewComingUpDetail = webViewComingUpDetail+"<p class='description'>" + desc.replaceAll("\n","<br>")  + "</p>" +
                            "</body>\n</html>";
                    Log.e("webviewComingupDetail", webViewComingUpDetail.toString());
                    Intent mIntent = new Intent(mContext, NasTodayDetailWebViewActivityNew.class);
                    System.out.println("result is::"+mNasTodayList.get(position).getImage() );
                    mIntent.putExtra("webViewComingDetail", webViewComingUpDetail);
                   Log.v("detail:: ",webViewComingUpDetail);
                   Log.v("desc:: ",mNasTodayList.get(position).getDescription());
                    mIntent.putExtra("pdf", mNasTodayList.get(position).getPdf());
                    mContext.startActivity(mIntent);
                }
            });
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
    public List<String> pullLinks(String text)
    {
//        String links ="";
        List<String> links = new ArrayList<String>();

        //String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        String regex = "\\(?\\b(https?://|http?://|www[.]|ftp://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);

        while(m.find())
        {
            String urlStr = m.group();

            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }

//            links=urlStr;
            if (!(links.contains(urlStr)))
            links.add(urlStr);

        }

        return links;
    }

    class ViewHolder {
        TextView listTxtTitle;
        TextView listTxtDate;
        TextView listTxtDescription;
        TextView readMoreTextView;

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
