package com.tv.GreenGrubBox.Fragment.VendorListFragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tv.GreenGrubBox.BaseClasses.BaseFragment;
import com.tv.GreenGrubBox.Callbacks.SearchDataCallback;
import com.tv.GreenGrubBox.Dialogs.LocationInfoDialog;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.adapter.LocationItemAdapter;
import com.tv.GreenGrubBox.data.modal.MainLocationDatum;
import com.tv.GreenGrubBox.data.modal.MainLocationModal;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.RecyclerItemClickListener;
import com.tv.GreenGrubBox.utils.Views.EndlessRecyclerScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 7/2/18.
 */

public class VendorListFragment extends BaseFragment implements VendorListMvpView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, SwipeRefreshLayout.OnRefreshListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATIONS = 123;
    private static final String TAG = VendorListFragment.class.getSimpleName();
    // The minimum distance to change Updates in meters
//    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1 * 1000; // 1000 meters
    // The minimum time between updates in milliseconds
//    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 60 * 4; // 4 Hours minute
    @Inject
    public VendorListMvpPresenter<VendorListMvpView, VendorListMvpInteractor> mPresenter;
    @BindView(R.id.foodlist_rv)
    RecyclerView foodlist_rv;
    @BindView(R.id.swipe_to_ref)
    SwipeRefreshLayout swipe_to_ref;
    @BindView(R.id.nodatafound_tv)
    TextView nodatafound_tv;
    @Inject
    LocationItemAdapter mAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    List<MainLocationDatum> mainLocationDatumList;
    boolean isLocation = false;
    private EndlessRecyclerScrollListener endlessRecyclerScrollListener;
    private SearchDataCallback mSearchDataCallback;
    private int mCurrentPage = 1;
    private int pageSize = 20;
    private String search = "";
    private String type = "";
    private double latitude = 0;
    private double longitude = 0;
    /**
     * If connected get lat and long
     */
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    public static VendorListFragment newInstance(Bundle mBundle, SearchDataCallback mSearchDataCallback) {

        VendorListFragment mVendorListFragment = new VendorListFragment();

        mVendorListFragment.mSearchDataCallback = mSearchDataCallback;

        return mVendorListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vendor_list_fragment, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        Logger.logsError(TAG, "onCreateView Called");
        return view;
    }

    @Override
    protected void setUp(View view) {
        mainLocationDatumList = new ArrayList<>();
        swipe_to_ref.setEnabled(true);
        swipe_to_ref.setOnRefreshListener(this);
        swipe_to_ref.setColorSchemeResources(R.color.colorPrimaryDark);
        initView();
        setFonts();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.logsError(TAG, "setUserVisibleHint Called");

        if (isVisibleToUser) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    mPresenter.onAttach(VendorListFragment.this);

                    if (getArguments() != null) {
                        if (getArguments().containsKey(Constant.LOCATION_TYPE)) {
                            type = getArguments().getString(Constant.LOCATION_TYPE);
                        }
                    }

                    boolean result = checkPermission(getActivity());
                    if (result) {
                        getCurrentLocation();
                    }
                    callWS(false);
                }
            }, 50);
        }
    }

    private void initView() {

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        foodlist_rv.setLayoutManager(mLayoutManager);
        foodlist_rv.setItemAnimator(new DefaultItemAnimator());
        foodlist_rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });

        foodlist_rv.setAdapter(mAdapter);
        endlessRecyclerScrollListener = new EndlessRecyclerScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (mainLocationDatumList == null || mainLocationDatumList.size() == 0) {
                    return;
                }
                if (mainLocationDatumList != null) {
                    if (mainLocationDatumList.size() < pageSize) {
                        return;
                    }
                }
                Logger.logsError(TAG, "OnLoadMore Called :" + currentPage);
                mCurrentPage = currentPage;
                callWS(true);
            }
        };
        foodlist_rv.addOnScrollListener(endlessRecyclerScrollListener);
        endlessRecyclerScrollListener.setVISIBLE_THRESHOLD(8);
        foodlist_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (mAdapter.getAllList().size() == 0) {
                            return;
                        }

                        Logger.logsError(TAG, "mainLocationDatumList++++" + mAdapter.getAllList());
                        MainLocationDatum bean = mAdapter.getAllList().get(position);

                        if (bean == null) {
                            return;
                        }

                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable(Constant.LOCATION_DATUM, bean);
                        LocationInfoDialog mFoodInfoDialog = new LocationInfoDialog();
                        mFoodInfoDialog.setArguments(mBundle);
                        getFragmentManager().beginTransaction()
                                .add(R.id.container_map, mFoodInfoDialog, "LocationInfoDialog")
                                .addToBackStack(null)
                                .commit();

