package com.numble.webnovelservice.transaction.repository;

import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.transaction.entity.PointTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {

    Slice<PointTransaction> findSliceByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);
}
