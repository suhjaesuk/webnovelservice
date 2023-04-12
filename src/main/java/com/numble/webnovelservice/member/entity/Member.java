package com.numble.webnovelservice.member.entity;

import com.numble.webnovelservice.util.time.Timestamped;
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
public class Member extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false)
    private Integer pointAmount;

    @Column(nullable = false)
    private Integer ticketCount;

    @Builder
    public Member(Long id, String username, String password, String nickname, String profileImage, Integer pointAmount, Integer ticketCount) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.pointAmount = pointAmount;
        this.ticketCount = ticketCount;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateProfileImage(String profileImage){
        this.profileImage = profileImage;
    }

    public void chargePoint(Integer amount){

        this.pointAmount += amount;
    }

    public void chargeTicket(Integer amount) {

        this.pointAmount -= 100 * amount;
        this.ticketCount += amount;
    }
}
