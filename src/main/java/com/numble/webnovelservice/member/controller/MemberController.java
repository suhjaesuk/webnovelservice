package com.numble.webnovelservice.member.controller;

import com.numble.webnovelservice.common.response.ResponseMessage;
import com.numble.webnovelservice.member.dto.request.MemberLoginRequest;
import com.numble.webnovelservice.member.dto.request.MemberSignUpRequest;
import com.numble.webnovelservice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
}
