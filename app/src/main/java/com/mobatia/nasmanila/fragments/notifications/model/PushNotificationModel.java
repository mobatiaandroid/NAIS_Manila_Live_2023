/**
 * 
 */
package com.mobatia.nasmanila.fragments.notifications.model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc


/**
 * @author Rijo
 *
 */
public class PushNotificationModel implements Serializable {

	String monthString;
	String monthNumber;
	String year;
	String dayOfTheWeek ;
	String day ;
	String headTitle;
	String id;
	String Alert_type;

	public String getHeadTitle() {
		return headTitle;
	}

	public void setHeadTitle(String headTitle) {
		this.headTitle = headTitle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlert_type() {
		return Alert_type;
	}

	public void setAlert_type(String alert_type) {
		Alert_type = alert_type;
	}

	public String getPushTime() {
		return pushTime;
	}

	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}

	String pushTime ;
	public String getPushTitle() {
		return pushTitle;
	}

	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}

	String pushTitle;
	String pushDetail;

	public String getPushDate() {
		return pushDate;
	}

	public void setPushDate(String pushDate) {
		this.pushDate = pushDate;
	}

	String pushDate;

	public String getPushUrl() {
		return pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	String pushUrl;

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	String pushType;

	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonthString() {
		return monthString;
	}

	public void setMonthString(String monthString) {
		this.monthString = monthString;
	}

	public String getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(String monthNumber) {
		this.monthNumber = monthNumber;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}



	public String getPushDetail() {
		return pushDetail;
	}

	public void setPushDetail(String pushDetail) {
		this.pushDetail = pushDetail;
	}


}