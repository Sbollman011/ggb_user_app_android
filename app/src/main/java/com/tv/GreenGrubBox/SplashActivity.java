package com.tv.GreenGrubBox;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewScreenActivity;
import com.tv.GreenGrubBox.data.modal.ValidateVersionRequestModal;
import com.tv.GreenGrubBox.data.modal.ValidateVersionResponseModal;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.network.retrofit.ApiClient;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.launch.LaunchActivity;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 16/01/18.
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @BindView(R.id.videochatapp_tv)
    TextView videochatapp_tv;

    /**
     * Duration of wait
     **/

    private long SPLASH_DISPLAY_LENGTH = 1000;
    private boolean TO_ACCOUNT_SCREEN = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);
        setFonts();
        Logger.logsError(TAG, CommonUtils.getTimeZone());
        Logger.logsError(TAG, "Device Token : " + CommonUtils.getDeviceTokenFromFCM());

        try {
            if (getIntent().getExtras() != null) {
                for (String key : getIntent().getExtras().keySet()) {
                    String value = getIntent().getExtras().getString(key);
                    Logger.logsError(TAG, "Key: " + key + " Value: " + value);
                    if (key.equalsIgnoreCase(Constant.TO_ACCOUNT_SCREEN)) {
                        TO_ACCOUNT_SCREEN = Boolean.parseBoolean(value);
                        break;
                    }
                }
            } else {
                Logger.logsError(TAG, "NULL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    @Override
    protected void onResume() {
        super.onResume();
        checkVersionOfTheApp();
    }

    @Override
    protected void setUp() {

    }

    private void checkVersionOfTheApp() {
        if (!NetworkUtils.isNetworkConnected(getApplicationContext())) {
            return;
        }

        showLoading();
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);


        int version = 0;
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        final ValidateVersionRequestModal validateVersionRequestModal = new ValidateVersionRequestModal();
        validateVersionRequestModal.setOs("Android");
        validateVersionRequestModal.setVersion(version);
        mApiHelper.validateAppVersion(validateVersionRequestModal).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());

                hideLoading();
                if (response.code() == 200) {

                    Gson mGson = new Gson();
                    ValidateVersionResponseModal validateVersionResponseModal = mGson.fromJson(response.body().toString(), ValidateVersionResponseModal.class);
                    if (validateVersionResponseModal == null) {
                        return;
                    }
                    if (validateVersionResponseModal.getStatus() != 1) {
                        openDialogForUpdate(validateVersionResponseModal);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (MyPreference.getCurrentUser() != null) {
                                    /* Create an Intent that will start the Menu-Activity. */
                                    Intent mainIntent = new Intent(SplashActivity.this, NewMainViewScreenActivity.class);
                                    Logger.logsError(TAG, "TO_ACCOUNT_SCREEN : " + TO_ACCOUNT_SCREEN);
                                    mainIntent.putExtra(Constant.TO_ACCOUNT_SCREEN, TO_ACCOUNT_SCREEN);
                                    startActivity(mainIntent);
                                    startActivitySideWiseAnimation();

                                    finish();
                                } else {
                                    /* Create an Intent that will start the Menu-Activity. */
                                    Intent mainIntent = new Intent(SplashActivity.this, LaunchActivity.class);
                                    startActivity(mainIntent);
                                    startActivitySideWiseAnimation();
                                    finish();
                                }


                            }
                        }, SPLASH_DISPLAY_LENGTH);
                    }


                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Logger.logsError(TAG, "onFailure Called");
                t.printStackTrace();

            }
        });
    }

    private void openDialogForUpdate(ValidateVersionResponseModal validateVersionResponseModal) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_update_reqText))
                .setCancelable(false)
                .setMessage(validateVersionResponseModal.getMessage())
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));

                    }
                })
                .show();
    }


    private void setFonts() {

        videochatapp_tv.setTypeface(CommonUtils.setSemiBoldFont(this));
    }
}
