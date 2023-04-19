package com.numble.webnovelservice.transaction.repository;

import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.transaction.entity.TicketTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTransactionRepository extends JpaRepository<TicketTransaction, Long> {

    Slice<TicketTransaction> findByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);
}
