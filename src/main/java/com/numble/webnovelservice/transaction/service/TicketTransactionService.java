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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.numble.webnovelservice.common.exception.ErrorCode.INSUFFICIENT_POINT;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_AVAILABLE_LOCK;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.numble.webnovelservice.util.redis.config.LockRedisConfig.LOCK_NAME;

@Service
@RequiredArgsConstructor
public class TicketTransactionService {

    private final TicketTransactionRepository ticketTransactionRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final MemberRepository memberRepository;
    private final RedissonClient redissonClient;

    @Transactional(readOnly = true)
    public TicketTransactionInfoResponseList retrieveCurrentMemberTicketTransactions(Member currentMember, int page) {

        PageRequest pageRequest = PageRequest.of(page, 20);
        Slice<TicketTransaction> ticketTransactions = ticketTransactionRepository.findByMemberOrderByCreatedAtDesc(currentMember, pageRequest);

        return TicketTransactionInfoResponseList.toResponse(ticketTransactions);
    }

    @Transactional
    public void chargeTicket(Member currentMember, TicketTransactionChargeRequest request) {

        String lockName = LOCK_NAME + currentMember.getId();
        RLock lock = redissonClient.getLock(lockName);

        try {
            boolean isLocked = lock.tryLock(2, 5, TimeUnit.SECONDS);
            if (!isLocked) throw new WebNovelServiceException(NOT_AVAILABLE_LOCK);

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

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            } finally {
                lock.unlock();
        }
    }

    private void throwIfInsufficientPoint(Integer availablePoint, Integer requiredPoint) {

        if (availablePoint < requiredPoint) {
            throw new WebNovelServiceException(INSUFFICIENT_POINT);
        }
    }
}
