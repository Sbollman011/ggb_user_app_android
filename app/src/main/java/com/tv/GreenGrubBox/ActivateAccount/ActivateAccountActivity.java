package com.tv.GreenGrubBox.ActivateAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.SelectCategoryTab.SelectedCatagoryActivity;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginRequest;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponseDatum;
import com.tv.GreenGrubBox.signup.SignUpActivity;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dev-20 on 4/9/18.
 */

public class ActivateAccountActivity extends BaseActivity implements ActivateAccountMvpView {

    private static final String TAG = ActivateAccountActivity.class.getSimpleName();

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.left_icon_1_iv)
    ImageView left_icon_1_iv;

    @BindView(R.id.input_pin_et)
    EditText input_pin_et;

    @BindView(R.id.inserttheSixdigitpinCode_tv)
    TextView inserttheSixdigitpinCode_tv;

    @BindView(R.id.insertsixdigitpin_tv)
    TextView insertsixdigitpin_tv;

    @BindView(R.id.backbutton_iv)
    ImageView backbutton_iv;

    @BindView(R.id.resend_pin_btn)
    Button resend_pin_btn;
    DeviceInfo mDeviceInfo;
    @Inject
    ActivateAccountMvpPresenter<ActivateAccountMvpView, ActiviateAccountMvpInteractor> mPresenter;

    private String email = "";
    private String username = "";
    private String socialId = "";
    private String accountType = "";
    private int otp = 0;
    private boolean isActivated = false;
    private SignUpResponseDatum signUpResponse;
    private String userEmail;

    @OnClick(R.id.resend_pin_btn)
    void onclickresend_pin_btn(View view) {

        if (signUpResponse != null ) {
            mPresenter.resendVerification(signUpResponse.getEmail());
        }
    }

    @OnClick(R.id.backbutton_iv)
    void onclicklefticon(View view) {
        /*Logger.logsError(TAG, "Left clicked called =================");
        Intent mIntent = new Intent(this, SignUpActivity.class);
        startActivity(mIntent);*/
        finishCurrentActivity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate_account_layout);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(ActivateAccountActivity.this);
        initData();
        setFonts();
    }

    private void setFonts() {
        resend_pin_btn.setTypeface(CommonUtils.setRegularFont(this));
        insertsixdigitpin_tv.setTypeface(CommonUtils.setRegularFont(this));
        inserttheSixdigitpinCode_tv.setTypeface(CommonUtils.setRegularFont(this));
        input_pin_et.setTypeface(CommonUtils.setBoldFont(this));
        resend_pin_btn.setTypeface(CommonUtils.setRegularFont(this));
        toolbar_title.setTypeface(CommonUtils.setBoldFont(this));
    }

    private void initData() {

        pinVerification();

        ActivateAccountDialog.newInstance(null).show(getSupportFragmentManager());

        if (getIntent() != null) {

            if (getIntent().hasExtra(Constant.SIGN_UP_RESPONSE)) {

                signUpResponse = (SignUpResponseDatum) getIntent().getSerializableExtra(Constant.SIGN_UP_RESPONSE);
            }

        }

        toolbar_title.setText(getResources().getString(R.string.activate_AccountText));
        toolbar_title.setTextColor(getResources().getColor(R.color.white));
        toolbar_title.setTextSize(18);
        left_icon_1_iv.setImageResource(R.drawable.back);

    }

    private void pinVerification() {
//        Logger.logsInfo(TAG,"USEID========"+        mLoginResponse.getUserDetail().getUserId());
        input_pin_et.addTextChangedListener(new TextWatcher() {
            private final long DELAY = 800; // milliseconds
            private Timer timer = new Timer();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                timer.cancel();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String mInput = editable.toString().trim();
                if (!mInput.isEmpty()) {
                    input_pin_et.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
// TODO: do what you need here (refresh list)
// you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mInput.trim().length() >= 6 && mInput.trim().length() < 15) {
                                                otp = Integer.parseInt(mInput);
                                                if (signUpResponse != null) {
                                                    email = signUpResponse.getEmail();
                                                    mPresenter.verifyOtp(email, otp);
                                                } /*else if (loginRequest != null) {
                                                    email = loginRequest.getEmail();
                                                }*/

                                            } else {
                                                onError(R.string.pleaseEntersixDigitPinText);
                                            }
                                        }
                                    });
                                }
                            },
                            DELAY
                    );
                }
            }
        });
    }

    @Override
    protected void setUp() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //do your stuff
            Intent mIntent = new Intent(this, SignUpActivity.class);
            startActivity(mIntent);
            finishCurrentActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void responseVerifyOTP(SignUpResponse signUpResponse) {
        if (signUpResponse == null) {
            onError(R.string.some_error);
            return;
        }

        if (signUpResponse.getStatus() == 1) {
            Intent intent = new Intent(this, SelectedCatagoryActivity.class);
            intent.putExtra(Constant.SIGN_UP_RESPONSE, signUpResponse.getData());
            startActivity(intent);
            finishCurrentActivity();
        } else {
            showMessage(signUpResponse.getMessage());
        }

    }
}