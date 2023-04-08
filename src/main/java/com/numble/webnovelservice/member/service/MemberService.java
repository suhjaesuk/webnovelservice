package com.numble.webnovelservice.member.service;

import com.numble.webnovelservice.member.dto.request.MemberLoginRequest;
import com.numble.webnovelservice.member.dto.request.MemberSignUpRequest;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(MemberSignUpRequest request) {

        String password = passwordEncoder.encode(request.getPassword());
        Member member = request.toMember(password);
        memberRepository.save(member);
    }

    @Transactional
    public void login(MemberLoginRequest request, HttpServletResponse response) {

        /*
        1. 요청이 오면 유저아이디(username)으로 찾는다.
        2. 해당 아이디의 비밀번호와 일치하는지 판별
        3. 토큰 발행
        */
    }
}
