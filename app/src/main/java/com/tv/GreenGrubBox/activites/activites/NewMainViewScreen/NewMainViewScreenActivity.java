package com.tv.GreenGrubBox.activites.activites.NewMainViewScreen;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.Fragment.Account.AccountFragment;
import com.tv.GreenGrubBox.Fragment.BlankFragment;
import com.tv.GreenGrubBox.Fragment.MainLocationFragment;
import com.tv.GreenGrubBox.Fragment.MapViewVendorFragment.VendorMapFragment;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.SplashActivity;
import com.tv.GreenGrubBox.activites.activites.BoxHistory.BoxHistoryActivity;
import com.tv.GreenGrubBox.data.modal.CheckOutBoxResponse;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.Views.MainViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by admin on 31/01/18.
 */

public class NewMainViewScreenActivity extends BaseActivity implements ZXingScannerView.ResultHandler, NewMainViewMvpView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = NewMainViewScreenActivity.class.getSimpleName();

    @BindView(R.id.scan_btn)
    ImageView scan_btn;

   /* @BindView(R.id.tvScanContent)
    TextView tvScanContent;

    @BindView(R.id.tvScanFormat)
    TextView tvScanFormat;
*/

    @Inject
    NewMainViewMvpPresenter<NewMainViewMvpView, NewMainViewMvpInteractor> mPresenter;

    MainViewPager viewPager;
    private double latitude = 0.0;
    private double longitude = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main_view_screem_layout);

        //   FirebaseCrash.report(new Exception("My first Android non-fatal error"));

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        ButterKnife.setDebug(true);
        mPresenter.onAttach(NewMainViewScreenActivity.this);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scan_btn.getLayoutParams();
        params.width = Constant.getDeviceWidth(this) / 3;
        scan_btn.setLayoutParams(params);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (MainViewPager) findViewById(R.id.main_tab_content);
        viewPager.setPagingEnabled(false);

        setupViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(7);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);

        tabLayout.getTabAt(0).select();

        if (getIntent() != null) {
            if (getIntent().hasExtra(Constant.TO_ACCOUNT_SCREEN)) {
                boolean TO_ACCOUNT_SCREEN = getIntent().getBooleanExtra(Constant.TO_ACCOUNT_SCREEN, false);
                if (TO_ACCOUNT_SCREEN) {
                    Logger.logsError(TAG, "TO_ACCOUNT_SCREEN :  " + TO_ACCOUNT_SCREEN);
                    viewPager.setCurrentItem(2, true);
                }
            }
        }

//        boolean result = checkPermission(NewMainViewScreenActivity.this);
//        if (result) {
//            getCurrentLocation();
//        }

    }

    private void setupTabIcons(TabLayout tabLayout) {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getResources().getString(R.string.location));

        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.location, 0, 0);
        tabOne.setScaleY(-1);
        tabOne.setTypeface(CommonUtils.setRegularFont(this));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tabTwo.setScaleY(-1);
        tabTwo.setTypeface(CommonUtils.setRegularFont(this));

        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getResources().getString(R.string.accountCaps));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account, 0, 0);
        tabThree.setScaleY(-1);
        tabThree.setTypeface(CommonUtils.setRegularFont(this));

        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 1 || viewPager.getCurrentItem() == 2) {
            super.onBackPressed();
        }

//        int count = getFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            additional code
//        } else {
//            getFragmentManager().popBackStack();
//        }

    }

    @Override
    protected void setUp() {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.insertNewFragment(new MainLocationFragment(), getResources().getString(R.string.location));
        adapter.insertNewFragment(new BlankFragment(), "");
        adapter.insertNewFragment(new AccountFragment(), getResources().getString(R.string.account));
        viewPager.setAdapter(adapter);
    }

    public void alertMessageForStatus(String message) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(NewMainViewScreenActivity.this);
        builder.setMessage(message).setPositiveButton(getResources().getString(R.string.okText), dialogClickListener).show();
    }

    @Override
    public void handleCheckOutResponse(CheckOutBoxResponse mCheckOutBoxResponse) {
        if (mCheckOutBoxResponse == null) {
            showMessage(R.string.some_error);
            return;
        }
        if (mCheckOutBoxResponse.getStatus() == 1) {

            if (mCheckOutBoxResponse.getIsRenewRequire() == 1) {
                alertMessageForStatus(mCheckOutBoxResponse.getMessage());
                return;

               /* Intent intent = new Intent(this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MyPreference.setUserUnderRegistration(false);
                MyPreference.saveUserAuthToken(null);
                MyPreference.setUserData(null);
                MyPreference.resetAllData();
                startActivity(intent);
                return;*/
            }

            showDialog(mCheckOutBoxResponse);

        } else {
            showDialog(mCheckOutBoxResponse);
        }
    }

    private void showDialog(final CheckOutBoxResponse mCheckOutBoxResponse) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.app_name)).
                setCancelable(false)
                .setMessage(mCheckOutBoxResponse.getMessage())
                .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        if (mCheckOutBoxResponse.getStatus() == 1) {

                            Intent intent = new Intent(NewMainViewScreenActivity.this, BoxHistoryActivity.class);
                            startActivity(intent);
                            startActivitySideWiseAnimation();
                        } else {
                            openCameraActivity();
                        }
                    }
                })
                .show();
    }

    /**
     * If locationChanges change lat and long
     *
     * @param location
     */

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Logger.logsInfo(TAG, "latitude : " + latitude);
        Logger.logsInfo(TAG, "longitude : " + longitude);

    }

    /**
     * If connected get lat and long
     */
    private LocationRequest mLocationRequest;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constant.LOCATION_INTERVAL);      // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(Constant.LOCATION_FASTEST_INTERVAL); // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

    // The minimum distance to change Updates in meters
