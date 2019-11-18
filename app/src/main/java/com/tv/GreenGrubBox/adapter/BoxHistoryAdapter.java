package com.tv.GreenGrubBox.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tv.GreenGrubBox.BaseClasses.BaseViewHolder;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.BoxHistoryDatum;
import com.tv.GreenGrubBox.utils.CommonUtils;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 01/02/18.
 */

public class BoxHistoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<BoxHistoryDatum> mBoxHistoryModal;
    private Activity mActivity;

    public BoxHistoryAdapter(Activity mActivity, List<BoxHistoryDatum> mBoxHistoryModal) {
        this.mActivity = mActivity;
        this.mBoxHistoryModal = mBoxHistoryModal;
    }

    public void clearAll() {
        mBoxHistoryModal.clear();
    }


    public void clearList() {
        if (this.mBoxHistoryModal != null) {
            this.mBoxHistoryModal.clear();
        }
        notifyDataSetChanged();
    }


    public void updateItem(BoxHistoryDatum beans, int position) {
        mBoxHistoryModal.set(position, beans);
        notifyDataSetChanged();
    }

    public void addItems(List<BoxHistoryDatum> mBoxHistoryModalList) {

        this.mBoxHistoryModal.addAll(mBoxHistoryModalList);
        notifyDataSetChanged();
    }

    public List<BoxHistoryDatum> getAllList() {
        return mBoxHistoryModal;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_history_item_layout, parent, false);
        return new MyViewHolders(itemView);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return mBoxHistoryModal.size();
    }

    public class MyViewHolders extends BaseViewHolder {

        @BindView(R.id.time_tv)
        TextView time_tv;

        @BindView(R.id.still_with_you_tv)
        TextView still_with_you_tv;

        @BindView(R.id.vender_name_tv)
        TextView vender_name_tv;

        @BindView(R.id.date_tv)
        TextView date_tv;

        @BindView(R.id.item_serial_number_tv)
        TextView item_serial_number_tv;

        @BindView(R.id.item_iv)
        ImageView item_iv;

        @BindView(R.id.main_rl)
        RelativeLayout main_rl;


        public MyViewHolders(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            time_tv.setTypeface(CommonUtils.setRegularFont(mActivity));
            still_with_you_tv.setTypeface(CommonUtils.setRegularFont(mActivity));
            vender_name_tv.setTypeface(CommonUtils.setRegularFont(mActivity));
            date_tv.setTypeface(CommonUtils.setRegularFont(mActivity));
            item_serial_number_tv.setTypeface(CommonUtils.setRegularFont(mActivity));

        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            BoxHistoryDatum bean = mBoxHistoryModal.get(position);
            // time_tv.setText(bean.get());

            if (bean != null) {
                if (bean.getReturnInventory()) {
                    still_with_you_tv.setText(mActivity.getResources().getString(R.string.returnedtoInventory).concat("\n").concat(String.valueOf(bean.getInventoryTime())));
                    item_serial_number_tv.setBackgroundColor(mActivity.getResources().getColor(R.color.green_color));
                    main_rl.setBackgroundColor(mActivity.getResources().getColor(R.color.green_color));
                } else {
                    main_rl.setBackgroundColor(mActivity.getResources().getColor(R.color.orange_color));
                    item_serial_number_tv.setBackgroundColor(mActivity.getResources().getColor(R.color.orange_color));
                    still_with_you_tv.setText(mActivity.getResources().getString(R.string.stillWithYouTxt));
                }

                item_serial_number_tv.setText(bean.getBoxId());

                date_tv.setText(bean.getUserCheckOutTime());
                vender_name_tv.setText(bean.getVendorName());

                String imgUrl = bean.getBoxIcon();
                Glide.
                        with(mActivity).
                        load(imgUrl)
                        .apply(RequestOptions.centerCropTransform()
                                /*.placeholder(R.drawable.demo_img)*/)
                        .into(item_iv);
            }
        }
    }
}