package com.doBattle.mydoBattle.controller;

import com.doBattle.mydoBattle.dto.battle.BattlePageResponseDto;
import com.doBattle.mydoBattle.dto.battle.DoingBattleListDto;
import com.doBattle.mydoBattle.dto.battle.MakeBattleRequestDto;
import com.doBattle.mydoBattle.dto.battle.BattleCodeDto;
import com.doBattle.mydoBattle.dto.calender.CalenderDto;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.exception.member.MemberNullException;
import com.doBattle.mydoBattle.service.BattleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BattleApiController {
    @Autowired
    private BattleService battleService;

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
    public ResponseEntity<List<DoingBattleListDto>> doingBattleList(HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        List<DoingBattleListDto> dto = battleService.doingBattleList(member);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("battle/{battleCode}")
    public ResponseEntity<BattlePageResponseDto> battlePage(@PathVariable Long battleCode, HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        BattlePageResponseDto dto = battleService.getBattlePage(battleCode, member);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("calender/{battleCode}")
    public ResponseEntity<CalenderDto> calenderPage(@PathVariable Long battleCode, HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        CalenderDto dto = battleService.getCalenderPage(battleCode, member);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
