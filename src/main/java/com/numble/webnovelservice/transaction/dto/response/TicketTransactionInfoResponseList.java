package com.numble.webnovelservice.transaction.dto.response;

import com.numble.webnovelservice.common.response.SliceInfoResponse;
import com.numble.webnovelservice.transaction.entity.TicketTransaction;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TicketTransactionInfoResponseList {

    private List<TicketTransactionInfoResponse> ticketTransactions;
    private SliceInfoResponse sliceInfo;

    public TicketTransactionInfoResponseList(List<TicketTransactionInfoResponse> ticketTransactions, SliceInfoResponse sliceInfo) {

        this.ticketTransactions = ticketTransactions;
        this.sliceInfo = sliceInfo;
    }

    public static TicketTransactionInfoResponseList toResponse(Slice<TicketTransaction> ticketTransactions) {

        List<TicketTransactionInfoResponse> responseList = ticketTransactions.getContent().stream()
                .map(TicketTransactionInfoResponse::toResponse)
                .collect(Collectors.toList());

        SliceInfoResponse sliceInfo = SliceInfoResponse.toResponse(ticketTransactions);
        return new TicketTransactionInfoResponseList(responseList, sliceInfo);
    }
}
