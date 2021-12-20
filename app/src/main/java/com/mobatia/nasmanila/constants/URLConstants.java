/**
 * 
 */
package com.mobatia.nasmanila.constants;

/**
 * @author RIJO K JOSE
 * 
 */
public interface URLConstants {
	//Mobicare NAIS below//
	/*public String POST_APITOKENURL="http://mobicare2.mobatia.com/nais_manila/oauth/access_token";
	public String URL_HEAD = "http://mobicare2.mobatia.com/nais_manila/api/";*/
/*	public String POST_APITOKENURL="http://192.168.0.194/nais_manila/oauth/access_token";
	public String URL_HEAD = "http://192.168.0.194/nais_manila/api/";*/

//Dev NAIS MANILA

////	public String POST_APITOKENURL="http://mobicare2.mobatia.com/nais_manila/oauth/access_token";
//	public String POST_APITOKENURL="http://alpha.mobatia.in:808/nais_manila/oauth/access_token";
//////	public String URL_HEAD = "http://mobicare2.mobatia.com/nais_manila/api/";
//	public String URL_HEAD = "http://alpha.mobatia.in:808/nais_manila/api";


//	public String POST_APITOKENURL="http://manila.mobatia.in:8081/oauth/access_token";
//	public String URL_HEAD = "http://manila.mobatia.in:8081/api/v1";


//Live NAIS MANILA
	public String POST_APITOKENURL="https://cms.naismanila.edu.ph/nais_manila/oauth/access_token";
    public String URL_HEAD = "https://cms.naismanila.edu.ph/nais_manila/api/";

	public String URL_ABOUTUS_LIST = URL_HEAD
			+ "about_us";
	public String URL_DEVICE_REGISTRATION = URL_HEAD
			+ "deviceregistration";
	public String URL_TERMS_OF_SERVICE = URL_HEAD
			+ "terms_of_service";
	public String URL_EARLY_YEARS_LIST = URL_HEAD
			+ "departmentearly";
	public String URL_PRIMARY_LIST = URL_HEAD
			+ "departmentprimary";
	public String URL_SECONDARY_LIST = URL_HEAD
			+ "departmentsecondary";
	public String URL_IB_PROGRAMMME_LIST = URL_HEAD
			+ "department_ib_programms";
	public String URL_TERM_CALENDAR_LIST = URL_HEAD
			+ "term_calender";
	public String URL_CLASS_REPRESENTATIVE_LIST = URL_HEAD
			+ "class_representative";
	public String URL_CHATTER_BOX_LIST = URL_HEAD
			+ "chatter_box";
	public String URL_HOME_BANNER = URL_HEAD
			+ "home_banner_images";
	public String URL_GET_PHOTOS_LIST = URL_HEAD
			+ "photos";

	public String URL_GET_VIDEOS_LIST = URL_HEAD
			+ "videos";
	public String URL_PTA_CONFIRMATION = URL_HEAD
			+ "pta_confirmation";
	public String URL_GET_PTA_REVIEW_LIST = URL_HEAD
			+ "pta_reviewlist";
	public String URL_GET_THUM_NAIL_IMAGE_LIST = URL_HEAD
			+ "thumbnailimages";

	public String URL_STAFFDIRECTORY_LIST=URL_HEAD+"getstaffcategorylist";
	public String URL_EARLY_COMING_UP_LIST=URL_HEAD+"early_coming_up";
	public String URL_PRIMARY_COMING_UP_LIST=URL_HEAD+"primary_coming_up";
	public String URL_SECONDARY_COMING_UP_LIST=URL_HEAD+"secondary_coming_up";
	public String URL_IB_PROGRAMME_COMING_UP_LIST=URL_HEAD+"ib_programmes_coming_up";
	public String URL_PERFORMING_ARTS_LIST=URL_HEAD+"performing_arts";
	public String URL_CCAS_LIST=URL_HEAD+"cca";
	public String URL_SPORTS_PDF=URL_HEAD+"sports_pdf";
	public String URL_NAS_TODAY_LIST=URL_HEAD+"nastoday";
	public String URL_LOGOUT=URL_HEAD+"logout";
	public String URL_NAE_PROGRAMMES_LIST=URL_HEAD+"nae_programme";
	public String URL_GETSTAFFLDEPT_LIST=URL_HEAD+"getstaffdeptlist";
	public String URL_GET_NOTICE_LIST=URL_HEAD+"notice";
	public String URL_GET_NOTICATIONS_LIST=URL_HEAD+"notifications";
	public String URL_GET_ABOUTUS_LIST=URL_HEAD+"about_us";
	public String URL_CLEAR_BADGE=URL_HEAD+"clear_badge";
	public String URL_GET_NOTICATIONS_MESSAGE=URL_HEAD+"getnotification_details";


