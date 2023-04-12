package com.numble.webnovelservice.transaction.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.transaction.dto.response.PointTransactionResponseList;
import com.numble.webnovelservice.transaction.service.PointTransactionService;
import com.numble.webnovelservice.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point-transactions")
public class PointTransactionController {

    private final PointTransactionService pointTransactionService;

    @GetMapping
    public ResponseEntity<ResponseMessage<PointTransactionResponseList>> retrieveCurrentMemberPointTransactions(UserDetailsImpl userDetails){

        PointTransactionResponseList response = pointTransactionService.retrieveCurrentMemberPointTransactions(userDetails.getMember());
        return new ResponseEntity<>(new ResponseMessage<>("포인트 거래 기록 조회 성공", response), HttpStatus.OK);
    }
}
