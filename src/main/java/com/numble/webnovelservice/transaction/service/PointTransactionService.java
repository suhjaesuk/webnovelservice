package com.numble.webnovelservice.transaction.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.member.repository.MemberRepository;
import com.numble.webnovelservice.transaction.dto.request.PointTransactionChargeRequest;
import com.numble.webnovelservice.transaction.dto.response.PointTransactionInfoResponseList;
import com.numble.webnovelservice.transaction.entity.PointTransaction;
import com.numble.webnovelservice.transaction.repository.PointTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_AVAILABLE_LOCK;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.numble.webnovelservice.util.redis.repository.DailyBestRedisRepository.LOCK_NAME;


@Service
@RequiredArgsConstructor
public class PointTransactionService {

    private final PointTransactionRepository pointTransactionRepository;
    private final MemberRepository memberRepository;
    private final RedissonClient redissonClient;

    @Transactional(readOnly = true)
    public PointTransactionInfoResponseList retrieveCurrentMemberPointTransactions(Member currentMember, int page) {

        PageRequest pageRequest = PageRequest.of(page, 20);
        Slice<PointTransaction> pointTransactions = pointTransactionRepository.findSliceByMemberOrderByCreatedAtDesc(currentMember, pageRequest);

        return PointTransactionInfoResponseList.toResponse(pointTransactions);
    }

    @Transactional
    public void chargePoint(Member currentMember, PointTransactionChargeRequest request) {

        String lockName = LOCK_NAME + currentMember.getId();
        RLock lock = redissonClient.getLock(lockName);

        try {
            boolean isLocked = lock.tryLock(2, 5, TimeUnit.SECONDS);
            if (!isLocked) throw new WebNovelServiceException(NOT_AVAILABLE_LOCK);

            Member member = memberRepository.findById(currentMember.getId()).orElseThrow(
                    () -> new WebNovelServiceException(NOT_FOUND_MEMBER)
            );

            member.chargePoint(request.getAmount());

            PointTransaction pointTransaction = request.toPointTransaction(member);

            pointTransactionRepository.save(pointTransaction);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } finally {
            lock.unlock();
        }
    }
}

