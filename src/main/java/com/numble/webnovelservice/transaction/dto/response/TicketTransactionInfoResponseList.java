package com.numble.webnovelservice.transaction.dto.response;

import com.numble.webnovelservice.transaction.entity.TicketTransaction;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TicketTransactionInfoResponseList {

    private List<TicketTransactionInfoResponse> ticketTransactions;

    public TicketTransactionInfoResponseList(List<TicketTransactionInfoResponse> ticketTransactions) {

        this.ticketTransactions = ticketTransactions;
    }

    public static TicketTransactionInfoResponseList toResponse(List<TicketTransaction> ticketTransactions) {

        List<TicketTransactionInfoResponse> responseList = ticketTransactions.stream()
                .map(TicketTransactionInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new TicketTransactionInfoResponseList(responseList);
    }
}