//    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1 * 1000; // 1000 meters

    // The minimum time between updates in milliseconds
//    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 60 * 4; // 4 Hours minute


    private GoogleApiClient mGoogleApiClient;

    /**
     * Show dialog for location using Google API Client
     *
     * @param activity
     */

    @SuppressWarnings("unchecked")
    public void showLocationAlertDialog(final Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(activity)
                        .addConnectionCallbacks(NewMainViewScreenActivity.this)
                        .addOnConnectionFailedListener(NewMainViewScreenActivity.this)
                        .addApi(LocationServices.API).build();
                mGoogleApiClient.connect();
                Object obj = LocationRequest.create();
                ((LocationRequest) (obj)).setPriority(LocationRequest.PRIORITY_LOW_POWER);
                ((LocationRequest) (obj)).setInterval(Constant.LOCATION_INTERVAL);
                ((LocationRequest) (obj)).setFastestInterval(Constant.LOCATION_FASTEST_INTERVAL);
                obj = new LocationSettingsRequest.Builder().addLocationRequest(
                        ((LocationRequest) (obj))).setAlwaysShow(true);

                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        ((LocationSettingsRequest.Builder) (obj)).build())
                        .setResultCallback(
                                new ResultCallback<LocationSettingsResult>() {

                                    @Override
                                    public void onResult(
                                            LocationSettingsResult locationsettingsresult) {
                                        // TODO Auto-generated method stub
                                        Status status;
                                        Log.e("result of yes no",
                                                (new StringBuilder())
                                                        .append("=")
                                                        .append(locationsettingsresult
                                                                .getStatus())
                                                        .toString());
                                        status = locationsettingsresult
                                                .getStatus();
                                        locationsettingsresult
                                                .getLocationSettingsStates();
                                        status.getStatusCode();

                                        try {
                                            status.startResolutionForResult(
                                                    NewMainViewScreenActivity.this, 1000);

                                        } catch (IntentSender.SendIntentException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                    }
                                });
            }
            return;
        } else {
            startActivityForResult(new Intent(
                    "android.settings.LOCATION_SOURCE_SETTINGS"), 1);
            return;

        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATIONS = 123;

    private boolean checkPermission(final Activity context) {
        Logger.logsError(TAG,"checkPermission Called");
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("GeoLocation permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATIONS);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATIONS);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();

                } else {
                    //code for deny
                }
                break;
        }
    }*/

    private void getCurrentLocation() {

        createLocationRequest();
        LocationManager locationManager = (LocationManager) NewMainViewScreenActivity.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;


        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (gps_enabled) {

                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(NewMainViewScreenActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewMainViewScreenActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, new android.location.LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();


                                    Logger.logsInfo(TAG, "currentLat : " + latitude);
                                    Logger.logsInfo(TAG, "currentLong : " + longitude);
                                }

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {

                                }

                                @Override
                                public void onProviderEnabled(String provider) {

                                }

                                @Override
                                public void onProviderDisabled(String provider) {

                                }
                            });
                    Log.d("Network", "Network");
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Logger.logsInfo(TAG, "latitude : " + latitude);
                        Logger.logsInfo(TAG, "longitude : " + longitude);


                    }
                }
            } else {
                showLocationAlertDialog(NewMainViewScreenActivity.this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(NewMainViewScreenActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(NewMainViewScreenActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            if (ActivityCompat.checkSelfPermission(NewMainViewScreenActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(NewMainViewScreenActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            latitude = location.getLatitude();
            longitude = location.getLongitude();


//            Toast.makeText(UpdateProfileScreen.this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void insertNewFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mTitles.add(title);
        }
    }

    @OnClick(R.id.scan_btn)
    void onClickscan_btn(View v) {
        openCameraActivity();
    }

    public void openCameraActivity() {
    /*    Intent intent = new Intent(this, QrScannerActivity.class);
        startActivity(intent);*/

        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setPrompt(getResources().getString(R.string.scanBoxText));

        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {

            if (result.getContents() == null) {

//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {

                showLoadingGif();
                hideKeyboard();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.checkOut(result.getContents(), String.valueOf(latitude), String.valueOf(longitude));
                    }
                }, 3000);


//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Scan Result");
//                builder.setMessage(result.getContents());
//                AlertDialog alert1 = builder.create();
//                alert1.show();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    @Override
    public void handleResult(Result result) {
        // Do something with the result here

        Log.e("handler", result.getText()); // Prints scan results
        Log.e("handler", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

    }
}
