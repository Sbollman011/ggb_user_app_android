package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Screens {
    @SerializedName("splash")
    @Expose
    private Splash splash;
    @SerializedName("Intro")
    @Expose
    private Intro intro;
    @SerializedName("calling")
    @Expose
    private Calling calling;
    @SerializedName("incoming-call")
    @Expose
    private IncomingCall incomingCall;
    @SerializedName("network-issue")
    @Expose
    private NetworkIssue networkIssue;
    @SerializedName("videochat")
    @Expose
    private Videochat videochat;

    public Splash getSplash() {
        return splash;
    }

    public void setSplash(Splash splash) {
        this.splash = splash;
    }

    public Intro getIntro() {
        return intro;
    }

    public void setIntro(Intro intro) {
        this.intro = intro;
    }

    public Calling getCalling() {
        return calling;
    }

    public void setCalling(Calling calling) {
        this.calling = calling;
    }

    public IncomingCall getIncomingCall() {
        return incomingCall;
    }

    public void setIncomingCall(IncomingCall incomingCall) {
        this.incomingCall = incomingCall;
    }

    public NetworkIssue getNetworkIssue() {
        return networkIssue;
    }

    public void setNetworkIssue(NetworkIssue networkIssue) {
        this.networkIssue = networkIssue;
    }

    public Videochat getVideochat() {
        return videochat;
    }

    public void setVideochat(Videochat videochat) {
        this.videochat = videochat;
    }
}
