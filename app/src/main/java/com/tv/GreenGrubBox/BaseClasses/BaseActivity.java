/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.tv.GreenGrubBox.BaseClasses;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tv.GreenGrubBox.MvpApp;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.SplashActivity;
import com.tv.GreenGrubBox.config.Config;
import com.tv.GreenGrubBox.config.NetworkIssue;
import com.tv.GreenGrubBox.config.Screens;
import com.tv.GreenGrubBox.di.component.ActivityComponent;
import com.tv.GreenGrubBox.di.component.DaggerActivityComponent;
import com.tv.GreenGrubBox.di.module.ActivityModule;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.NetworkUtils;

import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private ProgressDialog mProgressDialog;

    private ActivityComponent mActivityComponent;

    private Unbinder mUnBinder;

    public void startActivitySideWiseAnimation() {

        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);

    }

    public void startActivityVerticalAnimation() {

        overridePendingTransition(R.anim.anim_slide_in_top,
                R.anim.anim_slide_out_top);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (isAnimationFromBottom()) {
            isAnimationFromBottom = false;
            overridePendingTransition(R.anim.anim_slide_in_bottom,
                    R.anim.anim_slide_out_bottom);
        } else {
            overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MvpApp) getApplication()).getComponent())
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        Logger.logsError(TAG,"showLoading : Called");
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void showLoadingGif() {
        Logger.logsError(TAG,"showLoadingGif : Called");
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialogGIF(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        Config config = MyPreference.getConfigData();

        String networkMessage = getResources().getString(R.string.internetCheckText);
        if (config != null) {
            Screens mScreens = config.getScreens();
            if (mScreens != null) {
                NetworkIssue mNetworkIssue = mScreens.getNetworkIssue();
                if (mNetworkIssue != null) {
                    networkMessage = mNetworkIssue.getText();
                }
            }
        }
        if (!NetworkUtils.isNetworkConnected(getApplicationContext())) {
            showMessage(networkMessage);
        }
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void openActivityOnTokenExpire() {

        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        startActivitySideWiseAnimation();
//        startActivity(LaunchActivity.getStartIntent(this));
//        finish();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }

        hideKeyboard();
        hideLoading();
        super.onDestroy();

    }

    private boolean isAnimationFromBottom = false;

    private boolean isAnimationFromBottom() {
        return isAnimationFromBottom;
    }


    public void setAnimationFromBottom(boolean isAnimationFromBottom) {
        this.isAnimationFromBottom = isAnimationFromBottom;
    }

    public void finishCurrentActivity() {
        hideKeyboard();
        finish();

        if (isAnimationFromBottom()) {
            isAnimationFromBottom = false;

            overridePendingTransition(R.anim.anim_slide_in_bottom,
                    R.anim.anim_slide_out_bottom);
        } else {
            overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
        }


    }

    protected abstract void setUp();

    @Override
    public void showDialog(String title, String msg1, String msg2, int type, final DialogInterface mDialogInterface) {


    }

}
