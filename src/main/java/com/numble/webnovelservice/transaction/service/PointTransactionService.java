package com.numble.webnovelservice.transaction.service;

import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.transaction.dto.response.PointTransactionResponseList;
import com.numble.webnovelservice.transaction.entity.PointTransaction;
import com.numble.webnovelservice.transaction.repository.PointTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PointTransactionService {

    private final PointTransactionRepository pointTransactionRepository;

    public PointTransactionResponseList retrieveCurrentMemberPointTransactions(Member member) {

        List<PointTransaction> pointTransactions = pointTransactionRepository.findByMember(member);

        return PointTransactionResponseList.toResponse(pointTransactions);
    }
}

