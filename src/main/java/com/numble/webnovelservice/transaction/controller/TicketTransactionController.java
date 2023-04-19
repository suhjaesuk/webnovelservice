package com.numble.webnovelservice.transaction.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.transaction.dto.request.TicketTransactionChargeRequest;
import com.numble.webnovelservice.transaction.dto.response.TicketTransactionInfoResponseList;
import com.numble.webnovelservice.transaction.service.TicketTransactionService;
import com.numble.webnovelservice.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket-transactions")
public class TicketTransactionController {

    private final TicketTransactionService ticketTransactionService;

    @GetMapping("/page/{page}")
    public ResponseEntity<ResponseMessage<TicketTransactionInfoResponseList>> retrieveCurrentMemberTicketTransactions(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int page){

        TicketTransactionInfoResponseList response = ticketTransactionService.retrieveCurrentMemberTicketTransactions(userDetails.getMember(), page);
        return new ResponseEntity<>(new ResponseMessage<>("소장권 거래 기록 조회 성공", response), HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<ResponseMessage<Void>> chargeTicket(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @RequestBody TicketTransactionChargeRequest request){

        ticketTransactionService.chargeTicket(userDetails.getMember(), request);
        return new ResponseEntity<>(new ResponseMessage<>("소장권 충전 성공", null), HttpStatus.CREATED);
    }

}
