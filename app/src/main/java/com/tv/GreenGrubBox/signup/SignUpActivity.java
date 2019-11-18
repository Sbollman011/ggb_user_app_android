package com.tv.GreenGrubBox.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tv.GreenGrubBox.ActivateAccount.ActivateAccountActivity;
import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.login.LoginActivity;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tv-1 on 29/01/18.
 */

public class SignUpActivity extends BaseActivity implements SignUpMvpView {


    @Inject
    SignUpMvpPresenter<SignUpMvpView, SignUpMvpInteractor> mPresenter;

    @BindView(R.id.email_et)
    EditText email_et;

    @BindView(R.id.password_et)
    EditText password_et;

    @BindView(R.id.Confirmpassword_et)
    EditText Confirmpassword_et;

    @BindView(R.id.corporate_tv)
    TextView corporate_tv;
    @BindView(R.id.confirmpassword_tv)
    TextView confirmpassword_tv;

    @BindView(R.id.corporate_iv)
    ImageView corporate_iv;

    @BindView(R.id.individual_iv)
    ImageView individual_iv;

    @BindView(R.id.individual_tv)
    TextView individual_tv;

    @BindView(R.id.haveAnAccount_tv)
    TextView haveAnAccount_tv;

    @BindView(R.id.sign_up_for_an_acc_tv)
    TextView sign_up_for_an_acc_tv;

    @BindView(R.id.continue_btn)
    Button continue_btn;

    @BindView(R.id.email_tv)
    TextView email_tv;

    @BindView(R.id.password_tv)
    TextView password_tv;
    private ACCOUNT_TYPE mAccount_type;

    @OnClick(R.id.corporate_rl)
    void onClickcorporate_rl(View v) {
        corporateSelected();

    }

    @OnClick(R.id.continue_btn)
    void onClickcontinue_btn(View v) {

        if (isValidData()) {
            int mAccountType = 0;
            if (mAccount_type == ACCOUNT_TYPE.CORPORATE) {
                mAccountType = 2;
            } else {
                mAccountType = 1;
            }

            DeviceInfo mDeviceInfo = new DeviceInfo();
            mDeviceInfo.setOs(Constant.ANDROID);
            mDeviceInfo.setResHeight(Constant.getDeviceHeight(this));
            mDeviceInfo.setResWidth(Constant.getDeviceWidth(this));
            mDeviceInfo.setOsVer(Constant.getOSDetails());
            mDeviceInfo.setDevModel(Constant.getDeviceName());
            mDeviceInfo.setAppVer(String.valueOf(Constant.appBuildVersionCode(this)));
            mDeviceInfo.setBattery(Constant.getBatteryPercentage(this));
            mDeviceInfo.setDevice_token(CommonUtils.getDeviceTokenFromFCM());

            mPresenter.signUp(email_et.getText().toString().trim(), password_et.getText().toString().trim(), mAccountType, mDeviceInfo);

        }
    }

    private boolean isValidData() {
        if (email_et.getText().toString().trim().isEmpty()) {
            showMessage(R.string.empty_email);
            return false;
        }

        if (!CommonUtils.isEmailValid(email_et.getText().toString().trim())) {
            showMessage(R.string.invalid_email);
            return false;
        }

        if (password_et.getText().toString().trim().isEmpty()) {
            showMessage(R.string.empty_password);
            return false;
        }
        if (Confirmpassword_et.getText().toString().trim().isEmpty()) {
            showMessage(R.string.confirmempty_password);
            return false;
        }
        if (!password_et.getText().toString().trim().equals(Confirmpassword_et.getText().toString().trim())) {
            onError(R.string.passworddoenotmatch);
            return false;
        }

        return true;
    }

    @OnClick(R.id.haveAnAccount_tv)
    void onClickhaveAnAccount_tv(View v) {

        Intent mIntent = new Intent(this, LoginActivity.class);
        startActivity(mIntent);
        startActivitySideWiseAnimation();
        finishCurrentActivity();

    }

    private void corporateSelected() {
        mAccount_type = ACCOUNT_TYPE.CORPORATE;
        updateAccountTypeStatus();

    }

    private void updateAccountTypeStatus() {
        hideKeyboard();
        if (mAccount_type == ACCOUNT_TYPE.CORPORATE) {
            corporate_iv.setImageResource(R.drawable.select);
            individual_iv.setImageResource(R.drawable.unselect);

        } else if (mAccount_type == ACCOUNT_TYPE.INDIVIDUAL) {

            corporate_iv.setImageResource(R.drawable.unselect);
            individual_iv.setImageResource(R.drawable.select);

        }
    }

    @OnClick(R.id.individual_rl)
    void onClickindividual_rl(View v) {
        individualSelected();

    }

    private void individualSelected() {
        mAccount_type = ACCOUNT_TYPE.INDIVIDUAL;
        updateAccountTypeStatus();

    }

    @Override
    public void signUpResponse(SignUpResponse mSignUpResponse, String email) {

        if (mSignUpResponse == null) {
            showMessage(R.string.some_error);
            return;
        }

        if (mSignUpResponse.getStatus() == 0) {
            showMessage(mSignUpResponse.getMessage());
        } else {
            Intent mIntent = new Intent(this, ActivateAccountActivity.class);
            mIntent.putExtra(Constant.SIGN_UP_RESPONSE, mSignUpResponse.getData());
            startActivity(mIntent);
            startActivitySideWiseAnimation();
            /*if (mAccount_type == ACCOUNT_TYPE.INDIVIDUAL) {

                Intent mIntent = new Intent(this, SignUpIndividualActivity.class);
                mIntent.putExtra(Constant.SIGN_UP_RESPONSE, mSignUpResponse);
                startActivity(mIntent);
                startActivitySideWiseAnimation();

            } else {
                Intent mIntent = new Intent(this, SignUpCorporateActivity.class);
                mIntent.putExtra(Constant.SIGN_UP_RESPONSE, mSignUpResponse);
                startActivity(mIntent);
                startActivitySideWiseAnimation();

            }*/
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(SignUpActivity.this);
        setFonts();
    }

    private void setFonts() {
        email_et.setTypeface(CommonUtils.setRegularFont(this));
        password_et.setTypeface(CommonUtils.setRegularFont(this));
        Confirmpassword_et.setTypeface(CommonUtils.setRegularFont(this));
        corporate_tv.setTypeface(CommonUtils.setRegularFont(this));
        individual_tv.setTypeface(CommonUtils.setRegularFont(this));
        haveAnAccount_tv.setTypeface(CommonUtils.setRegularFont(this));
        continue_btn.setTypeface(CommonUtils.setSemiBoldFont(this));
        sign_up_for_an_acc_tv.setTypeface(CommonUtils.setSemiBoldFont(this));
        email_tv.setTypeface(CommonUtils.setRegularFont(this));
        password_tv.setTypeface(CommonUtils.setRegularFont(this));
        confirmpassword_tv.setTypeface(CommonUtils.setRegularFont(this));
    }

    private enum ACCOUNT_TYPE {CORPORATE, INDIVIDUAL}
}
