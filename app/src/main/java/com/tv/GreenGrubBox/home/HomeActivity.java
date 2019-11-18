package com.tv.GreenGrubBox.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.SplashActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 19/01/18.
 */

public class HomeActivity extends BaseActivity implements HomeMvpView{

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Inject
    HomeMvpPresenter<HomeMvpView, HomeMvpInteractor> mPresenter;



    @BindView(R.id.logout_btn)
    Button logout_btn;

    @OnClick(R.id.logout_btn)
    void onclicklogout_btn(View view){
        alertMessageForLogout();
    }

    public void alertMessageForLogout() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                      //  mPresenter.doLogoutApiCall(MyPreference.getUserAuthToken());

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked // do nothing
                        dialog.dismiss();

                        break;

                } } };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.areyousureyouwanttosignouttxt)).setPositiveButton(getResources().getString(R.string.yesText), dialogClickListener).setNegativeButton(getResources().getString(R.string.noText), dialogClickListener).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        mPresenter.onAttach(HomeActivity.this);
    }

    @Override
    protected void setUp() {

    }

 /*   @Override
    public void logoutSuccess() {
        Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyPreference.setUserUnderRegistration(false);
        MyPreference.saveUserAuthToken(null);
       // MyPreference.setUserData(null);
        MyPreference.resetAllData();
    }*/
}
