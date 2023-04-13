package com.numble.webnovelservice.transaction.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.member.repository.MemberRepository;
import com.numble.webnovelservice.transaction.dto.request.TicketTransactionChargeRequest;
import com.numble.webnovelservice.transaction.dto.response.TicketTransactionInfoResponseList;
import com.numble.webnovelservice.transaction.entity.PointTransaction;
import com.numble.webnovelservice.transaction.entity.TicketTransaction;
import com.numble.webnovelservice.transaction.repository.PointTransactionRepository;
import com.numble.webnovelservice.transaction.repository.TicketTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.numble.webnovelservice.common.exception.ErrorCode.INSUFFICIENT_POINT;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class TicketTransactionService {

    private final TicketTransactionRepository ticketTransactionRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public TicketTransactionInfoResponseList retrieveCurrentMemberTicketTransactions(Member currentMember) {

        List<TicketTransaction> ticketTransactions = ticketTransactionRepository.findByMember(currentMember);

        return TicketTransactionInfoResponseList.toResponse(ticketTransactions);
    }

    @Transactional
    public void chargeTicket(Member currentMember, TicketTransactionChargeRequest request) {

        Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_MEMBER)
        );

        Integer ticketAmount = request.getAmount();
        Integer requiredPoint = ticketAmount * 100;
        Integer availablePoint = member.getPointAmount();

        throwIfInsufficientPoint(availablePoint, requiredPoint);

        member.chargeTicket(ticketAmount);

        TicketTransaction ticketTransaction = request.toTicketTransaction(member);

        ticketTransactionRepository.save(ticketTransaction);

        PointTransaction pointTransaction = request.toPointTransaction(member);

        pointTransactionRepository.save(pointTransaction);
    }

    private void throwIfInsufficientPoint(Integer availablePoint, Integer requiredPoint) {

        if (availablePoint < requiredPoint) {
            throw new WebNovelServiceException(INSUFFICIENT_POINT);
        }
    }
}
