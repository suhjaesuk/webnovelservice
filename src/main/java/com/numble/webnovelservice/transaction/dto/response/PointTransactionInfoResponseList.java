package com.numble.webnovelservice.transaction.dto.response;

import com.numble.webnovelservice.transaction.entity.PointTransaction;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PointTransactionInfoResponseList {

    private List<PointTransactionInfoResponse> pointTransactionResponses;

    public PointTransactionInfoResponseList(List<PointTransactionInfoResponse> pointTransactionResponses) {

        this.pointTransactionResponses = pointTransactionResponses;
    }

    public static PointTransactionInfoResponseList toResponse(List<PointTransaction> pointTransactions) {

        List<PointTransactionInfoResponse> responseList = pointTransactions.stream()
                .map(PointTransactionInfoResponse::toResponse)
                .collect(Collectors.toList());

        return new PointTransactionInfoResponseList(responseList);
    }
}
