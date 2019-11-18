package com.tv.GreenGrubBox.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Created by Shoeb on 21/7/17.
 */

public class Constant {
    public static final String mPublishableKey = "pk_test_624xpBnqZIMHajri9QDIvGMc";

    public static final String FOLDER_NAME = "VideoCalling";
    public static final String ACCOUNT_TYPE_FB = "facebook";
    public static final String ACCOUNT_TYPE_GOOGLE_PLUS = "google";
    public static final String ACCOUNT_TYPE_TWITTER = "twitter";
    public static final String IS_REG_INPROCESS = "IS_REG_INPROCESS";
    public static final String AUTH_TOKEN = "AUTH_TOKEN";

    public static final String LOGIN_USER_RESPONSE = "LOGIN_USER_RESPONSE";
    public static final String CONFIG_DATA_RESPONSE = "CONFIG_DATA_RESPONSE";


    public static final String EVENT_DATUM = "EVENT_DATUM";
    public static final String TEAM_LIST = "TEAM_LIST";
    public static final String PHOTO_URL = "PHOTO_URL";
    public static final String BILLING_INFO_MODAL = "BILLING_INFO_MODAL";

    public static final String SCANNER = "Scanner";
    public static final String TICKET = "Tickets";
    public static final String TEAM = "Team";
    public static final String ASSOCIATED_PROFILE_LIST="ASSOCIATED_PROFILE_LIST";


    public static final String GUEST_LIST = "Guest List";
    public static final String FINACIAL_INFO = "Financial Info";
    public static final String START = "Stats & Analytics";
    public static final String AUTHENTICATION = "Authentication";
    public static final String PUSH_NOTI = "Push Notifications";
    public static final String EDIT_EVENT = "Edit this Event";
    public static final String DELETE_EVENT = "Delete this Event";
    public static final String PAYMENT_SUCCESS = "PAYMENT_SUCCESS";
    public static final String TICKET_DATUM = "TICKET_DATUM";
    public static final String CURRENT_POSITION = "CURRENT_POSITION";
    public static final String TICKET_VALIDATOR = "TICKET_VALIDATOR";
    public static final String ADMIN = "ADMIN";
    public static final String PR = "PR";
    public static final String BILLING_INFO_AFTER_PAYTMENT = "BILLING_INFO_AFTER_PAYTMENT";
    public static final String CARD_DETAILS = "CARD_DETAILS";
    public static final String fromFreeEvent = "fromFreeEvent";
    public static final String fromGenralInfo = "fromGenralInfo";
    public static final String fromPublicEventValidateAcc="fromPublicEventValidateAcc";
    public static final String fromPrivateLocation = "fromPrivateLocation";
    public static final String fromPhotoVideo = "fromPhotoVideo";
    public static final String fromAddFreeTickets = "fromAddFreeTickets";
    public static final String fromcategory = "fromcategory";
    public static final String fromAdditional = "fromAdditional";
    public static final String USER_DETAILS = "USER_DETAILS";
    public static final String toFreeTicketDetails = "toFreeTicketDetails";
    public static final String fromAssociatedProf = "fromAssociatedProf";
    public static final String fromTicketSalesToThankyouScreen = "fromTicketSalesToThankyouScreen";

    public static final String EVENT_TICKET = "EVENT_TICKET";
    public static final String fromValidateAccToPlans = "fromValidateAccToPlans";
    public static final String fromFreePlansToGeneralInfo = "fromFreePlansToGeneralInfo";
    public static final String fromGoldPlanToGeneralInfo = "fromGoldPlanToGeneralInfo";
    public static final String fromPublicEvent = "fromPublicEvent";
    public static final String fromBillingInfoToTicketSale = "fromBillingInfoToTicketSale";

    /*EventPlaceAllowedAge */
    public static final String AA_0_6 = "AA_0_6";
    public static final String AA_12_18 = "AA_12_18";
    public static final String AA_18_PLUS = "AA_18_PLUS";

    /*EventPlaceEnvironment */
    public static final String INDOOR = "INDOOR";
    public static final String OUTDOOR = "OUTDOOR";

