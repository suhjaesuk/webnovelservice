package com.numble.webnovelservice.member.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.member.dto.request.MemberLoginRequest;
import com.numble.webnovelservice.member.dto.request.MemberSignUpRequest;
import com.numble.webnovelservice.member.dto.request.MemberUpdateNicknameRequest;
import com.numble.webnovelservice.member.dto.request.MemberUpdateProfileImageRequest;
import com.numble.webnovelservice.member.service.MemberService;
import com.numble.webnovelservice.util.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/sign-up")
    public ResponseEntity<ResponseMessage<Void>> signUp(@RequestBody MemberSignUpRequest request){
        memberService.signUp(request);
        return new ResponseEntity<>(new ResponseMessage<>("회원가입 성공",null), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseMessage<Void>> login(@RequestBody MemberLoginRequest request, HttpServletResponse response){
        memberService.login(request, response);
        return new ResponseEntity<>(new ResponseMessage<>("로그인 성공",null), HttpStatus.OK);
    }

    @PutMapping("/members/nickname")
    public ResponseEntity<ResponseMessage<Void>> updateNickname(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody MemberUpdateNicknameRequest request){
        memberService.updateNickname(userDetails.getMember(), request);
        return new ResponseEntity<>(new ResponseMessage<>("닉네임 변경 성공",null), HttpStatus.OK);
    }

    @PutMapping("/members/profile-image")
    public ResponseEntity<ResponseMessage<Void>> updateProfileImage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody MemberUpdateProfileImageRequest request){
        memberService.updateProfileImage(userDetails.getMember(), request);
        return new ResponseEntity<>(new ResponseMessage<>("닉네임 변경 성공",null), HttpStatus.OK);
    }
}
