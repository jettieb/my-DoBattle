package com.doBattle.mydoBattle.service;

import com.doBattle.mydoBattle.controller.battle.MakeBattleRequestDto;
import com.doBattle.mydoBattle.controller.battle.MakeBattleResponseDto;
import com.doBattle.mydoBattle.entity.Battle;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.exception.member.BattleNullException;
import com.doBattle.mydoBattle.repository.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@Service
public class BattleService {
    @Autowired
    private BattleRepository battleRepository;

    public MakeBattleResponseDto getMakeBattle(Member member) {
        MakeBattleResponseDto dto = MakeBattleResponseDto.createDto(member);
        return dto;
    }

    @Transactional
    public void makeBattle(MakeBattleRequestDto dto, Member member){
        if(dto.getBattleName().isEmpty() || dto.getBattleCategory().isEmpty() || dto.getBattleEndDate() == null)
            throw new BattleNullException("배틀이름/카테고리/종료일자 중 하나가 비어있습니다.");
        if(dto.getBattleEndDate().isBefore(LocalDate.now()))
            throw new BattleNullException("배틀 종료시점이 오늘 이전입니다.");
        
        Long battleCode = generateUniqueBattleCode();   //배틀 난수 생성
        Battle battle = Battle.createBattle(dto, member, battleCode);
        battleRepository.save(battle);
    }
    
    //배틀 난수코드 생성
    public Long generateUniqueBattleCode(){
        Random random = new Random();

        //100000-999999 사이의 랜덤한 수 생성
        Long randomNum = 100000 + random.nextLong(900000);
        while(!battleRepository.findById(randomNum).isEmpty()){
            randomNum = 100000 + random.nextLong(900000);
        }
        return randomNum;
    }
}
