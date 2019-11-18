package com.tv.GreenGrubBox.activites.activites.MainViewScreen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.Fragment.Account.AccountFragment;
import com.tv.GreenGrubBox.Fragment.MainLocationFragment;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.activites.activites.QrScanner.QrScannerActivity;
import com.tv.GreenGrubBox.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by user on 24/1/18.
 */

public class MainViewScreenActivity extends BaseActivity implements MainViewScreenMvpView {

    @BindView(R.id.location_ll)
    LinearLayout location_ll;

    @BindView(R.id.account_ll)
    LinearLayout account_ll;

    @BindView(R.id.qrscanner_ll)
    LinearLayout qrscanner_ll;

    private ZXingScannerView mScannerView;
    private final int per_request_code = 1;
    private Boolean granted = false;


    @Inject
    MainViewScreenMvpPresenter<MainViewScreenMvpView, MainViewScreenMvpInteractor> mPresenter;

    @OnClick(R.id.qrscanner_ll)
    void openQRscanner(View view) {

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        } else {
            Intent mIntent = new Intent(this, QrScannerActivity.class);
            startActivity(mIntent);
        }
    }

    @OnClick(R.id.account_ll)
    void onClickAccount(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AccountFragment mAccountFragment = new AccountFragment();
        ft.replace(R.id.realtabcontent, mAccountFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    @OnClick(R.id.location_ll)
    void onClickLocation(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MainLocationFragment mAccountFragment = new MainLocationFragment();
        ft.replace(R.id.realtabcontent, mAccountFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_screen);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        mPresenter.onAttach(MainViewScreenActivity.this);

        setFonts();
        initData();

      /*  createViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();*/
    }

    private void initData() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MainLocationFragment mAccountFragment = new MainLocationFragment();
        ft.add(R.id.realtabcontent, mAccountFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();

    }

    private void setFonts() {

    }

    private String[] permissions = new String[]{
            Manifest.permission.CAMERA
    };

    public boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(MainViewScreenActivity.this, p);
            if (result == 0) {
                granted = true;
                Intent mIntent = new Intent(this, QrScannerActivity.class);
                startActivity(mIntent);
            } else {
                granted = false;
            }
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), per_request_code);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case per_request_code: {
                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                        granted = true;
                        Intent mIntent = new Intent(this, QrScannerActivity.class);
                        startActivity(mIntent);
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                            showDialogOK(getResources().getString(R.string.ReqiuredStoragePermissionText),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            AppConstants.showToast(getResources().getString(R.string.GotoSettingText), MainViewScreenActivity.this);
                        }
                    }
                    return;
                }
            }

        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.DialogOK), okListener)
                .setNegativeButton(getResources().getString(R.string.DialogCancel), okListener)
                .create()
                .show();
    }
}
