/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.tv.GreenGrubBox.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tv.GreenGrubBox.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Shoeb on 27/01/17.
 */

public final class CommonUtils {

    public static String getFBPhotoUrlFromID(long id) {
        return "https://graph.facebook.com/" + id + "/picture?type=large";
    }

    /**
     * Convert ms to time in AM/PM
     */
    public static String getTimeFromMillisecondsinAM_PM(long milliSeconds) {

        // Create a DateFormatter object for displaying date in specified format.
        String dateFormat = "hh:mm a";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

    // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());

    }

    /*Convert String startTime to long*/
    public static long getDateInMillisFromFB(String srcDate) {
        try {
            SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            long dateInMillis = 0;
            try {
                Date date = desiredFormat.parse(srcDate);
                dateInMillis = date.getTime();
                return dateInMillis;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static String getTimeZone() {

        return TimeZone.getDefault().getID();
    }

    public static String displayTimeZone(TimeZone tz) {

        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
                - TimeUnit.HOURS.toMinutes(hours);
        // avoid -4:-30 issue
        minutes = Math.abs(minutes);

        String result = "";
        if (hours > 0) {
            result = String.format("GMT+%d:%02d", hours, minutes);
        } else {
            result = String.format("GMT%d:%02d", hours, minutes);
        }

        return result;

    }

    public static int getDeviceWidth(Context context) {
        int widthDevice = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        widthDevice = metrics.widthPixels;
        return widthDevice;
    }

    public static int getDeviceHeight(Context context) {
        int heightDevice = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        heightDevice = metrics.heightPixels;
        return heightDevice;
    }

    /**
     * Get Current date
     */
    public static long getTimeInMillis() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    public static String loadCountryJSONFromAsset(Activity mActivity) {
        String json = null;
        try {
            InputStream is = mActivity.getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static final String TAG = CommonUtils.class.getSimpleName();

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static String printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);

                return hashKey;
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
            return "";
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
            return "";
        }
        return "";

    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        return progressDialog;
    }

    public static ProgressDialog showLoadingDialogGIF(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyDialogTheme);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        ImageView pb_loading;

        progressDialog.setContentView(R.layout.progress_dialog_imageview);
        pb_loading = progressDialog.findViewById(R.id.pb_loading);

        Glide.with(context)
                .load(R.raw.ggb_gif)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
//                .into(pb_loading);

                .into(new DrawableImageViewTarget(pb_loading) {
                    @Override
                    public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource instanceof GifDrawable) {
                            ((GifDrawable) resource).setLoopCount(1);
                        }
                        super.onResourceReady(resource, transition);
                    }
                });

        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceTokenFromFCM() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Minimum eight characters, at least one letter, one number and one special character:
     */
    public static boolean isValidPassword(String mPassword) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN =
                "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(mPassword);
        return matcher.matches();
    }

    private static Gson gson;

    /**
     * Add specific parsing to gson
     *
     * @return new instance of {@link Gson}
     */
    public static Gson getGsonParser() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.create();
        }
        return gson;
    }

    /**
     * getMapUrlStatic
     *
     * @return URL of Map with Marker.
     */
    public static String getMapUrlStatic(double lat, double lng, int height, int width, float zoom) {
        if (lat == 0 || lng == 0) {
            return "";
        }

        float mZoom = 20;
        if (zoom > 0) {
            mZoom = zoom;
        }

        return "http://maps.googleapis.com/maps/api/staticmap?zoom=" + mZoom + "&size=" + width + "x" + height + "&markers=size:large|color:red|" +
                lat + "," + lng + "&sensor=false";
    }

    /**
     * Convert ms to Month
     */
    public static String getMonthFromMilliseconds(long milliseconds) {

        Calendar c = Calendar.getInstance();
//Set time in milliseconds
        c.setTimeInMillis(milliseconds);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");

        int mMonth = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, mMonth);
        String month_name = month_date.format(c.getTime());
        return month_name;
    }

    /**
     * Convert ms to Date Opening & Closing Hours
     */
    public static String getHHMMSS(long milliseconds) {

        Calendar c = Calendar.getInstance();
//Set time in milliseconds
        c.setTimeInMillis(milliseconds);
        SimpleDateFormat month_date = new SimpleDateFormat("HH:mm:ss");

        return month_date.format(c.getTime());
    }

    /**
     * Convert ms to Day
     */
    public static String getDayFromMilliseconds(long milliseconds) {

        Calendar c = Calendar.getInstance();
//Set time in milliseconds
        c.setTimeInMillis(milliseconds);

        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DecimalFormat formatter = new DecimalFormat("00");
        return formatter.format(mDay);
    }

    /**
     * Convert ms to Day with full name
     */
    public static String getDayFromMillisecondsWithFullName(long milliseconds) {

        Calendar c = Calendar.getInstance();
//Set time in milliseconds
        c.setTimeInMillis(milliseconds);

//        int mDay = c.get(Calendar.DAY_OF_WEEK);


        return (String) android.text.format.DateFormat.format("EEEE", c.getTime());//Thursday

    }

    public static Address getAddressFromLatLng(double lat, double lng, Activity mActivity) {
        Address obj = null;
        Geocoder geocoder = new Geocoder(mActivity, Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses == null || addresses.size() == 0) {
                return null;
            }
            obj = addresses.get(0);

            String add = obj.getAddressLine(0);
            if (obj.getMaxAddressLineIndex() >= 1) {

                Logger.logsError(TAG, "Address line 2 " + obj.getAddressLine(1));

            }
            add = add + "\n" + obj.getFeatureName();
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

            Logger.logsError(TAG, "Address" + add);
            Logger.logsError(TAG, "Address Name " + obj.getFeatureName());
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return obj;
    }

    public static File bitmapConvertToFile(Bitmap bitmap, Activity mActivity) {
        FileOutputStream fileOutputStream = null;
        File bitmapFile = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Constant.FOLDER_NAME), "");
            if (!file.exists()) {
                file.mkdir();
            }

            bitmapFile = new File(file, "IMG_" + UUID.randomUUID() + ".png");
            fileOutputStream = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
            /*MediaScannerConnection.scanFile(mActivity, new String[]{bitmapFile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(String path, Uri uri) {

                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            }
        }

        return bitmapFile;
    }


    /**
     * Convert ms to time
     */
    public static String getTimeFromMillisecondsInHours(long milliseconds) {
   /*     SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getDefault());
        Date date = new Date(milliseconds);

        Logger.logsError(TAG,"Date with UTC : "+sdf.format(date));

        milliseconds = date.getTime();
        Logger.logsError(TAG,"getTimeFromMillisecondsInHours milliseconds : " + milliseconds);*/
//        Logger.logsError(TAG, "getTimeFromMillisecondsInHours milliseconds : " + milliseconds);
        long seconds = milliseconds / 1000;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d", h, m) + "h";
    }

    /**
     * Convert ms to hour (24 Hrs formate)
     */
    public static int getHourFromMillisecondsIn24HrsFomat() {

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
        return currentHour;
    }

    /**
     * Convert ms to Date
     */
    public static String getDateFromMilliseconds(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified format.
        String dateFormat = "dd/MM/yy";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());

    }

    /**
     * Regular Font
     */
    public static Typeface setRegularFont(Activity mActivity) {

        Typeface typeface = Typeface.createFromAsset(mActivity.getAssets(), "lato-reg-webfont_0.ttf");
        return typeface;
    }

    /**
     * Bold Font
     */
    public static Typeface setBoldFont(Activity mActivity) {

        Typeface typeface = Typeface.createFromAsset(mActivity.getAssets(), "Lato-Bold_0.ttf");
        return typeface;
    }

    /**
     * Bold Font
     */
    public static Typeface setSemiBoldFont(Activity mActivity) {

        Typeface typeface = Typeface.createFromAsset(mActivity.getAssets(), "Raleway-Bold_0.ttf");
        return typeface;
    }

    public static String writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            String dir = Environment.getExternalStorageDirectory() + File.separator + Constant.FOLDER_NAME;
            File mFile = new File(dir);
            if (!mFile.exists()) {
                boolean folderCreated = mFile.mkdir();
                System.out.println("folderCreated : " + folderCreated);
            }
            File futureStudioIconFile = new File(dir + File.separator + UUID.randomUUID() + ".png");
            /*if (!futureStudioIconFile.exists()){
//                boolean isCreated = futureStudioIconFile.createNewFile();
//                System.out.println("is Created : " + isCreated);
            }*/

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return futureStudioIconFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void animateExpandRotateBy180(ImageView mView) {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(200);
        rotate.setFillAfter(true);
        mView.setAnimation(rotate);
    }

    public static void animateCollapseRotateBy180(ImageView mView) {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(200);
        rotate.setFillAfter(true);
        mView.setAnimation(rotate);
    }

    /**
     * Returns Distance in kilometers (km)
     */
    public static String distance(double startLat, double startLong, double endLat, double endLong) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(startLat);
        startPoint.setLongitude(startLong);

        Location endPoint = new Location("locationA");
        endPoint.setLatitude(endLat);
        endPoint.setLongitude(endLong);

        return String.format("%.2f", startPoint.distanceTo(endPoint) / 1000); //KMs
    }

    /**
     * Check time lies between two different hours
     */
    public static boolean isTimeBetweenTwoTime(String initialTime, String finalTime) throws Exception {
        Calendar mCalendar = Calendar.getInstance();
        int mCurrentHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int mCurrentMins = mCalendar.get(Calendar.MINUTE);
        int mCurrentSeconds = mCalendar.get(Calendar.SECOND);

        String currentTime = "";
        currentTime = String.format("%02d:%02d:%02d", mCurrentHour, mCurrentMins, mCurrentSeconds);
        Logger.logsError(TAG, "currentTime  : " + currentTime);
        Logger.logsError(TAG, "initialTime  : " + initialTime);
        Logger.logsError(TAG, "finalTime  : " + finalTime);
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        if (initialTime.matches(reg) && finalTime.matches(reg) && currentTime.matches(reg)) {
            boolean valid = false;
            //Start Time
            Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);
            //Current Time
            Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);
            //End Time
            Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);
            if (finalTime.compareTo(initialTime) < 0) {
                calendar2.add(Calendar.DATE, 1);
                calendar3.add(Calendar.DATE, 1);
            }
            Date actualTime = calendar3.getTime();
            if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0)
                    && actualTime.before(calendar2.getTime())) {
                valid = true;
            }
            return valid;
        } else {
            throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
        }
    }
}