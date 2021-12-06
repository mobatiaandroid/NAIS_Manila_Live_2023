/**
 * 
 */
package com.mobatia.nasmanila.activities.notifications;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.activities.home.HomeListAppCompatActivity;
import com.mobatia.nasmanila.constants.IntentPassValueConstants;
import com.mobatia.nasmanila.constants.JSONConstants;
import com.mobatia.nasmanila.constants.StatusConstants;
import com.mobatia.nasmanila.constants.URLConstants;
import com.mobatia.nasmanila.fragments.notifications.model.PushNotificationModel;
import com.mobatia.nasmanila.fragments.notifications_new.model.PushNotificationModelNew;
import com.mobatia.nasmanila.manager.AppUtils;
import com.mobatia.nasmanila.manager.HeaderManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author rahul
 * 
 */

public class TextalertActivity extends Activity implements
		 OnClickListener,IntentPassValueConstants, JSONConstants, URLConstants, StatusConstants {

	TextView txtmsg;
	TextView mDateTv;
	ImageView img;
	ImageView home;
	Bundle extras;
	ArrayList<PushNotificationModelNew> imglist;
	int position;
	String date;
//	PhotosManager photosManager;
	Context context = this;
	Activity mActivity;
	RelativeLayout header;
//	HeaderManager headermanager;
	ImageView back;
	RelativeLayout relativeHeader;
	HeaderManager headermanager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_push);
		mActivity = this;
		extras = getIntent().getExtras();
		if (extras != null) {
			position = extras.getInt(POSITION);

			imglist = (ArrayList<PushNotificationModelNew>) extras
					.getSerializable(PASS_ARRAY_LIST);

		}


			initialiseUI();


	}

	private void initialiseUI() {



		img = (ImageView) findViewById(R.id.image);
		txtmsg = (TextView) findViewById(R.id.txt);
		mDateTv = (TextView) findViewById(R.id.mDateTv);
		relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
		headermanager = new HeaderManager(mActivity, "Notification");
		headermanager.getHeader(relativeHeader, 0);
		back = headermanager.getLeftButton();
		headermanager.setButtonLeftSelector(R.drawable.back,
				R.drawable.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		home = headermanager.getLogoButton();
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(mActivity, HomeListAppCompatActivity.class);
				in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
			}
		});
		date = AppUtils.dateParsingToDdMmmYyyy(imglist.get(position).getDate());


//		photosManager = new PhotosManager(context, ALERT_COVER_PATH,
//				(int) context.getResources().getDimension(
//						R.dimen.coverimage_width), (int) context.getResources()
//						.getDimension(R.dimen.coverimage_height), 0);
		setDetails();

	}

	private void setDetails() {
		txtmsg.setText(Html.fromHtml((imglist.get(position).getMessage().replaceAll("\n","<br>")).replaceAll(" ","&nbsp;")));
		mDateTv.setText(date);
//		if (!(imglist.get(position).getPushDetail().toString()
//				.equalsIgnoreCase(""))) {
//			txtmsg.setText(imglist.get(position).getPushDetail());
//		} else {
//			txtmsg.setText(imglist.get(position).getPushTitle());
//		}
	}

	@Override
	public void onClick(View v) {

	}

}
