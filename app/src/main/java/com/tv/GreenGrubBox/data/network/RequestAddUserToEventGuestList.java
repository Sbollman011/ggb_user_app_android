package com.tv.GreenGrubBox.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nayan on 11/10/17.
 */

public class RequestAddUserToEventGuestList {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("eventTicketSequence")
    @Expose
    private long eventTicketSequence;
    @SerializedName("ticketsNumber")
    @Expose
    private long ticketsNumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getEventTicketSequence() {
        return eventTicketSequence;
    }

    public void setEventTicketSequence(long eventTicketSequence) {
        this.eventTicketSequence = eventTicketSequence;
    }

    public long getTicketsNumber() {
        return ticketsNumber;
    }

    public void setTicketsNumber(long ticketsNumber) {
        this.ticketsNumber = ticketsNumber;
    }
}
