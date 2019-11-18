package com.tv.GreenGrubBox.signup.signupcorporate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewScreenActivity;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 30/01/18.
 */

public class SignUpCorporateActivity extends BaseActivity implements SignUpCorporateMvpView {

    @Inject
    SignUpCorporateMvpPresenter<SignUpCorporateMvpView, SignUpCorporateMvpInteractor> mPresenter;

    @BindView(R.id.done_btn)
    Button done_btn;

    @OnClick(R.id.done_btn)
    void onclickdone_btn(View view) {




        DeviceInfo mDeviceInfo = new DeviceInfo();
        mDeviceInfo.setOs(Constant.ANDROID);
        mDeviceInfo.setResHeight(Constant.getDeviceHeight(this));
        mDeviceInfo.setResWidth(Constant.getDeviceWidth(this));
        mDeviceInfo.setOsVer(Constant.getOSDetails());
        mDeviceInfo.setDevModel(Constant.getDeviceName());
        mDeviceInfo.setAppVer(String.valueOf(Constant.appBuildVersionCode(this)));
        mDeviceInfo.setBattery(Constant.getBatteryPercentage(this));
        mDeviceInfo.setDevice_token(CommonUtils.getDeviceTokenFromFCM());

        mPresenter.signUpCorporate(name_et.getText().toString().trim(), promocode_et.getText().toString().trim(), mSignUpResponse,mDeviceInfo);

    }

    @BindView(R.id.promocode_et)
    EditText promocode_et;

    @BindView(R.id.promocode_tv)
    TextView promocode_tv;

    @BindView(R.id.name_tv)
    TextView name_tv;

    @BindView(R.id.corporate_user_tv)
    TextView corporate_user_tv;

    @BindView(R.id.name_et)
    EditText name_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_corporate_layout);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(SignUpCorporateActivity.this);
        setFonts();
        initData();
    }

    private void setFonts() {
        promocode_et.setTypeface(CommonUtils.setRegularFont(this));
        promocode_tv.setTypeface(CommonUtils.setRegularFont(this));
        done_btn.setTypeface(CommonUtils.setSemiBoldFont(this));
        name_tv.setTypeface(CommonUtils.setRegularFont(this));
        corporate_user_tv.setTypeface(CommonUtils.setSemiBoldFont(this));
        name_et.setTypeface(CommonUtils.setRegularFont(this));
    }

    @Override
    protected void setUp() {

    }

    private SignUpResponse mSignUpResponse;

    private void initData() {

        if (getIntent() != null) {
            if (getIntent().hasExtra(Constant.SIGN_UP_RESPONSE)) {
                mSignUpResponse = (SignUpResponse) getIntent().getSerializableExtra(Constant.SIGN_UP_RESPONSE);
            }
        }
    }


    @Override
    public void openNewScreen(SignUpResponse mSignUpResponse) {
        if (mSignUpResponse == null) {
            showMessage(R.string.some_error);
            return;
        }


        Intent mIntent = new Intent(this, NewMainViewScreenActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        startActivitySideWiseAnimation();
        finishCurrentActivity();

       /* if (mSignUpResponse.getStatus() == 1) {
            mPresenter.doLogin(mSignUpResponse.getEmail(), mSignUpResponse.getPassword());


        } else {
            showMessage(mSignUpResponse.getMessage());
        }*/
    }


    @Override
    public void MainScreen(LoginResponse mLoginResponse) {


        Intent mIntent = new Intent(this, NewMainViewScreenActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        startActivitySideWiseAnimation();
        finishCurrentActivity();
    }
}
