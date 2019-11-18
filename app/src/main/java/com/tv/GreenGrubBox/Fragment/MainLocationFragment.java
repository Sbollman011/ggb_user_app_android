package com.tv.GreenGrubBox.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tv.GreenGrubBox.BaseClasses.BaseFragment;
import com.tv.GreenGrubBox.Callbacks.SearchDataCallback;
import com.tv.GreenGrubBox.Dialogs.LocationInfoDialog;
import com.tv.GreenGrubBox.Fragment.MapViewVendorFragment.MapViewVendorFragment;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListFragment;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.adapter.LocationItemAdapter;
import com.tv.GreenGrubBox.data.modal.MainLocationDatum;
import com.tv.GreenGrubBox.utils.AppConstants;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 25/1/18.
 */

public class MainLocationFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = MainLocationFragment.class.getSimpleName();

    @BindView(R.id.foodlist_rv)
    RecyclerView foodlist_rv;


    @BindView(R.id.container_map)
    FrameLayout container_map;

    @BindView(R.id.search_pb)
    ProgressBar search_pb;

    @BindView(R.id.card_cv)
    CardView card_cv;

    @BindView(R.id.search_box_rl)
    RelativeLayout search_box_rl;

    @BindView(R.id.map_diraction_iv)
    ImageView map_diraction_iv;

    @BindView(R.id.map_location_rl)
    RelativeLayout map_location_rl;

    @BindView(R.id.map_location_iv)
    ImageView map_location_iv;

    @BindView(R.id.search_for_location_et)
    EditText search_for_location_et;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private boolean ismap = true;

    private List<MainLocationDatum> mainLocationDatumList = new ArrayList<>();

    private LocationItemAdapter mAdapter;
    //    private SupportMapFragment fragment;
    private GoogleMap googleMap;

