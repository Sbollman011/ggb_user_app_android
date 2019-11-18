package com.tv.GreenGrubBox.Fragment.MapViewVendorFragment;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.underscore.$;
import com.github.underscore.Predicate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tv.GreenGrubBox.BaseClasses.BaseFragment;
import com.tv.GreenGrubBox.Callbacks.OpenLocationDetailsCallback;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.MainLocationDatum;
import com.tv.GreenGrubBox.data.modal.MainLocationModal;
import com.tv.GreenGrubBox.utils.AppConstants;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by tv-1 on 07/02/18.
 */

public class VendorMapFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        VendorMapMvpView, GoogleMap.OnMarkerClickListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATIONS = 123;
    private static final String TAG = VendorMapFragment.class.getSimpleName();
    // The minimum distance to change Updates in meters
//    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1 * 1000; // 1000 meters
    // The minimum time between updates in milliseconds
//    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 60 * 4; // 4 Hours minute
    public static boolean isCheckPermissionCalled = false;
    @Inject
    VendorMapMvpPresenter<VendorMapMvpView, VendorMapMvpInteractor> mPresenter;
    List<MainLocationDatum> mainLocationDatumList = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 20;
    private String search = "";
    private String type = "";
    private OpenLocationDetailsCallback mOpenLocationDetailsCallback;
    private GoogleMap googleMap;
    private Hashtable<String, MainLocationDatum> markers;
    private double latitude = 0;
    private double longitude = 0;
    /**
     * If connected get lat and long
     */
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    public static VendorMapFragment newInstance(Bundle mBundle, OpenLocationDetailsCallback mOpenLocationDetailsCallback) {
        VendorMapFragment mVendorMapFragment = new VendorMapFragment();
        mVendorMapFragment.mOpenLocationDetailsCallback = mOpenLocationDetailsCallback;
        return mVendorMapFragment;
    }

    @Override
    protected void setUp(View view) {


    }

    private  boolean isResumeCalled = false;
    @Override
    public void onResume() {
        super.onResume();
        Logger.logsError(TAG,"onResume Called");
        isResumeCalled = true;


        boolean result = checkPermission(getActivity());
        if (result) {
            showMap();
            getCurrentLocation();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.logsError(TAG,"setUserVisibleHint Called");
        if (isVisibleToUser) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openMap();
                }
            }, 50);
        }
    }

    private void openMap() {
        if (getArguments() != null) {
            if (getArguments().containsKey(Constant.LOCATION_TYPE)) {
                type = getArguments().getString(Constant.LOCATION_TYPE);
            }
        }


        if (googleMap == null) {
            ((SupportMapFragment) this.getChildFragmentManager().findFragmentById(
                    R.id.map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mGoogleMap) {

                    googleMap = mGoogleMap;
                    googleMap.setOnMarkerClickListener(VendorMapFragment.this);
                    boolean result = checkPermission(getActivity());
                    if (result) {
                        showMap();
                        getCurrentLocation();
                    }
                }
            });


        }
