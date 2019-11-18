package com.tv.GreenGrubBox.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tv.GreenGrubBox.ActivateAccount.ActivateAccountActivity;
import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog.ForgotPasswordDialog;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.SelectCategoryTab.SelectedCatagoryActivity;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewScreenActivity;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponseDatum;
import com.tv.GreenGrubBox.signup.SignUpActivity;
import com.tv.GreenGrubBox.signup.signupcorporate.SignUpCorporateActivity;
import com.tv.GreenGrubBox.signup.signupindividual.SignUpIndividualActivity;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 29/01/18.
 */

public class LoginActivity extends BaseActivity implements com.tv.GreenGrubBox.login.LoginMvpView {

    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.email_et)
    EditText email_et;
    @BindView(R.id.pass_et)
    EditText pass_et;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.logo_iv)
    ImageView logo_iv;
    @BindView(R.id.email_tv)
    TextView email_tv;
    @BindView(R.id.pass_tv)
    TextView pass_tv;
    @BindView(R.id.log_into_your_Acc_tv)
    TextView log_into_your_Acc_tv;
    @BindView(R.id.signUpForAnAccount_tv)
    TextView signUpForAnAccount_tv;
    @BindView(R.id.forgot_password_tv)
    TextView forgot_password_tv;
    @Inject
    com.tv.GreenGrubBox.login.LoginMvpPresenter<com.tv.GreenGrubBox.login.LoginMvpView, com.tv.GreenGrubBox.login.LoginMvpInteractor> mPresenter;
    private LoginResponse loginResponse;

    @OnClick(R.id.forgot_password_tv)
    void onclickforgot_password_tv(View view) {
        openForgotPasswordDialog();
    }

    private void openForgotPasswordDialog() {

        ForgotPasswordDialog.newInstance(null).show(getSupportFragmentManager());

    }

    @OnClick(R.id.login_btn)
    void onclickLoginbtn(View view) {

        DeviceInfo mDeviceInfo = new DeviceInfo();
        mDeviceInfo.setOs(Constant.ANDROID);
        mDeviceInfo.setResHeight(Constant.getDeviceHeight(this));
        mDeviceInfo.setResWidth(Constant.getDeviceWidth(this));
        mDeviceInfo.setOsVer(Constant.getOSDetails());
        mDeviceInfo.setDevModel(Constant.getDeviceName());
        mDeviceInfo.setAppVer(String.valueOf(Constant.appBuildVersionCode(this)));
        mDeviceInfo.setBattery(Constant.getBatteryPercentage(this));
        mDeviceInfo.setDevice_token(CommonUtils.getDeviceTokenFromFCM());

        mPresenter.doLogin(email_et.getText().toString().trim(), pass_et.getText().toString().trim(), mDeviceInfo);
    }

    @OnClick(R.id.signUpForAnAccount_tv)
    void onClicksignUpForAnAccount_tv(View view) {

        Intent mIntent = new Intent(this, SignUpActivity.class);
        startActivity(mIntent);
        startActivitySideWiseAnimation();
        finishCurrentActivity();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        ButterKnife.setDebug(true);
        mPresenter.onAttach(LoginActivity.this);
        setFonts();
    }

    private void setFonts() {
        email_et.setTypeface(CommonUtils.setRegularFont(this));
        pass_et.setTypeface(CommonUtils.setRegularFont(this));
        login_btn.setTypeface(CommonUtils.setSemiBoldFont(this));
        email_tv.setTypeface(CommonUtils.setRegularFont(this));
        pass_tv.setTypeface(CommonUtils.setRegularFont(this));
        log_into_your_Acc_tv.setTypeface(CommonUtils.setSemiBoldFont(this));
        signUpForAnAccount_tv.setTypeface(CommonUtils.setRegularFont(this));
        forgot_password_tv.setTypeface(CommonUtils.setRegularFont(this));
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void MainScreen(LoginResponse mLoginResponse) {

        Intent mIntent = new Intent(this, NewMainViewScreenActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        startActivitySideWiseAnimation();
        finishCurrentActivity();
    }

    public void alertMessageForStatus(String message) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        dialog.dismiss();

                        if (loginResponse != null) {
                            if (loginResponse.getData().getAccountType() == 1) {
                                Intent mIntent = new Intent(LoginActivity.this, SignUpIndividualActivity.class);
                                SignUpResponse mSignUpResponse = new SignUpResponse();

                                SignUpResponseDatum mSignUpResponseDatum = new SignUpResponseDatum();
                                if (loginResponse != null) {
                                    mSignUpResponseDatum.setId(loginResponse.getData().getId());

                                }

                                mSignUpResponse.setData(mSignUpResponseDatum);
                                mIntent.putExtra(Constant.SIGN_UP_RESPONSE, mSignUpResponse);

                                startActivity(mIntent);
                                startActivitySideWiseAnimation();
                            } else if (loginResponse.getData().getAccountType() == 2) {
                                Intent mIntent = new Intent(LoginActivity.this, SignUpCorporateActivity.class);
                                SignUpResponse mSignUpResponse = new SignUpResponse();

                                SignUpResponseDatum mSignUpResponseDatum = new SignUpResponseDatum();
                                mSignUpResponseDatum.setId(loginResponse.getData().getId());

                                mSignUpResponse.setData(mSignUpResponseDatum);
                                mIntent.putExtra(Constant.SIGN_UP_RESPONSE, mSignUpResponse);
                                startActivity(mIntent);
                                startActivitySideWiseAnimation();
                            }
                        }

                        break;
                    default:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setPositiveButton(getResources().getString(R.string.okText), dialogClickListener).show();
    }

    @Override
    public void openIndividualScreen(LoginResponse mLoginResponse) {

        if (mLoginResponse != null) {
            this.loginResponse = mLoginResponse;
//            alertMessageForStatus(mLoginResponse.getMessage());
        }

        Intent intent = new Intent(this, SelectedCatagoryActivity.class);
        intent.putExtra(Constant.SIGN_UP_RESPONSE, mLoginResponse.getData());
        intent.putExtra(Constant.IS_CORP_SELECTED,false);
        startActivity(intent);
        finishCurrentActivity();

        /*Intent mIntent = new Intent(this, SignUpIndividualActivity.class);
        SignUpResponse mSignUpResponse = new SignUpResponse();

        SignUpResponseDatum mSignUpResponseDatum = new SignUpResponseDatum();
        mSignUpResponseDatum.setId(mLoginResponse.getData().getId());

        mSignUpResponse.setData(mSignUpResponseDatum);
        mIntent.putExtra(Constant.SIGN_UP_RESPONSE, mSignUpResponse);

        startActivity(mIntent);
        startActivitySideWiseAnimation();*/
    }

    @Override
    public void openCorporateScreen(LoginResponse mLoginResponse) {

        if (mLoginResponse != null) {
            this.loginResponse = mLoginResponse;
//            alertMessageForStatus(mLoginResponse.getMessage());
        }
        Intent intent = new Intent(this, SelectedCatagoryActivity.class);
        intent.putExtra(Constant.SIGN_UP_RESPONSE, mLoginResponse.getData());
        intent.putExtra(Constant.IS_CORP_SELECTED,true);
        startActivity(intent);
        finishCurrentActivity();

       /*
        Intent mIntent = new Intent(this, SignUpCorporateActivity.class);
        SignUpResponse mSignUpResponse = new SignUpResponse();

        SignUpResponseDatum mSignUpResponseDatum = new SignUpResponseDatum();
        mSignUpResponseDatum.setId(mLoginResponse.getData().getId());

        mSignUpResponse.setData(mSignUpResponseDatum);
        mIntent.putExtra(Constant.SIGN_UP_RESPONSE, mSignUpResponse);
        startActivity(mIntent);
        startActivitySideWiseAnimation();*/
    }

    @Override
    public void activateAccount(LoginResponse mLoginResponse, String email) {

        Intent mIntent = new Intent(LoginActivity.this, ActivateAccountActivity.class);
        mIntent.putExtra(Constant.EMAIL, email);
        mIntent.putExtra(Constant.SIGN_UP_RESPONSE, mLoginResponse.getData());

        startActivity(mIntent);
        startActivitySideWiseAnimation();
    }

}
