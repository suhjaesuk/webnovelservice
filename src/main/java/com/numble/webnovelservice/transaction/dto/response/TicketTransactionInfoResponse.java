package com.numble.webnovelservice.transaction.dto.response;

import com.numble.webnovelservice.transaction.entity.TicketTransaction;
import com.numble.webnovelservice.transaction.entity.Type;
import com.numble.webnovelservice.util.time.TimeConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class TicketTransactionInfoResponse {

    private Long ticketTransactionId;

    private String type;

    private Integer amount;

    private String createdAt;

    @Builder
    public TicketTransactionInfoResponse(Long ticketTransactionId, String type, Integer amount, String createdAt) {

        this.ticketTransactionId = ticketTransactionId;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public static TicketTransactionInfoResponse toResponse(TicketTransaction ticketTransaction){

        String koreanType = Type.toKoreanName(ticketTransaction.getType());
        String convertedCreatedAt = Optional.ofNullable(ticketTransaction.getCreatedAt())
                .map(TimeConverter::toStringFormat)
                .orElse(null);

        return TicketTransactionInfoResponse.builder()
                .ticketTransactionId(ticketTransaction.getId())
                .type(koreanType)
                .amount(ticketTransaction.getAmount())
                .createdAt(convertedCreatedAt)
                .build();
    }
}
