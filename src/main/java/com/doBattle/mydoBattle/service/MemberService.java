package com.doBattle.mydoBattle.service;

import com.doBattle.mydoBattle.controller.member.SignupDto;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.error.MemberDuplicateException;
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
        //중복회원 찾기
        if(memberRepository.findByUsername(signupDto.getUsername()) != null)
            throw new MemberDuplicateException("중복된 닉네임으로 회원가입 실패");
        if(memberRepository.findByIdentify(signupDto.getIdentify()) != null)
            throw new MemberDuplicateException("중복된 아이디으로 회원가입 실패");

        //패스워드 암호화
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Member saveMember = Member.createMember(signupDto);
        memberRepository.save(saveMember);
    }
}