    /*EventPlaceAllowedPaymentInside */
    public static final String CASH = "CASH";
    public static final String DEBIT_CARD = "DEBIT_CARD";
    public static final String CREDIT_CARD = "CREDIT_CARD";
    public static final String CASHLESS = "CASHLESS";
    public static final String ATM_NEARBY = "ATM_NEARBY";
    public static final String OTHER = "OTHER";

    /*EventPlaceParking */
    public static final String FREE = "FREE";
    public static final String PAYED = "PAYED";
    public static final String NEARBY = "NEARBY";
    public static final String UNDERGROUND = "UNDERGROUND";

    /*EventPlaceExtra */
    public static final String FREE_WI_FI = "FREE_WI_FI";
    public static final String SMOKING_AREA = "SMOKING_AREA";
    public static final String FOOD = "FOOD";
    public static final String VIP = "VIP";
    public static final String PET_FRIENDLY = "PET_FRIENDLY";
    public static final String TOILET = "TOILET";
    public static final String CLOAKROOM = "CLOAKROOM";
    public static final String LOUNGE_AREA = "LOUNGE_AREA";
    public static final String SPECIAL_MOBILITY_ACCESS = "SPECIAL_MOBILITY_ACCESS";
    public static final String FAMILY_FRIENDLY = "FAMILY_FRIENDLY";
    public static final String BAR_DRINKS = "BAR_DRINKS";
    public static final String SHOWROOM = "SHOWROOM";

    /*EventSearchDateType */
    public static final String TODAY = "TODAY";
    public static final String TOMORROW = "TOMORROW";
    public static final String WEEKEND = "WEEKEND";
    public static final String SPECIFIC_DATE = "SPECIFIC_DATE";


    /*NotificationAction */
    public static final String FRIEND_REQUEST = "FRIEND_REQUEST";
    public static final String FRIEND = "FRIEND";
    public static final String INVITE_EVENT_TEAM_REQUEST = "INVITE_EVENT_TEAM_REQUEST";
    public static final String INVITE_EVENT_TEAM_RESPONSE = "INVITE_EVENT_TEAM_RESPONSE";
    public static final String TICKET_OFFER_REQUEST = "TICKET_OFFER_REQUEST";
    public static final String TICKET_OFFER_RESPONSE = "TICKET_OFFER_RESPONSE";
    public static final String FOLLOWING_ARTIST_EVENT_STARTING = "FOLLOWING_ARTIST_EVENT_STARTING";
    public static final String INVITE_EVENT_GUEST_LIST_REQUEST = "INVITE_EVENT_GUEST_LIST_REQUEST";
    public static final String INVITE_EVENT_GUEST_LIST_RESPONSE = "INVITE_EVENT_GUEST_LIST_RESPONSE";
    public static final String EVENT_CREATED = "EVENT_CREATED";
    public static final String EVENT_SHARE = "EVENT_SHARE";
    public static final String FOLLOWERS_COUNTER_ACHIEVEMENT = "FOLLOWERS_COUNTER_ACHIEVEMENT";
    public static final String SAME_CATEGORY = "SAME_CATEGORY";
    public static final String SAME_LOCATION = "SAME_LOCATION";
    public static final String SAME_CREATOR = "SAME_CREATOR";
    public static final String SAME_PROFILE = "SAME_PROFILE";
    public static final String FRIEND_DATUM = "FRIEND_DATUM";
    public static final String EMAIL_SEND_US_MESSAGE = "support@greengrubbox.com";
    public static final String SUBJECT_SEND_US_MESSAGE = "Green GrubBox";
    public static final String SUBJECT_MAIL = "Request Container Return issue.";
    public static final String SUBJECT_REPORT_AN_ISSUE = "GOiN Report an issue";
    public static final String PUBLIC_PROFILE_DATA = "PUBLIC_PROFILE_DATA";
    public static final String QUERY = "QUERY";
    public static final String EVENT_SEQUENCE = "EVENT_SEQUENCE";
    public static final String WEBSITE_URL = "https://goin.events";
    public static final String TERMS_AND_CONDITION_URL = "https://dev.goin.events/termos_condicoes.php";
    public static final String PRIVACY_POLICY_URL = "https://dev.goin.events/politica_privacidade.php";

