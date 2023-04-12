package com.numble.webnovelservice.transaction.dto.response;

import com.numble.webnovelservice.transaction.entity.PointTransaction;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PointTransactionResponseList {

    private List<PointTransactionResponse> pointTransactionResponses;

    public PointTransactionResponseList(List<PointTransactionResponse> pointTransactionResponses) {

        this.pointTransactionResponses = pointTransactionResponses;
    }

    public static PointTransactionResponseList toResponse(List<PointTransaction> pointTransactions) {

        List<PointTransactionResponse> responseList = pointTransactions.stream()
                .map(PointTransactionResponse::toResponse)
                .collect(Collectors.toList());

        return new PointTransactionResponseList(responseList);
    }
}