//    private android.support.v4.app.FragmentManager mFragmentManager;

    @OnClick(R.id.map_diraction_iv)
    void onClickMapDirectionToCurrentLocation(View v) {
        /*boolean isCheck = checkPermission(getActivity());
        if (isCheck) {
            mapDirectionToCurrentLocation();
        }*/

        if (mapViewVendorFragment != null) {
            mapViewVendorFragment.mapDirectionToCurrentLocation();
        }

    }


    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }

    private void mapDirectionToCurrentLocation() {

        if (googleMap != null && latitude != 0.0 && longitude != 0) {

            LatLng coordinate = new LatLng(latitude, longitude); //Store these lat lng values somewhere. These should be constant.
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    coordinate, 9);
            this.googleMap.animateCamera(location);
        }

    }

    @OnClick(R.id.map_location_iv)
    void openMap(View view) {
        if (ismap) {
            showListView();
        } else {
            showMapView();

        }
    }

    private void showListView() {
        ismap = false;
//        Logger.logsError(TAG, "showListView Called with map status ? : " + ismap);

        map_location_iv.setImageResource(R.drawable.map_location_list_icon);
//            card_cv.setVisibility(View.VISIBLE);
        map_diraction_iv.setVisibility(View.GONE);


        List<Fragment> fragmentList = getFragmentManager().getFragments();
// You might have to access all the fragments by their tag,
// in which case just follow the line below to remove the fragment
        if (fragmentList == null || getFragmentManager() == null || getFragmentManager().getFragments() == null || fragmentList.size() == 0) {
            // code that handles no existing fragments
            Logger.logsError(TAG, "fragmentList is NULL while showing list ");
            return;
        }


        for (Fragment frag : fragmentList) {
            // To save any of the fragments, add this check
            // a tag can be added as a third parameter to the fragment when you commit it
            if (frag != null && frag.getTag() != null && frag.getTag().equalsIgnoreCase("MapViewVendorFragment")) {
                getFragmentManager().beginTransaction().remove(frag).commit();
//                break;
            }

        }
    }

    private void showMapView() {

        ismap = true;
//        Logger.logsError(TAG, "showMapView Called with map status ? : " + ismap);

//            card_cv.setVisibility(View.GONE);
        map_location_iv.setImageResource(R.drawable.locations_list);
        map_diraction_iv.setVisibility(View.VISIBLE);
//            container_map.setVisibility(View.VISIBLE);
        openMapLocation();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainlocation_screen, container, false);
        ButterKnife.bind(this, view);

        Logger.logsError(TAG, "onCreateView Called");

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initView();
                    setupViewPager(viewPager);
                    tabs.setupWithViewPager(viewPager);
                    setupTabText(tabs);
                    tabs.getTabAt(0).select();




                }
            }, 50);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showMapView();
                }
            }, 500);
        }
    }

    private void setupTabText(TabLayout tabs) {

        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabOne.setText(getResources().getString(R.string.vendors));
        tabOne.setTypeface(CommonUtils.setRegularFont(getActivity()));
        tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getResources().getString(R.string.dropstations));
        tabTwo.setTypeface(CommonUtils.setRegularFont(getActivity()));
        tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabThree.setText(getResources().getString(R.string.both));
        tabThree.setTypeface(CommonUtils.setRegularFont(getActivity()));
        tabs.getTabAt(2).setCustomView(tabThree);

    }


    VendorListFragment mVendorMapFragment;
    VendorListFragment mVendorMapFragmentDS;
    VendorListFragment mVendorMapFragmentBoth;

    private ViewPagerAdapter viewPagerAdapter;

    private void setupViewPager(ViewPager viewPager) {
        Logger.logsError(TAG, "setupViewPager Called ");
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        mVendorMapFragment = VendorListFragment.newInstance(null, new SearchDataCallback() {
            @Override
            public void searchCompleted() {
                search_pb.setVisibility(View.GONE);

            }
        });
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.LOCATION_TYPE, "1");// vendor
        mVendorMapFragment.setArguments(mBundle);
        viewPagerAdapter.addFrag(mVendorMapFragment, getResources().getString(R.string.vendors));

        mVendorMapFragmentDS = VendorListFragment.newInstance(null, new SearchDataCallback() {
            @Override
            public void searchCompleted() {
                search_pb.setVisibility(View.GONE);
            }
        });
        Bundle mBundleDS = new Bundle();
        mBundleDS.putString(Constant.LOCATION_TYPE, "2");// Drop stations
        mVendorMapFragmentDS.setArguments(mBundleDS);
        viewPagerAdapter.addFrag(mVendorMapFragmentDS, getResources().getString(R.string.dropstations));

        mVendorMapFragmentBoth = VendorListFragment.newInstance(null, new SearchDataCallback() {
            @Override
            public void searchCompleted() {
                search_pb.setVisibility(View.GONE);
            }
        });
        Bundle mBundleBoth = new Bundle();
        mBundleBoth.putString(Constant.LOCATION_TYPE, "3");// Both
        mVendorMapFragmentBoth.setArguments(mBundleBoth);
        viewPagerAdapter.addFrag(mVendorMapFragmentBoth, getResources().getString(R.string.both));
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(viewPagerAdapter);
        Logger.logsError(TAG, "setupViewPager after setAdapter ");

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        @Override
        public int getItemPosition(Object object) {
            Logger.logsError(TAG, "getItemPosition Called");

            return super.getItemPosition(object);
        }


        public ViewPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);
            Logger.logsError(TAG, "ViewPagerAdapter Called");

         /*   if (fragmentManager.getFragments() != null) {
                Logger.logsError(TAG, "Prev fragments : " + fragmentManager.getFragments().size());
                fragmentManager.getFragments().clear();
                notifyDataSetChanged();
            } else {
                Logger.logsError(TAG,"No prev frag found");
            }*/

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for (Fragment fragment : mFragmentList) {
                transaction.remove(fragment);
            }
            mFragmentList.clear();
            transaction.commit();
            notifyDataSetChanged();

            if (viewPager.getAdapter()!=null){
                viewPager.getAdapter().notifyDataSetChanged();
            }



        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Logger.logsError(TAG, "instantiateItem Called");

            return super.instantiateItem(container, position);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Logger.logsError(TAG, "destroyItem Called");

            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            Logger.logsError(TAG, "getItem Called");
            return mFragmentList.get(position);
        }

        public void clearAll() {
           /* mFragmentList.clear();
            mFragmentTitleList.clear();
            notifyDataSetChanged();*/
        }

        @Override
        public int getCount() {
//            Logger.logsError(TAG,"getCount Called : "  + mFragmentList.size());

            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

            notifyDataSetChanged();
//            Logger.logsError(TAG, "addFrag Called");

        }


        @Override
        public CharSequence getPageTitle(int position) {
//            Logger.logsError(TAG, "getPageTitle Called : " + mFragmentTitleList.get(position));

            return mFragmentTitleList.get(position);
        }
    }

    private void initView() {
        mAdapter = new LocationItemAdapter(mainLocationDatumList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        foodlist_rv.setLayoutManager(mLayoutManager);
        foodlist_rv.setItemAnimator(new DefaultItemAnimator());
        foodlist_rv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });
        foodlist_rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        foodlist_rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        LocationInfoDialog mFoodInfoDialog = new LocationInfoDialog();
                        getFragmentManager().beginTransaction()
                                .add(R.id.container_map, mFoodInfoDialog, "LocationInfoDialog")
                                .addToBackStack(null)
                                .commit();
