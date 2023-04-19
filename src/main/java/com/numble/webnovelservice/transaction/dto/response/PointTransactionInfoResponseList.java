package com.numble.webnovelservice.transaction.dto.response;

import com.numble.webnovelservice.common.response.SliceInfoResponse;
import com.numble.webnovelservice.transaction.entity.PointTransaction;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PointTransactionInfoResponseList {

    private List<PointTransactionInfoResponse> pointTransactions;
    private SliceInfoResponse sliceInfo;

    public PointTransactionInfoResponseList(List<PointTransactionInfoResponse> pointTransactions, SliceInfoResponse sliceInfo) {

        this.pointTransactions = pointTransactions;
        this.sliceInfo = sliceInfo;
    }

    public static PointTransactionInfoResponseList toResponse(Slice<PointTransaction> pointTransactions) {
        List<PointTransactionInfoResponse> responseList = pointTransactions.getContent().stream()
                .map(PointTransactionInfoResponse::toResponse)
                .collect(Collectors.toList());

        SliceInfoResponse sliceInfo = SliceInfoResponse.toResponse(pointTransactions);

        return new PointTransactionInfoResponseList(responseList, sliceInfo);
    }
}