	public String URL_COMMUNICATION_BANNER = URL_HEAD
			+ "communication_banner_images";

	public String URL_GET_CALENDAR_LIST=URL_HEAD+"calender";
	public String URL_GET_CONTACTUS=URL_HEAD+"contact_us";
	public String URL_GET_NEWSLETTER_CATEGORY=URL_HEAD+"newsletter_categories";
	public String URL_GET_NEWSLETTERS=URL_HEAD+"newsletter";
	public String URL_PARENT_ESSENTIALS=URL_HEAD+"parents_essentials";
	public String URL_GET_COMMUNICATIONS_EVENTS=URL_HEAD+"events";
	public String URL_GET_SOCIALMEDIA_LIS=URL_HEAD+"social_media";
	public String URL_GET_SPORTSEVENT_LIST=URL_HEAD+"sportseventlist";
	public String URL_GET_SPORTS_EVENTS_LISTDETAILS=URL_HEAD+"sportseventdetails";
	public String URL_GET_STUDENT_LIST=URL_HEAD+"studentlist";
	public String URL_GET_PTA_ALLOTTED_LIST=URL_HEAD+"ptaallotteddates";
	public String URL_GET_PARENT_ASSOCIATION_EVENT_LIST=URL_HEAD+"parent_assoc_events";
	public String URL_GET_PTA_TIME_SLOT_LIST=URL_HEAD+"listingpta";
	public String URL_BOOK_PTA_INSERT_TIME_SLOT=URL_HEAD+"insertpta";
	public String URL_BOOK_PTA_TIME_SLOT=URL_HEAD+"parent_assoc_events_attending_or_not";
	public String URL_BOOK_PTA_TIME_SLOT_NEW=URL_HEAD+"parent_assoc_events_attending_or_not_new";
	public String URL_GET_STAFF_LIST_ACCORDING_TO_STUDENT=URL_HEAD+"stafflist";
	public String URL_SEND_EMAIL_TO_STAFF=URL_HEAD+"sendemail";
	public String URL_SEND_EMAIL_TO_STAFF_PTA=URL_HEAD+"pta_meeting";
	public String URL_PARENT_SIGNUP=URL_HEAD+"parent_signup";
	public String URL_LOGIN=URL_HEAD+"login";
	public String URL_CHANGEPSAAWORD=URL_HEAD+"changepassword";
	public String URL_FORGOTPASSWORD=URL_HEAD+"forgotpassword";
	public String URL_UPDATE_EVENTGOINGSTATUS=URL_HEAD+"sports_event_goingstatus";
	public String URL_PARENTS_ASSOCIATION=URL_HEAD+"parents_association";
	public String URL_CCA_DETAILS=URL_HEAD+"cca_details";
	public String URL_CCA_SUBMIT=URL_HEAD+"cca_submit";
	public String URL_CCA_REVIEWS=URL_HEAD+"cca_reviews";
	public String URL_ASSESSMENT_LINK_LIST=URL_HEAD+"assessment";
	public static String URL_GET_LEAVEREQUESTSUBMISSION=URL_HEAD+"requestLeave";
	public static String URL_GET_LEAVEREQUEST_LIST=URL_HEAD+"leaveRequests";
	public static String URL_GET_ENRICHMENT_LESSONS=URL_HEAD+"enrichment_lessons";
}
