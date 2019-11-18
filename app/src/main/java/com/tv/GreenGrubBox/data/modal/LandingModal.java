package com.tv.GreenGrubBox.data.modal;

/**
 * Created by user on 24/1/18.
 */

public class LandingModal {
    int slideImage;
    String scrollerheadingtext="";
    String scrollertext="";

    public String getScrollerheadingtext() {
        return scrollerheadingtext;
    }

    public void setScrollerheadingtext(String scrollerheadingtext) {
        this.scrollerheadingtext = scrollerheadingtext;
    }

    public String getScrollertext() {
        return scrollertext;
    }

    public void setScrollertext(String scrollertext) {
        this.scrollertext = scrollertext;
    }

    public LandingModal(String scrollerheadingtext, String scrollertext) {
        this.scrollerheadingtext = scrollerheadingtext;
        this.scrollertext = scrollertext;
    }

    public LandingModal(int slideImage) {
        this.slideImage = slideImage;
    }

    public int getSlideImage() {
        return slideImage;
    }

    public void setSlideImage(int slideImage) {
        this.slideImage = slideImage;
    }
}
