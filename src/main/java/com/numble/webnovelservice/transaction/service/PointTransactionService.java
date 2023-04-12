package com.numble.webnovelservice.transaction.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.member.repository.MemberRepository;
import com.numble.webnovelservice.transaction.dto.request.PointTransactionChargeRequest;
import com.numble.webnovelservice.transaction.dto.response.PointTransactionInfoResponseList;
import com.numble.webnovelservice.transaction.entity.PointTransaction;
import com.numble.webnovelservice.transaction.repository.PointTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_MEMBER;


@Service
@RequiredArgsConstructor
public class PointTransactionService {

    private final PointTransactionRepository pointTransactionRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public PointTransactionInfoResponseList retrieveCurrentMemberPointTransactions(Member currentMember) {

        List<PointTransaction> pointTransactions = pointTransactionRepository.findByMember(currentMember);

        return PointTransactionInfoResponseList.toResponse(pointTransactions);
    }

    @Transactional
    public void chargePoint(Member currentMember, PointTransactionChargeRequest request) {

        Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_MEMBER)
        );

        member.chargePoint(request.getAmount());

        PointTransaction pointTransaction = request.toPointTransaction(member);

        pointTransactionRepository.save(pointTransaction);
    }
}

