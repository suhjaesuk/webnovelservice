package com.numble.webnovelservice.member.dto.request;

import lombok.Getter;

@Getter
public class MemberUpdateProfileImageRequest {

    private String profileImage;

    public void setProfileImageForTest(String profileImage) {
        this.profileImage = profileImage;
    }
}
