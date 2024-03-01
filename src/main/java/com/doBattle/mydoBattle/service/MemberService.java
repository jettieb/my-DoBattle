package com.doBattle.mydoBattle.service;

import com.doBattle.mydoBattle.controller.member.LoginDto;
import com.doBattle.mydoBattle.controller.member.SignupDto;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.exception.member.LoginException;
import com.doBattle.mydoBattle.exception.member.MemberDuplicateException;
import com.doBattle.mydoBattle.exception.member.MemberNullException;
import com.doBattle.mydoBattle.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    //회원가입
    public void signup(SignupDto signupDto) {
        //중복회원 검사
        if(memberRepository.findByUsername(signupDto.getUsername()) != null)
            throw new MemberDuplicateException("중복 닉네임으로 회원가입 불가");
        if(memberRepository.findByIdentify(signupDto.getIdentify()) != null)
            throw new MemberDuplicateException("중복 아이디로 회원가입 불가");
        //공백 검사
        if(signupDto.getUsername().isEmpty() || signupDto.getIdentify().isEmpty() || signupDto.getPassword().isEmpty())
            throw new MemberNullException("닉네임/아이디/비밀번호 중 공백 있음");

        //비밀번호 암호화
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Member member = Member.createMember(signupDto);
        memberRepository.save(member);
    }

    @Transactional
    //로그인
    public Member login(LoginDto loginDto) {
        Member member = memberRepository.findByIdentify(loginDto.getIdentify());
        if(member == null)
            throw new LoginException("해당 id의 회원이 존재하지 않습니다.");
        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword()))
            throw new LoginException("비밀번호가 일치하지 않습니다.");

        return member;
    }
}
