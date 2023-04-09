package com.numble.webnovelservice.member.dto.request;

import lombok.Getter;

@Getter
public class MemberLoginRequest {

    private String username;
    private String password;

    public void setUsernameForTest(String username) {
        this.username = username;
    }

    public void setPasswordForTest(String password) {
        this.password = password;
    }
}
