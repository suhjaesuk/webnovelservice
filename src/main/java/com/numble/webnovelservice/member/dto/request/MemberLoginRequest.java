package com.numble.webnovelservice.member.dto.request;

import lombok.Getter;

@Getter
public class MemberLoginRequest {

    private String username;
    private String password;
}
