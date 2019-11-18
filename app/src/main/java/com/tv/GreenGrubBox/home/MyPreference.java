package com.tv.GreenGrubBox.home;

import android.content.Context;
import android.content.SharedPreferences;

import com.tv.GreenGrubBox.MvpApp;
import com.tv.GreenGrubBox.config.Config;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;

/**
 * Created by Shoeb on 14/8/17.
 */

public class MyPreference {

    private static final String TAG = MyPreference.class.getSimpleName();
    private static SharedPreferences sharedPref;
    private static LoginResponse mLoginResponse;
    private static Config mConfig;


    /**
     * Set user is registered.
     *
     * @param isUserRegistered to save user registration status.
     */
    public static void setUserUnderRegistration(boolean isUserRegistered) {

        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(Constant.IS_REG_INPROCESS, isUserRegistered);
        editor.commit();
    }


    /**
     * Save User Token.
     *
     * @param token to save user token.
     */
    public static void saveUserAuthToken(String token) {

        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(Constant.AUTH_TOKEN, token);
        editor.commit();
    }

    /**
     * Get User Token.
     *
     * @return user token.
     */
    public static String getUserAuthToken() {

        SharedPreferences prefs = getSettings();

        return prefs.getString(Constant.AUTH_TOKEN, "");
//        return "c00f73ad945bd431db80dc1a048c804e7aa30f92aa3bf4ba4f5314e6bf2dbee91f8d079ff2bb636cf0d2824e34d118980c69f1ba82747554d67ba632f2358538f67b7c33870759397074f91ec4dc893f969460469629b84ada6eeec0526603158e45";
    }


    /**
     * Get User Data.
     *
     * @return Logged In user data or null if blank.
     */
    public static LoginResponse getCurrentUser() {
        if (mLoginResponse != null) {
            return mLoginResponse;
        } else {
            SharedPreferences prefs = getSettings();
            String json = prefs.getString(Constant.LOGIN_USER_RESPONSE, "");
            Logger.logsInfo(TAG, "mGoinUserModalMain : " + json);
            if (json.isEmpty() || "null".equals(json)) {

                return null;
            } else {
                mLoginResponse = CommonUtils.getGsonParser().fromJson(json, LoginResponse.class);

                return mLoginResponse;
            }
        }

    }

    public static Config getConfigData() {
        if (mConfig != null) {
            return mConfig;
        } else {
            SharedPreferences prefs = getSettings();
            String json = prefs.getString(Constant.CONFIG_DATA_RESPONSE, "");
            if (json.isEmpty() || "null".equals(json)) {

                return null;
            } else {
                mConfig = CommonUtils.getGsonParser().fromJson(json, Config.class);

                return mConfig;
            }
        }
    }

    public static void resetAllData() {
        mLoginResponse = null;
    }

    /**
     * Get User Registration info.
     *
     * @return true if user is registered.
     */
    public static boolean isUserUnderRegistation() {

        SharedPreferences prefs = getSettings();

        return prefs.getBoolean(Constant.IS_REG_INPROCESS, false);
    }

    /**
     * Obtain preferences instance.
     *
     * @return base instance of app SharedPreferences.
     */
    public static SharedPreferences getSettings() {
        if (sharedPref == null) {
            sharedPref = MvpApp.getInstance().getSharedPreferences(MvpApp.PACKAGE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPref;
    }

    public static void setUserData(LoginResponse mLoginResponse) {
        MyPreference.mLoginResponse = mLoginResponse;
        String json = CommonUtils.getGsonParser().toJson(mLoginResponse);

        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(Constant.LOGIN_USER_RESPONSE, json);
        editor.commit();
    }


    public static void setConfigData(Config mConfig) {
        MyPreference.mConfig = mConfig;
        String json = CommonUtils.getGsonParser().toJson(mConfig);
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(Constant.CONFIG_DATA_RESPONSE, json);
        editor.commit();
    }


}
