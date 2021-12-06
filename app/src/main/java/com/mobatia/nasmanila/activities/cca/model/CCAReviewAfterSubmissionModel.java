package com.mobatia.nasmanila.activities.cca.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RIJO K JOSE on 25/1/17.
 */
public class CCAReviewAfterSubmissionModel implements Serializable {

    String day;
    String choice1;
    String choice2;
    ArrayList<CCAAttendanceModel>calendarDaysChoice1;
    ArrayList<CCAAttendanceModel>calendarDaysChoice2;
    String cca_item_start_time;
    String cca_item_end_time;
    String cca_item_description;
    String venue;

    public String getCca_item_description() {
        return cca_item_description;
    }

    public void setCca_item_description(String cca_item_description) {
        this.cca_item_description = cca_item_description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public ArrayList<CCAAttendanceModel> getCalendarDaysChoice1() {
        return calendarDaysChoice1;
    }

    public void setCalendarDaysChoice1(ArrayList<CCAAttendanceModel> calendarDaysChoice1) {
        this.calendarDaysChoice1 = calendarDaysChoice1;
    }

    public ArrayList<CCAAttendanceModel> getCalendarDaysChoice2() {
        return calendarDaysChoice2;
    }

    public void setCalendarDaysChoice2(ArrayList<CCAAttendanceModel> calendarDaysChoice2) {
        this.calendarDaysChoice2 = calendarDaysChoice2;
    }

    public String getCca_item_start_time() {
        return cca_item_start_time;
    }

    public void setCca_item_start_time(String cca_item_start_time) {
        this.cca_item_start_time = cca_item_start_time;
    }

    public String getCca_item_end_time() {
        return cca_item_end_time;
    }

    public void setCca_item_end_time(String cca_item_end_time) {
        this.cca_item_end_time = cca_item_end_time;
    }
}
