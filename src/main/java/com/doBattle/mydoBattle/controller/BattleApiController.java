package com.doBattle.mydoBattle.controller;

import com.doBattle.mydoBattle.dto.battle.BattleListDto;
import com.doBattle.mydoBattle.dto.battle.MakeBattleRequestDto;
import com.doBattle.mydoBattle.dto.battle.MakeBattleResponseDto;
import com.doBattle.mydoBattle.dto.battle.BattleCodeDto;
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

import java.util.List;

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
    public ResponseEntity<BattleCodeDto> postMakeBattle(@RequestBody MakeBattleRequestDto dto, HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        BattleCodeDto returnDto = battleService.makeBattle(dto, member);
        return ResponseEntity.status(HttpStatus.OK).body(returnDto);
    }

    @PostMapping("/joinBattle")
    public ResponseEntity<BattleCodeDto> joinBattle(@RequestBody BattleCodeDto dto, HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        BattleCodeDto returnDto = battleService.joinBattle(dto, member);
        return ResponseEntity.status(HttpStatus.OK).body(returnDto);
    }

    @GetMapping("/doingBattleList")
    public ResponseEntity<List<BattleListDto>> doingBattleList(HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        List<BattleListDto> dto = battleService.doingBattleList(member);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
