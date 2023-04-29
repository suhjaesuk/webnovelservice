package com.numble.webnovelservice.transaction.entity;

import com.numble.webnovelservice.episode.entity.Episode;
import com.numble.webnovelservice.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static com.numble.webnovelservice.transaction.entity.Type.CONSUME;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketTransaction extends Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="episode_id")
    private Episode episode;

    @Builder
    public TicketTransaction(Type type, Integer amount, Integer balance, Member member, Long id, Episode episode) {
        super(type, amount, balance, member);
        this.id = id;
        this.episode = episode;
    }


    public static TicketTransaction createConsumeTicketTransaction(Member member, int amount, Episode episode) {

        Type type = CONSUME;
        Integer balance = member.getTicketCount() - amount;

        return TicketTransaction.builder()
                                .type(type)
                                .amount(amount)
                                .balance(balance)
                                .member(member)
                                .episode(episode)
                                .build();
    }
}
