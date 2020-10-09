package com.example.TicketSystem.payload.request;

import javax.validation.constraints.NotNull;

public class BuyTicketsRequest {

    @NotNull
    private Integer noOfTickets;

    public Integer getNoOfTickets() {
        return noOfTickets;
    }
}
