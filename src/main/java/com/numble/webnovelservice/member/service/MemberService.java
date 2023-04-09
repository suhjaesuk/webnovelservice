package com.numble.webnovelservice.member.service;

import com.numble.webnovelservice.common.exception.WebNovelServiceException;
import com.numble.webnovelservice.member.dto.request.MemberLoginRequest;
import com.numble.webnovelservice.member.dto.request.MemberSignUpRequest;
import com.numble.webnovelservice.member.dto.request.MemberUpdateNicknameRequest;
import com.numble.webnovelservice.member.dto.request.MemberUpdateProfileImageRequest;
import com.numble.webnovelservice.member.entity.Member;
import com.numble.webnovelservice.member.repository.MemberRepository;
import com.numble.webnovelservice.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static com.numble.webnovelservice.common.exception.ErrorCode.ALREADY_EXIST_NICKNAME;
import static com.numble.webnovelservice.common.exception.ErrorCode.ALREADY_EXIST_USERNAME;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.numble.webnovelservice.common.exception.ErrorCode.NOT_VALID_PASSWORD;
import static com.numble.webnovelservice.util.jwt.JwtUtil.AUTHORIZATION_ACCESS;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(MemberSignUpRequest request) {

        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new WebNovelServiceException(ALREADY_EXIST_USERNAME);
        }

        validateDuplicateNickname(request.getNickname());

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Member member = request.toMember(encryptedPassword);

        memberRepository.save(member);
    }

    @Transactional
    public void login(MemberLoginRequest request, HttpServletResponse response) {

        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new WebNovelServiceException(NOT_FOUND_MEMBER));

        validatePasswordMatch(request.getPassword(), member.getPassword());

        issueTokens(response, member.getUsername());
    }

    public void issueTokens(HttpServletResponse response, String username) {

        String accessToken = jwtUtil.createAccessToken(username);
        response.addHeader(AUTHORIZATION_ACCESS, accessToken);
    }

    public void validatePasswordMatch(String encryptedPassword, String inputPassword) {

        if (passwordEncoder.matches(encryptedPassword, inputPassword)) {
            return;
        }
        throw new WebNovelServiceException(NOT_VALID_PASSWORD);
    }

    @Transactional
    public void updateNickname(Member member, MemberUpdateNicknameRequest request){

        validateDuplicateNickname(request.getNickname());

        Member findMember = findMemberById(member.getId());

        findMember.updateNickname(request.getNickname());
    }

    @Transactional
    public void updateProfileImage(Member member, MemberUpdateProfileImageRequest request){

        Member findMember = findMemberById(member.getId());

        findMember.updateProfileImage(request.getProfileImage());
    }

    public Member findMemberById(Long memberId) {

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new WebNovelServiceException(NOT_FOUND_MEMBER));
    }

    public void validateDuplicateNickname(String nickname){

        if (memberRepository.existsByNickname(nickname)) {
            throw new WebNovelServiceException(ALREADY_EXIST_NICKNAME);
        }
    }

}
