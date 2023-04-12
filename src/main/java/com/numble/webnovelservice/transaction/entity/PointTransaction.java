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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointTransaction extends Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="point_transaction_id")
    private Long id;

    @Builder
    public PointTransaction(Type type, Integer amount, Integer balance, Member member, Long id) {

        super(type, amount, balance, member);
        this.id = id;
    }
}