/*
        fragment = SupportMapFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.container_framelayout, fragment, "VendorMapFragment")
                .addToBackStack(null)
                .commit();
        fragment.getMapAsync(
                new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        VendorMapFragment.this.googleMap = googleMap;
                        //   AppDelegate.LogT("Google Map ==" + DriverOntheWayFragment.this.googleMap);
                        boolean result = checkPermission(getActivity());
                        if (result) {
                            showMap();
                            getCurrentLocation();
                        }


                    }
                }
        );*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vendor_map_fragment, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        return view;
    }

    private void getCurrentLocation() {

        createLocationRequest();

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;


        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (gps_enabled) {

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
                    Log.d("Network", "Network");
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
                showLocationAlertDialog(getActivity());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void callWS(final boolean isProgress) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getVendors(isProgress, currentPage, pageSize, search, String.valueOf(latitude), String.valueOf(longitude), type);
            }
        }, 500);

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constant.LOCATION_INTERVAL);      // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(Constant.LOCATION_FASTEST_INTERVAL); // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

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
                        .addConnectionCallbacks(VendorMapFragment.this)
                        .addOnConnectionFailedListener(VendorMapFragment.this)
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
                                                    getActivity(), 1000);

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

    private boolean checkPermission(final Activity context) {
        Logger.logsError(TAG, "checkPermission Called");
        if (isCheckPermissionCalled) {
            return false;
        }
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
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
                    alert.show();

                    isCheckPermissionCalled = true;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    showMap();
                    getCurrentLocation();

                } else {

                    //code for deny
                }
                isCheckPermissionCalled = false;
                break;
        }
    }


    @SuppressLint("MissingPermission")
    private void showMap() {
        if (googleMap == null) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
//        googleMap.getUiSettings().setMapToolbarEnabled(false);
//        googleMap.getUiSettings().setZoomControlsEnabled(false);
//        googleMap.getUiSettings().setCompassEnabled(false);
//        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        googleMap.clear();
        googleMap.clear();
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            public void onMapLoaded() {
                AppConstants.setStictModePermission();
                if (googleMap == null)
                    return;
            }
        });

       /* ArrayList<LatLng> markersArray = new ArrayList<LatLng>();
        markersArray.add(new LatLng(40.273502, -86.126976));
        markersArray.add(new LatLng(38.573936, -92.603760));
        markersArray.add(new LatLng(27.994402, -81.760254));
        markersArray.add(new LatLng(39.876019, -117.224121));
        markersArray.add(new LatLng(45.367584, -68.972168));
        markersArray.add(new LatLng(44.182205, -84.506836));
        markersArray.add(new LatLng(33.247875, -83.441162));
        markersArray.add(new LatLng(21.289373, -157.917480));
        markersArray.add(new LatLng(66.160507, -153.369141));

        for (int i = 0; i < markersArray.size(); i++) {

            createMarker(markersArray.get(i).latitude, markersArray.get(i).longitude, "", "", R.drawable.location_green_leaf_icon);
        }*/
    }


    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {
        Logger.logsError(TAG, "Lat Lng Create Marker : " + latitude + " : " + longitude);

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }

    public void mapDirectionToCurrentLocation() {

        boolean isCheck = checkPermission(getActivity());
        if (isCheck) {
            mapDirectionToCurrentLocationAfterPer();
        }
    }

    private void mapDirectionToCurrentLocationAfterPer() {

        Logger.logsError(TAG,"mapDirectionToCurrentLocationAfterPer Called");

        if (googleMap != null && latitude != 0.0 && longitude != 0) {
            Logger.logsError(TAG,"mapDirectionToCurrentLocationAfterPer Called in IFFFFFF");

            LatLng coordinate = new LatLng(latitude, longitude); //Store these lat lng values somewhere. These should be constant.
            final CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    coordinate, 18);

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    googleMap.animateCamera(location);
                }
            });

        }
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

        callWS(true);

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
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
            Logger.logsError(TAG,"latitude : " + latitude);
            Logger.logsError(TAG,"longitude : " + longitude);

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

    @Override
    public void vendorsDataPopulate(MainLocationModal mMainLocationDatumLIst) {
        if (mMainLocationDatumLIst == null) {
            return;
        }
        if (mMainLocationDatumLIst.getStatus() == 1) {
            if (mMainLocationDatumLIst.getData() == null || mMainLocationDatumLIst.getData().size() == 0) {
                showMessage(R.string.novendorsforyourLocationTxt);
            }

            if (currentPage == 1) {
                if (this.mainLocationDatumList != null) {
                    this.mainLocationDatumList.clear();
                }
                if (this.googleMap != null) {
                    this.googleMap.clear();
                }

            }

            this.mainLocationDatumList = mMainLocationDatumLIst.getData();
            mapDirectionToCurrentLocationAfterPer();
            markers = new Hashtable<>();


            $.find(this.mainLocationDatumList, new Predicate<MainLocationDatum>() {
                @Override
                public Boolean apply(MainLocationDatum arg) {
                    if (arg.getVendorGeo() != null) {
                        int img;
                        if (arg.getType() == 2) {

                            img = R.drawable.location_drop_box_icon;
                        } else {
                            img = R.drawable.location_green_leaf_icon;
                        }
                        Marker mMarker = createMarker(
                                arg.getVendorGeo().getCoordinates().get(1),
                                arg.getVendorGeo().getCoordinates().get(0),
                                arg.getBusinessName(),
                                arg.getDescription(),
                                img

                        );
                        Logger.logsError(TAG, "Marker Id While Adding : " + mMarker.getId());
                        Logger.logsError(TAG, "Marker Id While Adding pageSize : " + currentPage);
                        markers.put(mMarker.getId(), arg);

                    }


                    return false;
                }
            });
        } else {
            // alertMessageForStatus(mMainLocationDatumLIst.getMessage());
        }
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
    public boolean onMarkerClick(Marker marker) {
        Logger.logsError(TAG, "Marker Id While Click : " + marker.getId());

//        Log.i("Position of arraylist", pos+"");
        Set<String> keys = markers.keySet();
        for (String key : keys) {
            Logger.logsError(TAG, "Marker Id While Click key : " + key);

            if (marker.getId().equalsIgnoreCase(key)) {
                Logger.logsError(TAG, "Marker Id While MATCH ");
                MainLocationDatum bean = markers.get(key);
                Logger.logsError(TAG, "Name : " + bean.getBusinessName());
                if (mOpenLocationDetailsCallback != null) {
                    mOpenLocationDetailsCallback.openLocationDetails(bean);
                }
                break;

            } else {
                Logger.logsError(TAG, "Marker Id While NOT MATCH ");
            }
        }
        return false;
    }

    public void searchData(String mInput) {
        search = mInput;
        callWS(false);
    }
}

