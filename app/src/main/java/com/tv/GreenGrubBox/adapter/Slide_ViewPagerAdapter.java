package com.tv.GreenGrubBox.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.LandingModal;
import com.tv.GreenGrubBox.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by user on 24/1/18.
 */

public class Slide_ViewPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<LandingModal> image_arraylist;

    public Slide_ViewPagerAdapter(Activity activity, ArrayList<LandingModal> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.view_pager_item, container, false);
        TextView header_tv = (TextView) view.findViewById(R.id.header_tv);
        TextView mesg_tv = (TextView) view.findViewById(R.id.mesg_tv);
        LandingModal landingModal = image_arraylist.get(position);
        header_tv.setText(landingModal.getScrollerheadingtext());
        mesg_tv.setText(landingModal.getScrollertext());
        header_tv.setTypeface(CommonUtils.setSemiBoldFont(activity));
        mesg_tv.setTypeface(CommonUtils.setSemiBoldFont(activity));
     /*   Picasso.with(activity.getApplicationContext())
                .load(image_arraylist.get(position))
                .placeholder(R.mipmap.ic_launcher) // optional
                .error(R.mipmap.ic_launcher)         // optional
                .into(im_slider);*/


        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
