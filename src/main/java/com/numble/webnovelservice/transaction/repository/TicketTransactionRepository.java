package com.numble.webnovelservice.transaction.repository;

import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.transaction.entity.TicketTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketTransactionRepository extends JpaRepository<TicketTransaction, Long> {

    List<TicketTransaction> findByMemberOrderByCreatedAtDesc(Member member);
}
