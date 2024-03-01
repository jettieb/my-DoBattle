package com.doBattle.mydoBattle.controller.member;

import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApiController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    //회원가입
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto){
        memberService.signup(signupDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request){
        Member member = memberService.login(loginDto);

        //세션이 있으면 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute("currentMember", member);

        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공!");
    }
}
