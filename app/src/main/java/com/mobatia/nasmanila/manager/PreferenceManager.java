/**
 *
 */
package com.mobatia.nasmanila.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobatia.nasmanila.R;
import com.mobatia.nasmanila.constants.NaisTabConstants;


/**
 * @author Rijo K Jose
 *
 */
public class PreferenceManager implements NaisTabConstants {
    public static String SHARED_PREF_NAS = "NAS_MANILA";

    public static void setIsFirstLaunch(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_launch", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getIsFirstLaunch(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_launch", true);
    }

    public static void setCalendarEventNames(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cal_event", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getCalendarEventNames() Description : get calendar event
     * names Parameters : context Return type : String Date : 23-Jan-2015 Author
     * : Rijo K Jose
     *****************************************************/
    public static String getCalendarEventNames(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("cal_event", "");
    }

    //save access token
    public static void setAccessToken(Context mContext, String accesstoken) {
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREF_NAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", accesstoken);
        editor.commit();
    }

    //get accesstoken
    public static String getAccessToken(Context mContext) {
        String tokenValue = "";
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        tokenValue = prefs.getString("access_token", "0");
        return tokenValue;
    }

    public static String getUserName(Context context) {
        String userName = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        userName = prefs.getString("username", "");
        return userName;
    }

    /*******************************************************
     * Method name : setUserName Description : get user name after login
     * Parameters : context, username Return type : void Date : Oct 28, 2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setUserName(Context context, String userName) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", userName);
        editor.commit();
    }

    public static int getButtonOneBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_one_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonOneBg() Description : set home button one bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonOneBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_one_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonOneTextImage() Description : get home button one
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonOneTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_pos", TAB_NAS_TODAY);//21

    }

    /*******************************************************
     * Method name : setButtonOneTextImage() Description : set home button one
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonOneTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_one_pos", TAB_NAS_TODAY);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonOneTabId() Description : get home button one tab
     * id Parameters : context Return type : String Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static String getButtonOneTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_tab", TAB_NAS_TODAY);
    }

    /*******************************************************
     * Method name : getButtonOneTabId() Description : set home button one tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonOneTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_one_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonTwoBg() Description : get home button two bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonTwoBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_two_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonTwoBg() Description : set home button two bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_two_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonTwoTextImage() Description : get home button two
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonTwoTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_two_pos", "4");

    }

    /*******************************************************
     * Method name : setButtonTwoTextImage() Description : set home button two
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_two_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonTwoTabId() Description : get home button two tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static String getButtonTwoTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_two_tab", TAB_PARENT_ESSENTIALS_REG);
    }

    /*******************************************************
     * Method name : setButtonTwoTabId() Description : set home button two tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_two_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonThreeBg() Description : get home button three bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonThreeBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_three_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonThreeBg() Description : set home button three bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_three_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonThreeTextImage() Description : get home button
     * three text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonThreeTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_three_pos", "7");//19

    }

    /*******************************************************
     * Method name : setButtonThreeTextImage() Description : set home button
     * three text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_three_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonThreeTabId() Description : get home button three
     * tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static String getButtonThreeTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_three_tab", TAB_SOCIAL_MEDIA_REG);
    }

    /*******************************************************
     * Method name : setButtonThreeTabId() Description : set home button three
     * tab id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_three_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFourBg() Description : get home button four bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonFourBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_four_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonFourBg() Description : set home button four bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonFourBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_four_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFourTextImage() Description : get home button four
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonFourTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_four_pos", "9");

    }

    /*******************************************************
     * Method name : setButtonFourTextImage() Description : set home button four
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_four_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonFourTabId() Description : get home button four tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static String getButtonFourTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_four_tab", TAB_CONTACT_US_REG);
    }

    /*******************************************************
     * Method name : setButtonFourTabId() Description : set home button four tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_four_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFiveBg() Description : get home button five bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonFiveBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_five_color",
                context.getResources().getColor(R.color.rel_five));

    }

    /*******************************************************
     * Method name : setButtonFiveBg() Description : set home button five bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_five_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFiveTextImage() Description : get home button five
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonFiveTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_five_pos", "5");

    }

    /*******************************************************
     * Method name : setButtonFiveTextImage() Description : set home button five
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_five_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonFiveTabId() Description : get home button five tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static String getButtonFiveTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_five_tab", TAB_PROGRAMMES_REG);
    }

    /*******************************************************
     * Method name : setButtonFiveTabId() Description : set home button five tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_five_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSixBg() Description : get home button six bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonSixBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_six_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonSixBg() Description : set home button six bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonSixBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_six_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSixImage() Description : get home button six text
     * and image Parameters : context Return type : String Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static String getButtonSixTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_six_pos", "2");

    }

    /*******************************************************
     * Method name : setButtonSixTextImage() Description : set home button six
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSixTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_six_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonSixTabId() Description : get home button six tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static String getButtonSixTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_six_tab", TAB_NOTIFICATIONS_REG);
    }

    /*******************************************************
     * Method name : setButtonSixTabId() Description : set home button six tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSixTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_six_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSevenBg() Description : get home button seven bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonSevenBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_seven_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonSevenBg() Description : set home button seven bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_seven_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSevenTextImage() Description : get home button
     * seven text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonSevenTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_seven_pos", "3");

    }

    /*******************************************************
     * Method name : setButtonSevenTextImage() Description : set home button
     * seven text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_seven_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonSevenTabId() Description : get home button seven
     * tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static String getButtonSevenTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_seven_tab",TAB_ABSENCES_REG);
    }

    /*******************************************************
     * Method name : setButtonSevenTabId() Description : set home button seven
     * tab id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_seven_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonEightBg() Description : get home button eight bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonEightBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_eight_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonEightBg() Description : set home button eight bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonEightBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_eight_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonEightTextImage() Description : get home button
     * eight text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonEightTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_eight_pos", "1");//19

    }

    /*******************************************************
     * Method name : setButtonEightTextImage() Description : set home button
     * eight text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_eight_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonEightTabId() Description : get home button eight
     * tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static String getButtonEightTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_eight_tab", TAB_CALENDAR_REG);
    }

    /*******************************************************
     * Method name : setButtonEightTabId() Description : set home button eight
     * tab id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_eight_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonNineBg() Description : get home button nine bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonNineBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_nine_color",
                context.getResources().getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonNineBg() Description : set home button nine bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonNineBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_nine_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonNineTextImage() Description : get home button nine
     * text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonNineTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_nine_pos", "6");

    }

    /*******************************************************
     * Method name : setButtonNineTextImage() Description : set home button nine
     * text and image Parameters : context, position Return type : void Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineTextImage(Context context, String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_nine_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonNineTabId() Description : get home button nine tab
     * id Parameters : context Return type : String Date : 25-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static String getButtonNineTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_nine_tab", TAB_PARENTS_MEETING_REG);
    }

    /*******************************************************
     * Method name : setButtonNineTabId() Description : set home button nine tab
     * id Parameters : context, TAB_ID Return type : void Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_nine_tab", TAB_ID);
        editor.commit();
    }

    public static boolean getIfHomeItemClickEnabled(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("home_item_click", true);

    }

    /*******************************************************
     * Method name : setIfHomeItemClickEnabled() Description : set if home list
     * item click is enabled Parameters : context, result Return type : void
     * Date : 11-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setIfHomeItemClickEnabled(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("home_item_click", result);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonOneGuestBg() Description : get home button one bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonOneGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_one_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonOneGuestBg() Description : set home button one bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonOneGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_one_guest_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonOneGuestTextImage() Description : get home button
     * one text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonOneGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_guest_pos",TAB_NAS_TODAY);

    }

    /*******************************************************
     * Method name : setButtonOneGuestTextImage() Description : set home button
     * one text and image Parameters : context, position Return type : void Date
     * : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonOneGuestTextImage(Context context,
                                                  String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_one_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonOneGuestTabId() Description : get home button one
     * guest tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static String getButtonOneGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_one_guest_tab", TAB_NAS_TODAY);
    }

    /*******************************************************
     * Method name : setButtonOneGuestTabId() Description : set home button one
     * guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonOneGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_one_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonTwoGuestBg() Description : get home button two bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonTwoGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_two_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonTwoGuestBg() Description : set home button two bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_two_guest_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonTwoGuestTextImage() Description : get home button
     * two text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonTwoGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_two_guest_pos", TAB_SOCIAL_MEDIA_GUEST);

    }

    /*******************************************************
     * Method name : setButtonTwoGuestTextImage() Description : set home button
     * two text and image Parameters : context, position Return type : void Date
     * : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoGuestTextImage(Context context,
                                                  String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_two_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonTwoGuestTabId() Description : get home button two
     * guest tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static String getButtonTwoGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_two_guest_tab",TAB_SOCIAL_MEDIA_GUEST);
    }

    /*******************************************************
     * Method name : setButtonTwoGuestTabId() Description : set home button two
     * guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonTwoGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_two_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonThreeGuestBg() Description : get home button three
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonThreeGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_three_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonThreeGuestBg() Description : set home button three
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_three_guest_color", color);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonThreeGuestTextImage() Description : get home
     * button three text and image Parameters : context Return type : String
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonThreeGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_three_guest_pos", TAB_PARENT_ESSENTIALS_GUEST);

    }

    /*******************************************************
     * Method name : setButtonThreeGuestTextImage() Description : set home
     * button three text and image Parameters : context, position Return type :
     * void Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeGuestTextImage(Context context,
                                                    String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_three_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonThreeGuestTabId() Description : get home button
     * three guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonThreeGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_three_guest_tab", TAB_PARENT_ESSENTIALS_GUEST);
    }

    /*******************************************************
     * Method name : setButtonThreeGuestTabId() Description : set home button
     * three guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonThreeGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_three_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFourGuestBg() Description : get home button four
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonFourGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_four_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonFourGuestBg() Description : set home button four
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_four_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFourGuestTextImage() Description : get home button
     * four text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonFourGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_four_guest_pos", TAB_SETTINGS);

    }

    /*******************************************************
     * Method name : setButtonFourGuestTextImage() Description : set home button
     * four text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourGuestTextImage(Context context,
                                                   String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_four_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonFourGuestTabId() Description : get home button
     * four guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonFourGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_four_guest_tab", TAB_SETTINGS);
    }

    /*******************************************************
     * Method name : setButtonFourGuestTabId() Description : set home button
     * four guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFourGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_four_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFiveGuestBg() Description : get home button five
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonFiveGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_five_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonFiveGuestBg() Description : set home button five
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_five_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonFiveGuestTextImage() Description : get home button
     * five text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonFiveGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_five_guest_pos", TAB_NOTIFICATIONS_GUEST);

    }

    /*******************************************************
     * Method name : setButtonFiveGuestTextImage() Description : set home button
     * five text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveGuestTextImage(Context context,
                                                   String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_five_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonFiveGuestTabId() Description : get home button
     * five guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonFiveGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_five_guest_tab", TAB_NOTIFICATIONS_GUEST);
    }

    /*******************************************************
     * Method name : setButtonFiveGuestTabId() Description : set home button
     * five guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonFiveGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_five_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSixGuestBg() Description : get home button six bg
     * Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonSixGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_six_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonSixGuestBg() Description : set home button six bg
     * Parameters : context, color Return type : void Date : 07-Nov-2014 Author
     * : Rijo K Jose
     *****************************************************/
    public static void setButtonSixGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_six_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSixGuestTextImage() Description : get home button
     * six text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonSixGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_six_guest_pos", TAB_PROGRAMMES_GUEST);

    }

    /*******************************************************
     * Method name : setButtonSixGuestTextImage() Description : set home button
     * six text and image Parameters : context, position Return type : void Date
     * : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSixGuestTextImage(Context context,
                                                  String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_six_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonSixGuestTabId() Description : get home button six
     * guest tab id Parameters : context Return type : String Date : 25-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static String getButtonSixGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_six_guest_tab", TAB_PROGRAMMES_GUEST);
    }

    /*******************************************************
     * Method name : setButtonSixGuestTabId() Description : set home button six
     * guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSixGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_six_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSevenGuestBg() Description : get home button seven
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonSevenGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_seven_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonSevenGuestBg() Description : set home button seven
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_seven_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonSevenGuestTextImage() Description : get home
     * button seven text and image Parameters : context Return type : String
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonSevenGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_seven_guest_pos", TAB_CONTACT_US_GUEST);

    }

    /*******************************************************
     * Method name : setButtonSevenGuestTextImage() Description : set home
     * button seven text and image Parameters : context, position Return type :
     * void Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenGuestTextImage(Context context,
                                                    String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_seven_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonSevenGuestTabId() Description : get home button
     * seven guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonSevenGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_seven_guest_tab", TAB_CONTACT_US_GUEST);
    }

    /*******************************************************
     * Method name : setButtonSixGuestTabId() Description : set home button
     * seven guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonSevenGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_seven_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonEightGuestBg() Description : get home button eight
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonEightGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_eight_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonEightGuestBg() Description : set home button eight
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_eight_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonEightGuestTextImage() Description : get home
     * button eight text and image Parameters : context Return type : String
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonEightGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_eight_guest_pos", TAB_ABOUT_US_GUEST);//TAB_ABOUT_US

    }

    /*******************************************************
     * Method name : setButtonEightGuestTextImage() Description : set home
     * button eight text and image Parameters : context, position Return type :
     * void Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightGuestTextImage(Context context,
                                                    String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_eight_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonEightGuestTabId() Description : get home button
     * eight guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonEightGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_eight_guest_tab", TAB_ABOUT_US_GUEST);
    }

    /*******************************************************
     * Method name : setButtonEightGuestTabId() Description : set home button
     * eight guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonEightGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_eight_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonNineGuestBg() Description : get home button nine
     * bg Parameters : context Return type : int Date : 07-Nov-2014 Author :
     * Rijo K Jose
     *****************************************************/
    public static int getButtonNineGuestBg(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getInt("btn_nine_guest_color", context.getResources()
                .getColor(R.color.transparent));

    }

    /*******************************************************
     * Method name : setButtonNineGuestBg() Description : set home button nine
     * bg Parameters : context, color Return type : void Date : 07-Nov-2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineGuestBg(Context context, int color) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("btn_nine_guest_color", color);
        editor.commit();
    }

    /*******************************************************
     * Method name : getButtonNineGuestTextImage() Description : get home button
     * nine text and image Parameters : context Return type : String Date :
     * 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonNineGuestTextImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_nine_guest_pos", TAB_LOGOUT_GUEST);

    }

    /*******************************************************
     * Method name : setButtonNineGuestTextImage() Description : set home button
     * nine text and image Parameters : context, position Return type : void
     * Date : 07-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineGuestTextImage(Context context,
                                                   String position) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_nine_guest_pos", position);
        editor.commit();

    }

    /*******************************************************
     * Method name : getButtonNineGuestTabId() Description : get home button
     * nine guest tab id Parameters : context Return type : String Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static String getButtonNineGuestTabId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("btn_nine_guest_tab", TAB_LOGOUT_GUEST);
    }

    /*******************************************************
     * Method name : setButtonNineGuestTabId() Description : set home button
     * nine guest tab id Parameters : context, TAB_ID Return type : void Date :
     * 25-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static void setButtonNineGuestTabId(Context context, String TAB_ID) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("btn_nine_guest_tab", TAB_ID);
        editor.commit();
    }

    /*******************************************************
     * Method name : getCacheClearStatusForShop() Description : get cache clear
     * status for shop Parameters : context Return type : boolean Date :
     * 24-Nov-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getCacheClearStatusForShop(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("shop_clear_cache", true);

    }

    public static void setFireBaseId(Context context, String id) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("firebase id", id);
        editor.commit();
    }

    public static String getFireBaseId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("firebase id", "0");

    }

    public static String getUserId(Context context) {
        String userid = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        userid = prefs.getString("userid", "");
        return userid;
    }

    /*******************************************************
     * Method name : setUserName Description : get user name after login
     * Parameters : context, username Return type : void Date : Oct 28, 2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setUserId(Context context, String userid) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userid", userid);
        editor.commit();
    }

    public static String getUserEmail(Context context) {
        String userid = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        userid = prefs.getString("user_email", "");
        return userid;
    }
    public static void setLeaveStudentId(Context context, String mLeaveStudentId) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LeaveStudentId", mLeaveStudentId);
        editor.commit();
    }

    public static String getLeaveStudentId(Context context) {
        String mLeaveStudentId = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mLeaveStudentId = prefs.getString("LeaveStudentId", "");
        return mLeaveStudentId;
    }
    public static void setLeaveStudentName(Context context, String mLeaveStudentName) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LeaveStudentName", mLeaveStudentName);
        editor.commit();
    }

    public static String getLeaveStudentName(Context context) {
        String mLeaveStudentName = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mLeaveStudentName = prefs.getString("LeaveStudentName", "");
        return mLeaveStudentName;
    }
    /*******************************************************
     * Method name : setUserName Description : get user name after login
     * Parameters : context, username Return type : void Date : Oct 28, 2014
     * Author : Rijo K Jose
     *****************************************************/
    public static void setUserEmail(Context context, String userid) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_email", userid);
        editor.commit();
    }

    public static void setIsFirstTimeInPA(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_pa", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getIsFirstTimeInPA(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_pa", true);
    }

    public static void setIsFirstTimeInPE(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_pe", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getIsFirstTimeInPE(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_pe", true);
    }

    public static void setBackParent(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("BackParent", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getIBackParent(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("BackParent", true);
    }

    public static void setIsFirstTimeInCalendar(Context context, boolean result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_first_calendar", result);
        editor.commit();
    }

    /*******************************************************
     * Method name : getIsFirstLaunch() Description : get if app is launching
     * for first time Parameters : context Return type : boolean Date :
     * 11-Dec-2014 Author : Rijo K Jose
     *****************************************************/
    public static boolean getIsFirstTimeInCalendar(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getBoolean("is_first_calendar", true);
    }

    public static void setStudIdForCCA(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StudIdForCCA", result);
        editor.commit();
    }

    //getStudIdForCCA
    public static String getStudIdForCCA(Context context) {
        String StudIdForCCA = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        StudIdForCCA = prefs.getString("StudIdForCCA", "");
        return StudIdForCCA;
    }


    public static void setStudNameForCCA(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StudNameForCCA", result);
        editor.commit();
    }

    public static String getStudNameForCCA(Context context) {
        String StudNameForCCA = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        StudNameForCCA = prefs.getString("StudNameForCCA", "");
        return StudNameForCCA;
    }

    public static void setStudClassForCCA(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StudClassForCCA", result);
        editor.commit();
    }

    public static String getStudClassForCCA(Context context) {
        String StudClassForCCA = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        StudClassForCCA = prefs.getString("StudClassForCCA", "");
        return StudClassForCCA;
    }

    public static void setCCATitle(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("CCATitle", result);
        editor.commit();
    }

    public static String getCCATitle(Context context) {
        String CCATitle = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        CCATitle = prefs.getString("CCATitle", "");
        return CCATitle;
    }


    public static void setCCAItemId(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("CCAItemId", result);
        editor.commit();
    }

    public static String getCCAItemId(Context context) {
        String CCAItemId = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        CCAItemId = prefs.getString("CCAItemId", "");
        return CCAItemId;
    }


    public static void setCCAStudentIdPosition(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("CCAStudentIdPosition", result);
        editor.commit();
    }

    public static String getCCAStudentIdPosition(Context context) {
        String CCAStudentIdPosition = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        CCAStudentIdPosition = prefs.getString("CCAStudentIdPosition", "");
        return CCAStudentIdPosition;
    }
    public static void setGoToSettings(Context context, String mGoToSetting) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("GoToSetting", mGoToSetting);
        editor.commit();
    }

    public static String getGoToSettings(Context context) {
        String mGoToSetting = "";
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        mGoToSetting = prefs.getString("GoToSetting", "0");
        return mGoToSetting;
    }

    /*********** Force Update **********/
    public static void setVersionFromApi(Context context, String result) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("version_api", result);
        editor.commit();
    }

    public static String getVersionFromApi(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAS,
                Context.MODE_PRIVATE);
        return prefs.getString("version_api", "");
    }


}

