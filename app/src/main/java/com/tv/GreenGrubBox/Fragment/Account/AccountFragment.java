package com.tv.GreenGrubBox.Fragment.Account;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tv.GreenGrubBox.BaseClasses.BaseFragment;
import com.tv.GreenGrubBox.Callbacks.CallBackTextChange;
import com.tv.GreenGrubBox.Dialogs.ChangeEmailDialog.ChangeEmailIdDialog;
import com.tv.GreenGrubBox.Dialogs.ChangeToAccountTypeDialog.ChangeToAccountTypeDialog;
import com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog.ForgotPasswordDialog;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.SplashActivity;
import com.tv.GreenGrubBox.activites.activites.BoxHistory.BoxHistoryActivity;
import com.tv.GreenGrubBox.data.modal.UserDetailModal;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.launch.LaunchActivity;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 25/1/18.
 */

public class AccountFragment extends BaseFragment implements AccountMvpView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = AccountFragment.class.getSimpleName();

    private UserDetailModal mUserDetailModal;

    @Inject
    AccountMvpPresenter<AccountMvpView, AccountMvpInteractor> mPresenter;

    @BindView(R.id.sign_out_tv)
    TextView sign_out_tv;

    @BindView(R.id.total_boxes_iv)
    ImageView total_boxes_iv;

    @BindView(R.id.sign_out_rl)
    RelativeLayout sign_out_rl;

    @OnClick(R.id.sign_out_rl)
    void onclicksign_out_rl(View view) {
        alertMessageForSignout();
    }

    public void alertMessageForSignout() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        mPresenter.doLogoutApiCall(MyPreference.getUserAuthToken());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked // do nothing
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(
                getResources().getString(R.string.areyousureyouwanttosignouttxt))
                .setPositiveButton(getResources().getString(R.string.yesText), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.noText), dialogClickListener).show();
    }

    @BindView(R.id.swipe_to_ref)
    SwipeRefreshLayout swipe_to_ref;

    @BindView(R.id.user_iv)
    ImageView user_iv;

    @BindView(R.id.view_tutorial_tv)
    TextView view_tutorial_tv;

    @BindView(R.id.view_tutorial_iv)
    ImageView view_tutorial_iv;

    @BindView(R.id.account_info_tv)
    TextView account_info_tv;

    @BindView(R.id.paymentinfo_tv)
    TextView paymentinfo_tv;

    @BindView(R.id.paymentinfo_iv)
    ImageView paymentinfo_iv;

    @BindView(R.id.account_info_iv)
    ImageView account_info_iv;

    @BindView(R.id.renewal_date_tv)
    TextView renewal_date_tv;

    @BindView(R.id.scanned_box_status_tv)
    TextView scanned_box_status_tv;

    @BindView(R.id.view_box_history_tv)
    TextView view_box_history_tv;

    @BindView(R.id.view_box_history_iv)
    ImageView view_box_history_iv;

    @BindView(R.id.number_of_ggb_tv)
    TextView number_of_ggb_tv;

    @BindView(R.id.you_have_two_ggb_tv)
    TextView you_have_two_ggb_tv;

    @BindView(R.id.green_greenbox_iv)
    ImageView green_greenbox_iv;

    @BindView(R.id.greengrubbox_iv)
    ImageView greengrubbox_iv;

    @BindView(R.id.main_ll)
    LinearLayout main_ll;

    @BindView(R.id.resync_app_rl)
    RelativeLayout resync_app_rl;

    @BindView(R.id.resync_app_tv)
    TextView resync_app_tv;

    @BindView(R.id.account_tv)
    TextView account_tv;

    @BindView(R.id.user_tv)
    TextView user_tv;

    @BindView(R.id.username_tv)
    TextView username_tv;

    @BindView(R.id.total_boxes_tv)
    TextView total_boxes_tv;

    @BindView(R.id.boxes_used_tv)
    TextView boxes_used_tv;

    @BindView(R.id.subscr_expires_tv)
    TextView subscr_expires_tv;

    @BindView(R.id.subscr_expires_number_tv)
    TextView subscr_expires_number_tv;

    @BindView(R.id.subscr_expires_iv)
    ImageView subscr_expires_iv;

    @BindView(R.id.toUpdateAccount_tv)
    TextView toUpdateAccount_tv;

    @BindView(R.id.support_tv)
    TextView support_tv;

    @BindView(R.id.other_tv)
    TextView other_tv;

    @BindView(R.id.website_Rl)
    RelativeLayout website_Rl;

    @OnClick(R.id.website_Rl)
    void onclickwebsite_Rl(View view) {
        if (this.mUserDetailModal != null && this.mUserDetailModal.getData() != null) {
            openBrowser(mUserDetailModal.getData().getWebUrl());
        }
    }

    @BindView(R.id.requestbox_rl)
    RelativeLayout requestbox_rl;

    @BindView(R.id.subscr_expires_rl)
    RelativeLayout subscr_expires_rl;

    @BindView(R.id.migrate_new_email_rl)
    RelativeLayout migrate_new_email_rl;

    @BindView(R.id.report_boxes_rl)
    RelativeLayout report_boxes_rl;

    @BindView(R.id.requestbox_tv)
    TextView requestbox_tv;

    @BindView(R.id.migrate_new_email_tv)
    TextView migrate_new_email_tv;

    @BindView(R.id.move_to_other_tv)
    TextView move_to_other_tv;

    @BindView(R.id.website_tv)
    TextView website_tv;

    private void openBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @BindView(R.id.report_boxes_tv)
    TextView report_boxes_tv;

    @BindView(R.id.call_us_rl)
    RelativeLayout call_us_rl;

    @BindView(R.id.email_rl)
    RelativeLayout email_rl;

    @BindView(R.id.email_tv)
    TextView email_tv;

    @BindView(R.id.call_us_tv)
    TextView call_us_tv;

    @BindView(R.id.terms_and_cond_tv)
    TextView terms_and_cond_tv;

    @BindView(R.id.terms_and_cond_rl)
    RelativeLayout terms_and_cond_rl;

    @OnClick(R.id.terms_and_cond_rl)
    void onclickterms_and_cond_rl(View view) {
        if (this.mUserDetailModal != null && this.mUserDetailModal.getData() != null) {
            openBrowser(mUserDetailModal.getData().getTermsAndCondition());
        }
    }

    @OnClick(R.id.migrate_new_email_rl)
    void onclickmigrate_new_email_rl(View view) {
        ChangeEmailIdDialog.newInstance(null).show(getChildFragmentManager());
    }

    @OnClick(R.id.email_rl)
    void onClickemail_rl(View v) {

        if (mUserDetailModal != null && mUserDetailModal.getData() != null && mUserDetailModal.getData().getGgb_email() != null) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:" + mUserDetailModal.getData().getGgb_email()));
                intent.putExtra(Intent.EXTRA_SUBJECT, Constant.SUBJECT_SEND_US_MESSAGE);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.call_us_rl)
    void onclickCall_us_RL(View view) {
        if (mUserDetailModal != null && mUserDetailModal.getData() != null && mUserDetailModal.getData().getNumber() != null) {
            Uri number = Uri.parse("tel:" + mUserDetailModal.getData().getNumber());
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        }
    }

    @OnClick(R.id.main_ll)
    void onclickmain_ll(View view) {
        Intent intent = new Intent(getActivity(), BoxHistoryActivity.class);
        startActivity(intent);
        startActivitySideWiseAnimation();
    }


    @OnClick(R.id.move_to_other_rl)
    void onclickmove_to_other_rl(View view) {
        openChangeToAccountDialog();
    }

    private void openChangeToAccountDialog() {
        if(mUserDetailModal.getData() == null){
            return;
        }

        Bundle mBundle = new Bundle();
        if (mUserDetailModal.getData() != null) {
            mBundle.putInt(Constant.ACCOUNTTYPE, mUserDetailModal.getData().getAccountType());
        }
        ChangeToAccountTypeDialog.newInstance(mBundle, new CallBackTextChange() {
            @Override
            public void textChange() {
                callWS(false);
            }
        }).show(getChildFragmentManager());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_screen_fragment, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        return view;
    }

    private void setFonts() {
        sign_out_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        view_tutorial_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        account_info_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        paymentinfo_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        renewal_date_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        scanned_box_status_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        number_of_ggb_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        you_have_two_ggb_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        account_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        resync_app_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        move_to_other_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        migrate_new_email_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        user_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        username_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        total_boxes_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        boxes_used_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        subscr_expires_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        subscr_expires_number_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        support_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        toUpdateAccount_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        other_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        requestbox_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        report_boxes_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        website_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        email_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        call_us_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        terms_and_cond_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        view_box_history_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
    }

    @Override
    protected void setUp(View view) {

        swipe_to_ref.setEnabled(true);
        swipe_to_ref.setOnRefreshListener(this);
        swipe_to_ref.setColorSchemeResources(R.color.colorPrimaryDark);
        setFonts();
        callWS(true);

//        mPresenter.getUserDetails(false);

    }

    private void callWS(boolean isProgress) {

        if (mPresenter != null) {
            mPresenter.getUserDetails(isProgress);
            Logger.logsError(TAG, "Api called");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //mPresenter.getUserDetails(false);
        callWS(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //mPresenter.getUserDetails(false);
                    callWS(false);

                }
            }, 50);
        }
    }

    @Override
    public void userDetailsResponse(UserDetailModal mUserDetailModal) {
        if (mUserDetailModal == null) {
            showMessage(R.string.some_error);
            return;
        }

        swipe_to_ref.setRefreshing(false);


        this.mUserDetailModal = mUserDetailModal;

        if (mUserDetailModal.getData().getName() != null) {
            username_tv.setText(mUserDetailModal.getData().getName());
        }

        if (mUserDetailModal.getData().getTotalBoxes() != null) {
            boxes_used_tv.setText("" + mUserDetailModal.getData().getTotalBoxes());
        }

        if (mUserDetailModal.getData().getNextRenewOn() != null) {
            subscr_expires_number_tv.setText(mUserDetailModal.getData().getNextRenewOn());
        }

        if (mUserDetailModal.getStatus() == 1) {

            number_of_ggb_tv.setText(String.valueOf(mUserDetailModal.getData().getCurrentBoxes()));

            if (mUserDetailModal.getData().getAccountType() == 1) {
                move_to_other_tv.setText(getResources().getString(R.string.movetocorparate));
            } else {
                move_to_other_tv.setText(getResources().getString(R.string.moveto));
            }

            if (mUserDetailModal.getData().getCurrentBoxes() == 0) {
                you_have_two_ggb_tv.setText("" + getResources().getString(R.string.grubBoxesScannedOutZeroText));
            } else {

                if (mUserDetailModal.getData().getCurrentBoxes() == 1) {

                    you_have_two_ggb_tv.setText("" + getResources().getString(R.string.youHaveText).concat(" ")
                            .concat(String.valueOf(mUserDetailModal.getData().getCurrentBoxes()).concat(" ").
                                    concat(getResources().getString(R.string.grubBoxScannedOutText))));
                } else {
                    you_have_two_ggb_tv.setText("" + getResources().getString(R.string.youHaveText).concat(" ")
                            .concat(String.valueOf(mUserDetailModal.getData().getCurrentBoxes()).concat(" ").
                                    concat(getResources().getString(R.string.grubBoxesScannedOutText))));
                }
            }
//            you_have_two_ggb_tv.setText(""+getResources().getString(R.string.youHaveText).concat(" ")
//                    .concat(String.valueOf(mUserDetailModal.getData().getCurrentBoxes()).concat(" ").
//                            concat(getResources().getString(R.string.grubBoxesScannedOutText))));

            scanned_box_status_tv.setText(String.valueOf(mUserDetailModal.getData().getCurrentBoxes()).concat(" ")
                    .concat(getResources().getString(R.string.grubBoxesScannedText)));

        } else {
            showMessage(mUserDetailModal.getMessage());
        }
    }

    @Override
    public void logoutSuccess() {
        Intent intent = new Intent(getActivity(), LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MyPreference.setUserUnderRegistration(false);
        MyPreference.saveUserAuthToken(null);
        MyPreference.setUserData(null);
        MyPreference.resetAllData();
        getActivity().startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void logoutUser() {
        logoutSuccess();
    }

    @Override
    public void onRefresh() {
        callWS(false);
    }
}