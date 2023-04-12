package com.numble.webnovelservice.transaction.dto.request;

import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.transaction.entity.PointTransaction;
import com.numble.webnovelservice.transaction.entity.Type;
import lombok.Getter;

import static com.numble.webnovelservice.transaction.entity.Type.CHARGE;

@Getter
public class PointTransactionChargeRequest {

    private Integer amount;

    public PointTransaction toPointTransaction(Member member){

        Type type = CHARGE;
        Integer balance = member.getPointAmount() + amount;

        return PointTransaction.builder()
                               .amount(amount)
                               .balance(balance)
                               .type(type)
                               .member(member)
                               .build();
    }
}
