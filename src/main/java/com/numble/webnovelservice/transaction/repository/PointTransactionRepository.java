package com.numble.webnovelservice.transaction.repository;

import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.transaction.entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {

    List<PointTransaction> findByMemberOrderByCreatedAtDesc(Member member);
}
