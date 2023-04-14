package com.numble.webnovelservice.episode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {

    PAID("Paid"),
    FREE("Free"),
    ;

    private final String name;
}
