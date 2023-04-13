package com.numble.webnovelservice.transaction.entity;

import com.numble.webnovelservice.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static com.numble.webnovelservice.transaction.entity.Type.CONSUME;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketTransaction extends Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_transaction_id")
    private Long id;

    @Builder
    public TicketTransaction(Type type, Integer amount, Integer balance, Member member, Long id) {
        super(type, amount, balance, member);
        this.id = id;
    }


    public static TicketTransaction createConsumeTicketTransaction(Member member, int amount) {

        Type type = CONSUME;
        Integer balance = member.getTicketCount() - amount;

        return TicketTransaction.builder()
                                .type(type)
                                .amount(amount)
                                .balance(balance)
                                .member(member)
                                .build();
    }
}
