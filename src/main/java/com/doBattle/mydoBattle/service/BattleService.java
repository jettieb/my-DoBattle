package com.doBattle.mydoBattle.service;

import com.doBattle.mydoBattle.dto.battle.BattleListDto;
import com.doBattle.mydoBattle.dto.battle.MakeBattleRequestDto;
import com.doBattle.mydoBattle.dto.battle.MakeBattleResponseDto;
import com.doBattle.mydoBattle.dto.battle.MakeBattleSuccessDto;
import com.doBattle.mydoBattle.entity.Battle;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.exception.battle.BattleNullException;
import com.doBattle.mydoBattle.repository.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BattleService {
    @Autowired
    private BattleRepository battleRepository;

    public MakeBattleResponseDto getMakeBattle(Member member) {
        MakeBattleResponseDto dto = MakeBattleResponseDto.createDto(member);
        return dto;
    }

    @Transactional
    public MakeBattleSuccessDto makeBattle(MakeBattleRequestDto dto, Member member){
        if(dto.getBattleName().isEmpty() || dto.getBattleCategory().isEmpty() || dto.getBattleEndDate() == null)
            throw new BattleNullException("배틀이름/카테고리/종료일자 중 하나가 비어있습니다.");
        if(dto.getBattleEndDate().isBefore(LocalDate.now()))
            throw new BattleNullException("배틀 종료시점이 오늘 이전입니다.");
        
        Long battleCode = generateUniqueBattleCode();   //배틀 난수 생성
        Battle battle = Battle.createBattle(dto, member, battleCode);
        battleRepository.save(battle);

        MakeBattleSuccessDto returnDto = new MakeBattleSuccessDto(battleCode);
        return returnDto;
    }

    public List<BattleListDto> doingBattleList(Member member) {
        List<Battle> joinedBattle = battleRepository.findByJoinMemberId(member.getId());

        //유저가 참여하고 있는 모든 배틀에 대한 정보
        List<BattleListDto> dto = new ArrayList<>();
        for(Battle battle : joinedBattle){
            //배틀코드 동일한 다른 참여자 불러오기
            List<String> partnerUser = battleRepository.findByBattleCodeWithoutCurrentMember(battle.getBattleCode(), member.getId())
                    .stream()
                    .map(b -> b.getJoinMember().getUsername())   //Battle 객체에 대해 getJoinMember() 메서드를 호출해서 Member 객체 얻어냄
                    .collect(Collectors.toList());

            BattleListDto eachDto = BattleListDto.createDto(battle, partnerUser);
            dto.add(eachDto);
        }

        return dto;
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
