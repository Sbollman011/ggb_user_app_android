package com.tv.GreenGrubBox.activites.activites.BoxHistory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.adapter.BoxHistoryAdapter;
import com.tv.GreenGrubBox.data.modal.BoxHistoryDatum;
import com.tv.GreenGrubBox.data.modal.BoxHistoryModal;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.RecyclerItemClickListener;
import com.tv.GreenGrubBox.utils.Views.EndlessRecyclerScrollListener;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 01/02/18.
 */

public class BoxHistoryActivity extends BaseActivity implements BoxHistoryMvpView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = BoxHistoryActivity.class.getSimpleName();
    @Inject
    BoxHistoryMvpPresenter<BoxHistoryMvpView, BoxHistoryMvpInteractor> mPresenter;

    @Inject
    BoxHistoryAdapter boxHistoryAdapter;

    @BindView(R.id.box_history_recycler_view)
    RecyclerView box_history_recycler_view;

    @BindView(R.id.back_iv)
    ImageView back_iv;

    @BindView(R.id.green_greenbox_tv)
    TextView green_greenbox_tv;

    @BindView(R.id.swipe_to_ref)
    SwipeRefreshLayout swipe_to_ref;

    private int mCurrentPage = 1;
    private int mPageSize = 20;

    private EndlessRecyclerScrollListener endlessRecyclerScrollListener;

    List<BoxHistoryDatum> modalList;

    @OnClick(R.id.back_iv)
    void onclickback_iv(View view) {
        finishCurrentActivity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.box_history_layout);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mPresenter.onAttach(BoxHistoryActivity.this);

        initData();

        swipe_to_ref.setEnabled(true);
        swipe_to_ref.setOnRefreshListener(this);
        swipe_to_ref.setColorSchemeResources(R.color.colorPrimaryDark);

        callWS(true);
    }

    private void initData() {

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        box_history_recycler_view.setLayoutManager(layoutManager);
        box_history_recycler_view.setItemAnimator(new DefaultItemAnimator());
        box_history_recycler_view.setAdapter(boxHistoryAdapter);

        endlessRecyclerScrollListener = new EndlessRecyclerScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (modalList != null) {
                    if (modalList.size() < mPageSize) {
                        return;
                    }
                }
                Logger.logsError(TAG, "OnLoadMore Called : " + currentPage);
                mCurrentPage = currentPage;
                callWS(true);

            }
        };
        box_history_recycler_view.addOnScrollListener(endlessRecyclerScrollListener);
        endlessRecyclerScrollListener.setVISIBLE_THRESHOLD(6);
        box_history_recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        }));

        green_greenbox_tv.setTypeface(CommonUtils.setRegularFont(this));
    }


    private void callWS(boolean isLoadMore) {

        if (mCurrentPage == 1) {
            if (endlessRecyclerScrollListener != null) {
                // Fragment stopped during loading data. Allow new loading on return.
                endlessRecyclerScrollListener.clean();
            }
        }
        mPresenter.getAllHistory(isLoadMore, mCurrentPage, mPageSize);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onStop() {
        if (endlessRecyclerScrollListener != null) {
            // Fragment stopped during loading data. Allow new loading on return.
            endlessRecyclerScrollListener.resetLoading();
        }
        super.onStop();
    }

    @Override
    public void populateHistoryData(BoxHistoryModal mBoxHistoryModal) {

        swipe_to_ref.setRefreshing(false);

        if (mBoxHistoryModal == null) {
            return;
        }
        if (mBoxHistoryModal.getStatus() == 1) {
            if (mBoxHistoryModal.getData() == null || mBoxHistoryModal.getData().size() == 0) {
                boxHistoryAdapter.clearList();
                showMessage(R.string.noboxesText);
                return;
            }
            if (mCurrentPage == 1) {
                if (this.modalList != null) {
                    this.modalList.clear();
                }
                boxHistoryAdapter.clearList();
            }
            Logger.logsError(TAG, "BoxHistorylist Size : " + mBoxHistoryModal.getData().size());

            this.modalList = mBoxHistoryModal.getData();
            boxHistoryAdapter.addItems(this.modalList);

        } else {
            showMessage(R.string.noboxesText);

        }



    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        callWS(false);
    }
}