//                        mFoodInfoDialog.show(getFragmentManager(), TAG);
                    }
                })
        );

        setCustomLayoutToTabs();
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATIONS = 123;

    private double latitude = 0;
    private double longitude = 0;

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
                showLocationAlertDialog(getActivity());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
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
                break;
        }
    }

    private void setCustomLayoutToTabs() {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getActivity()).inflate(R.layout.main_location_tabs_layout, tabs, false);
            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setTypeface(CommonUtils.setRegularFont(getActivity()));
            tabTextView.setTextSize(16);
            tabTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
        }
    }


    MapViewVendorFragment mapViewVendorFragment;

    private void openMapLocation() {

        try {
            mapViewVendorFragment = new MapViewVendorFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container_map, mapViewVendorFragment, "MapViewVendorFragment")
                    .addToBackStack(null)
                    .commit();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @SuppressLint("MissingPermission")
    private void showMap() {
        if (googleMap == null) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                new LatLng(40.273502, -86.126976), 9);
        this.googleMap.animateCamera(location);
//        googleMap.clear();
        googleMap.clear();
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            public void onMapLoaded() {
                AppConstants.setStictModePermission();
                if (googleMap == null)
                    return;
            }
        });

        ArrayList<LatLng> markersArray = new ArrayList<LatLng>();
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
        }
    }


    private Timer timer = new Timer();
    private final long DELAY = 500;

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.logsError(TAG, "onDestroy Called");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.logsError(TAG, "onDestroyView Called");
//        viewPager.setAdapter(null);
        showListView();
        /*if (viewPagerAdapter != null) {
            viewPagerAdapter.clearAll();
            viewPagerAdapter.notifyDataSetChanged();
            Logger.logsError(TAG, "onDestroyView Called Fragments removed");

        }*/
    }

    @Override
    public void onStart() {
        super.onStart();
        search_for_location_et.setText("");

    }

    @Override
    protected void setUp(View view) {
//        mFragmentManager = getFragmentManager();


       /* mFragmentManager.removeOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                ismap = false;
                map_location_iv.setImageResource(R.drawable.map_location_list_icon);
//            card_cv.setVisibility(View.VISIBLE);
                map_diraction_iv.setVisibility(View.GONE);
            }
        });*/

        search_for_location_et.addTextChangedListener(new TextWatcher() {
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
//                final String mInput = editable.toString().trim();
                final String mInput = search_for_location_et.getText().toString().trim();
                if (mInput.isEmpty()) {
                    hideKeyboard();
                }

                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       if (getActivity() == null) {
                                           return;
                                       }
                                       getActivity().runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               if (ismap) {
                                                   if (mapViewVendorFragment != null) {
                                                       mapViewVendorFragment.search(mInput);
                                                   }
                                               } else {
                                                   if (!mInput.isEmpty()) {
                                                       search_pb.setVisibility(View.VISIBLE);
                                                   }
                                                   // this is your adapter that will be filtered
                                                   PagerAdapter pagerAdapter = (PagerAdapter) viewPager
                                                           .getAdapter();
//                                                   for (int i = 0; i < pagerAdapter.getCount(); i++) {
                                                   Fragment viewPagerFragment = (Fragment) viewPager
                                                           .getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());

                                                   if (viewPagerFragment != null && viewPagerFragment.isAdded()) {
                                                       switch (viewPager.getCurrentItem()) {
                                                           case 0:
                                                               mVendorMapFragment = (VendorListFragment) viewPagerFragment;
                                                               mVendorMapFragment.searchData(mInput);
                                                               break;
                                                           case 1:
                                                               mVendorMapFragmentDS = (VendorListFragment) viewPagerFragment;
                                                               mVendorMapFragmentDS.searchData(mInput);

                                                               break;
                                                           case 2:
                                                               mVendorMapFragmentBoth = (VendorListFragment) viewPagerFragment;
                                                               mVendorMapFragmentBoth.searchData(mInput);

                                                               break;
                                                           default:
                                                               break;
                                                       }
                                                   }
//                                                   }
                                                  /* switch (viewPager.getCurrentItem()) {
                                                       case 0:
                                                           mVendorMapFragment.searchData(mInput);
                                                           break;
                                                       case 1:
                                                           mVendorMapFragmentDS.searchData(mInput);

                                                           break;
                                                       case 2:
                                                           mVendorMapFragmentBoth.searchData(mInput);

                                                           break;
                                                       default:
                                                           break;
                                                   }*/
                                               }

                                           }


                                       });
                                   }

                               },
                        DELAY
                );

            }
        });


        setFonts();

    }

    private void setFonts() {

        search_for_location_et.setTypeface(CommonUtils.setRegularFont(getActivity()));
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

//            Toast.makeText(UpdateProfileScreen.this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
