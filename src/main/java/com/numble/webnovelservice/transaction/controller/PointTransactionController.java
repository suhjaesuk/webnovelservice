package com.numble.webnovelservice.transaction.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.transaction.dto.request.PointTransactionChargeRequest;
import com.numble.webnovelservice.transaction.dto.response.PointTransactionInfoResponseList;
import com.numble.webnovelservice.transaction.service.PointTransactionService;
import com.numble.webnovelservice.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point-transactions")
public class PointTransactionController {

    private final PointTransactionService pointTransactionService;

    @GetMapping
    public ResponseEntity<ResponseMessage<PointTransactionInfoResponseList>> retrieveCurrentMemberPointTransactions(@AuthenticationPrincipal UserDetailsImpl userDetails){

        PointTransactionInfoResponseList response = pointTransactionService.retrieveCurrentMemberPointTransactions(userDetails.getMember());
        return new ResponseEntity<>(new ResponseMessage<>("포인트 거래 기록 조회 성공", response), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage<Void>> chargePoint(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @RequestBody PointTransactionChargeRequest request){

        pointTransactionService.chargePoint(userDetails.getMember(), request);
        return new ResponseEntity<>(new ResponseMessage<>("포인트 거래 기록 조회 성공", null), HttpStatus.CREATED);
    }
}