//                        mFoodInfoDialog.show(getFragmentManager(), TAG);
                    }

                })
        );
    }

    private void setFonts() {

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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message).setPositiveButton(getResources().getString(R.string.okText), dialogClickListener).show();
    }

    @Override
    public void vendorsDataPopulate(MainLocationModal mMainLocationDatumLIst) {

        if (mSearchDataCallback != null) {
            mSearchDataCallback.searchCompleted();
        }
        swipe_to_ref.setRefreshing(false);

        if (mMainLocationDatumLIst == null) {

            return;
        }

        if (mCurrentPage == 1) {
            if (this.mainLocationDatumList != null) {
                this.mainLocationDatumList.clear();
            }

            mAdapter.clearList();
        }

        if (mMainLocationDatumLIst.getData() == null || mMainLocationDatumLIst.getData().size() == 0) {
            mCurrentPage = 1;
            showMessage(R.string.novendorsforyourLocationTxt);
            return;

        }

        if (mMainLocationDatumLIst.getStatus() == 0) {
            alertMessageForStatus(mMainLocationDatumLIst.getMessage());
            return;
        }

        if (mMainLocationDatumLIst.getData().size() > 0) {
            nodatafound_tv.setVisibility(View.GONE);
//            Collections.sort(eventList, new CustomComparator());
            swipe_to_ref.setRefreshing(false);

            this.mainLocationDatumList = mMainLocationDatumLIst.getData();

            mAdapter.addItems(this.mainLocationDatumList);
        }
        Logger.logsError(TAG, "eventList SUGGESTED Size  : " + this.mainLocationDatumList.size());
        swipe_to_ref.setRefreshing(false);
      /*  if (mMainLocationDatumLIst.getStatus() == 1) {

            if (mCurrentPage == 1) {
                if (this.mainLocationDatumList != null) {
                    this.mainLocationDatumList.clear();
                }
                mAdapter.clearList();

                if (mMainLocationDatumLIst.getData() == null || mMainLocationDatumLIst.getData().size() == 0) {
                    mAdapter.clearList();
                    showMessage(R.string.novendorsforyourLocationTxt);
                    return;
                }
            }
            Logger.logsError(TAG, "Vendorlist Size : " + mMainLocationDatumLIst.getData().size());

//            this.mainLocationDatumList = mMainLocationDatumLIst.getData();
            if (mMainLocationDatumLIst.getData().size()>0){
                this.mainLocationDatumList.addAll(mMainLocationDatumLIst.getData());
                mAdapter.addItems(this.mainLocationDatumList);
            }

        } else {
            showMessage(R.string.novendorsforyourLocationTxt);

        }*/
    }

    /**
     * If locationChanges change lat and long
     *
     * @param location
     */

    @Override
    public void onLocationChanged(Location location) {
        Logger.logsError(TAG, "onLocationChanged Called");

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Logger.logsInfo(TAG, "latitude : " + latitude);
        Logger.logsInfo(TAG, "longitude : " + longitude);

        callWS(true);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Logger.logsError(TAG, "onConnected Called");

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
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

            callWS(true);
//            Toast.makeText(UpdateProfileScreen.this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void getCurrentLocation() {
        Logger.logsError(TAG, "getCurrentLocation Called");


        createLocationRequest();

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;


        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (gps_enabled) {
                Logger.logsError(TAG, "gps_enabled Called");


                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                        Logger.logsError(TAG, "Permission denied Called");

                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, new android.location.LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    Logger.logsError(TAG, "onLocationChanged Called");


                                    Logger.logsInfo(TAG, "currentLat : " + latitude);
                                    Logger.logsInfo(TAG, "currentLong : " + longitude);
                                    callWS(true);
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
                    Logger.logsError(TAG, "Network");
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Logger.logsInfo(TAG, "latitude : " + latitude);
                        Logger.logsInfo(TAG, "longitude : " + longitude);

                        callWS(true);

                    }
                }
            } else {
                Logger.logsError(TAG, "gps NOT enabled Called");
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                showLocationAlertDialog(getActivity());
            }
        } catch (Exception ex) {
            Logger.logsError(TAG, "gps_enabled Called Exception : " + ex.getMessage());

            ex.printStackTrace();
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constant.LOCATION_INTERVAL);      // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(Constant.LOCATION_FASTEST_INTERVAL); // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    /**
     * Show dialog for location using Google API Client
     *
     * @param activity
     */


    @SuppressWarnings("unchecked")
    public void showLocationAlertDialog(final Activity activity) {
        Logger.logsError(TAG, "showLocationAlertDialog Called");

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            if (mGoogleApiClient == null) {
                Logger.logsError(TAG, "showLocationAlertDialog Called mGoogleApiClient is NULL");

                mGoogleApiClient = new GoogleApiClient.Builder(activity)
                        .addConnectionCallbacks(VendorListFragment.this)
                        .addOnConnectionFailedListener(VendorListFragment.this)
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
                                            Logger.logsError(TAG, "showLocationAlertDialog Called onResult Called");

                                            status.startResolutionForResult(
                                                    getActivity(), 1000);

                                        } catch (IntentSender.SendIntentException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                    }
                                });
            } else {
                Logger.logsError(TAG, "showLocationAlertDialog Called mGoogleApiClient is NOT NULL");

            }
