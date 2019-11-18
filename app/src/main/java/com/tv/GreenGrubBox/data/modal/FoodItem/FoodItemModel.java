package com.tv.GreenGrubBox.data.modal.FoodItem;

/**
 * Created by user on 25/1/18.
 */

public class FoodItemModel {

    String foodname = "";

    public FoodItemModel(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }
}