    public static final String EVENT_FILTER_DATUM = "EVENT_FILTER_DATUM";
    public static final String IS_PROFILE_UPDATED = "IS_PROFILE_UPDATED";
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNESDAY = "WEDNESDAY";
    public static final String THURSDAY = "THURSDAY";
    public static final String FRIDAY = "FRIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String SUNDAY = "SUNDAY";
    public static final String IS_DELETED = "IS_DELETED";
    public static final String OPEN_PURCHASED_TICKETS = "OPEN_PURCHASED_TICKETS";

    /*Push notifications*/
    public static final String FROM_EVENTS_AND_PUBLIC_PROFILES_I_FOLLOW = "FROM_EVENTS_AND_PUBLIC_PROFILES_I_FOLLOW";
    public static final String NEW_LOCATION = "NEW_LOCATION";
    public static final String FROM_FRIENDS = "FROM_FRIENDS";
    public static final String PROMOTIONAL = "PROMOTIONAL";
    public static final String TICKET_SALES = "TICKET_SALES";
    public static final String RATE_EVENTS = "RATE_EVENTS";
    public static final String GOIN_INFO = "GOIN_INFO";
    public static final String ADDED_TO_GUEST_LIST = "ADDED_TO_GUEST_LIST";
    public static final String ADDED_TO_TEAM = "ADDED_TO_TEAM";
    public static final long LOCATION_INTERVAL = 1000 * 60 * 60 * 8; // 8 Hours
    public static final long LOCATION_FASTEST_INTERVAL = 1000 * 60 * 60 * 8; // 8 Hours
    public static final String GOINUSER = "GoinUser";


    public static final String ANDROID = "Android";
    public static final String FROM_SOCIAL_LOGIN = "FROM_SOCIAL_LOGIN" ;
    public static final String SIGN_UP_RESPONSE = "SIGN_UP_RESPONSE";
    public static final String LOCATION_TYPE = "LOCATION_TYPE";
    public static final String LOCATION_DATUM = "LOCATION_DATUM";
    public static final String IMAGES_LIST = "IMAGES_LIST";
    public static final String TO_ACCOUNT_SCREEN = "TO_ACCOUNT_SCREEN";
    public static final String EMAIL = "EMAIL";
    public static final String IS_CORP_SELECTED = "IS_CORP_SELECTED";


    //    ** Returns the consumer friendly device name */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

//        String phrase = "";
        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
//                phrase += Character.toUpperCase(c);
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
//            phrase += c;
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static String getOSDetails() {
        String version = "";

        StringBuilder builder = new StringBuilder();
        builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                version = fieldName;
            }
        }
        return version;
    }

    public static String getDeviceResolutions(Activity context) {
        int widthDevice = 0;
        int heightDevice = 0;
        String resolution = "";
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        widthDevice = metrics.widthPixels;
        heightDevice = metrics.heightPixels;
        resolution = heightDevice + "x" + widthDevice;
        return resolution;
    }

    public static int getDeviceWidth(Context context) {
        int widthDevice = 0;


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        widthDevice = metrics.widthPixels;

        return widthDevice;
    }

    public static int getDeviceHeight(Context context) {
        int heightDevice = 0;


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        heightDevice = metrics.heightPixels;

        return heightDevice;
    }


    public static String appBuildVersionName(Context mContext) {
        String buildVersion = "";
        try {

            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            buildVersion = pInfo.versionName;

        } catch (Exception e) {

        }

        return buildVersion;
    }


    public static int appBuildVersionCode(Context mContext) {
        int verCode = 0;
        try {

            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            verCode = pInfo.versionCode;

        } catch (Exception e) {

        }

        return verCode;
    }


    public static int getBatteryPercentage(Context context) {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }
}