//                return;
        } else {
            Logger.logsError(TAG, "showLocationAlertDialog Called API Level is Less than 19");

            startActivityForResult(new Intent(
                    "android.settings.LOCATION_SOURCE_SETTINGS"), 1);
            return;

        }

    }

    private boolean checkPermission(final Activity context) {
        Logger.logsError(TAG,"checkPermission Called ");
        int currentAPIVersion = Build.VERSION.SDK_INT;

        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                /*if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(false);
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
//                    alert.show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATIONS);
                }*/
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Logger.logsError(TAG, "onRequestPermissionsResult called");
                    getCurrentLocation();

                } else {
                    //code for deny
                }
                break;
        }
    }

    private void callWS(boolean isProgress) {
        Logger.logsError(TAG, "Call ws called");
        if (mCurrentPage == 1) {
            if (endlessRecyclerScrollListener != null) {
                // Fragment stopped during loading data. Allow new loading on return.
                endlessRecyclerScrollListener.clean();
            }
        }
        if (mPresenter != null) {
            Logger.logsError(TAG, "Call ws called mPresenter is NOT null");

            mPresenter.getVendors(isProgress, mCurrentPage, pageSize, search, String.valueOf(latitude), String.valueOf(longitude), type);
        } else {

            Logger.logsError(TAG, "Call ws called mPresenter is NULL  " + mLayoutManager);
        }
    }

    @Override
    public void onStop() {
        // Disconnect the client.
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }

        }
        if (endlessRecyclerScrollListener != null) {
            // Fragment stopped during loading data. Allow new loading on return.
            endlessRecyclerScrollListener.resetLoading();
        }
        super.onStop();
    }

    public void searchData(String mInput) {
        search = mInput;
        callWS(false);
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        callWS(false);
    }
}