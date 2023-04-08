package com.numble.webnovelservice.member.dto.request;

import lombok.Getter;

@Getter
public class MemberSignUpRequest {

    private String username;
    private String nickname;
    private String password;
}
