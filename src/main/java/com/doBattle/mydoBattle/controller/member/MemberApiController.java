package com.doBattle.mydoBattle.controller.member;

import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.exception.member.MemberNullException;
import com.doBattle.mydoBattle.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApiController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        //세션이 있으면 세션 반환, 없으면 null 반환 (세션 삭제 위해)
        HttpSession session = request.getSession(false);
        if(session != null)
            session.invalidate();

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공!");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null)
            throw new MemberNullException("세션 없음. 멤버 조회 실패");

        Member member = (Member) session.getAttribute("currentMember");
        memberService.delete(member);
        session.invalidate();   //세션 제거

        return ResponseEntity.status(HttpStatus.OK).body("회원탈퇴 성공!");
    }

    @GetMapping("/main")
    public ResponseEntity<MainPageResponseDto> main(HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음. 멤버 조회 실패");

        MainPageResponseDto dto = memberService.main(member);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
