package com.doBattle.mydoBattle.service;

import com.doBattle.mydoBattle.controller.member.SignupDto;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.exception.SignupException;
import com.doBattle.mydoBattle.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    public void signup(SignupDto signupDto) {
        //중복회원 검사
        if(memberRepository.findByUsername(signupDto.getUsername()) != null)
            throw new SignupException("중복 닉네임으로 회원가입 불가");
        if(memberRepository.findByIdentify(signupDto.getIdentify()) != null)
            throw new SignupException("중복 아이디로 회원가입 불가");
        if(signupDto.getUsername().isEmpty() || signupDto.getIdentify().isEmpty() || signupDto.getPassword().isEmpty())
            throw new SignupException("닉네임/아이디/비밀번호 중 공백 있음");

        //비밀번호 암호화
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Member member = Member.createMember(signupDto);
        memberRepository.save(member);
    }
}
