package com.tv.GreenGrubBox.activites.activites.BoxHistory;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.BoxHistoryModal;

/**
 * Created by admin on 01/02/18.
 */

public interface BoxHistoryMvpView extends MvpView {
    void populateHistoryData(BoxHistoryModal mBoxHistoryModal);
}
