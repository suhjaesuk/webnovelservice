package com.numble.webnovelservice.transaction.dto.request;

import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.transaction.entity.PointTransaction;
import com.numble.webnovelservice.transaction.entity.TicketTransaction;
import com.numble.webnovelservice.transaction.entity.Type;
import lombok.Getter;

import static com.numble.webnovelservice.transaction.entity.Type.CHARGE;
import static com.numble.webnovelservice.transaction.entity.Type.CONSUME;

@Getter
public class TicketTransactionChargeRequest {

    private Integer amount;

    public TicketTransaction toTicketTransaction(Member member){

        Type type = CHARGE;
        Integer balance = member.getTicketCount() + amount;

        return TicketTransaction.builder()
                .amount(amount)
                .balance(balance)
                .type(type)
                .member(member)
                .build();
    }

    public PointTransaction toPointTransaction(Member member){

        Type type = CONSUME;
        Integer pointAmount = this.amount *100;
        Integer balance = member.getPointAmount() - pointAmount;

        return PointTransaction.builder()
                .amount(pointAmount)
                .balance(balance)
                .type(type)
                .member(member)
                .build();
    }
}
