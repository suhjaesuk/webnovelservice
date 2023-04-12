package com.numble.webnovelservice.transaction.dto.response;

import com.numble.webnovelservice.transaction.entity.PointTransaction;
import com.numble.webnovelservice.transaction.entity.Type;
import com.numble.webnovelservice.util.time.TimeConverter;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class PointTransactionResponse {

    private Long pointTransactionId;

    private String type;

    private Integer amount;

    private String createdAt;

    @Builder
    public PointTransactionResponse(Long pointTransactionId, String type, Integer amount, String createdAt) {

        this.pointTransactionId = pointTransactionId;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public static PointTransactionResponse toResponse(PointTransaction pointTransaction){

        String koreanType = Type.toKoreanName(pointTransaction.getType());
        String convertedCreatedAt = Optional.ofNullable(pointTransaction.getCreatedAt())
                                            .map(TimeConverter::toStringFormat)
                                            .orElse(null);

        return PointTransactionResponse.builder()
                .pointTransactionId(pointTransaction.getId())
                .type(koreanType)
                .amount(pointTransaction.getAmount())
                .createdAt(convertedCreatedAt)
                .build();
    }
}
