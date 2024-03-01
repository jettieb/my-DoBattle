package com.doBattle.mydoBattle.controller.member;

import com.doBattle.mydoBattle.service.MemberService;
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

    //회원가입
    @PostMapping("/signup")
    private ResponseEntity<String> signup(@RequestBody SignupDto signupDto){
        memberService.signup(signupDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공!");
    }
}
