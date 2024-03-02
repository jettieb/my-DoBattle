package com.doBattle.mydoBattle.controller.battle;

import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.exception.member.MemberNullException;
import com.doBattle.mydoBattle.service.BattleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleApiController {
    @Autowired
    private BattleService battleService;

    @GetMapping("/makeBattle")
    public ResponseEntity<MakeBattleResponseDto> getMakeBattle(HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        MakeBattleResponseDto dto = battleService.getMakeBattle(member);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/makeBattle")
    public ResponseEntity<String> postMakeBattle(@RequestBody MakeBattleRequestDto dto, HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        battleService.makeBattle(dto, member);
        return ResponseEntity.status(HttpStatus.OK).body("배틀 만들기 성공");
    }
}
