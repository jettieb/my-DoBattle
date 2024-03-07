package com.doBattle.mydoBattle.service;

import com.doBattle.mydoBattle.dto.battle.BattlePageResponseDto;
import com.doBattle.mydoBattle.dto.battle.DoingBattleListDto;
import com.doBattle.mydoBattle.dto.battle.MakeBattleRequestDto;
import com.doBattle.mydoBattle.dto.battle.BattleCodeDto;
import com.doBattle.mydoBattle.dto.member.MemberAndPercentDto;
import com.doBattle.mydoBattle.dto.todo.TodoResponseDto;
import com.doBattle.mydoBattle.entity.Battle;
import com.doBattle.mydoBattle.entity.JoinBattle;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.exception.battle.BattleNullException;
import com.doBattle.mydoBattle.repository.BattleRepository;
import com.doBattle.mydoBattle.repository.JoinBattleRepository;
import com.doBattle.mydoBattle.repository.TodoRepository;
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

    @Autowired
    private JoinBattleRepository joinBattleRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Transactional
    public BattleCodeDto makeBattle(MakeBattleRequestDto dto, Member member){
        if(dto.getBattleName().isEmpty() || dto.getBattleCategory().isEmpty() || dto.getBattleEndDate() == null)
            throw new BattleNullException("배틀이름/카테고리/종료일자 중 하나가 비어있습니다.");
        if(dto.getBattleEndDate().isBefore(LocalDate.now()))
            throw new BattleNullException("배틀 종료시점이 오늘 이전입니다.");
        
        Long battleCode = generateUniqueBattleCode();   //배틀 난수 생성
        Battle battle = Battle.createBattle(dto, battleCode);
        battleRepository.save(battle);

        JoinBattle joinList = JoinBattle.createJoinBattle(member, battle);
        joinBattleRepository.save(joinList);

        return new BattleCodeDto(battleCode);
    }

    @Transactional
    public BattleCodeDto joinBattle(BattleCodeDto dto, Member member) {
        Battle battle = battleRepository.findById(dto.getBattleCode())
                .orElseThrow(() -> new BattleNullException("배틀코드에 해당하는 배틀이 존재하지 않습니다."));
        if(!joinBattleRepository.findByBattleCodeAndCurrentMember(battle.getBattleCode(), member.getId()).isEmpty())
            throw new BattleNullException("이미 배틀에 참여하였습니다.");

        JoinBattle joinBattle = JoinBattle.createJoinBattle(member, battle);
        joinBattleRepository.save(joinBattle);

        return new BattleCodeDto(battle.getBattleCode());
    }

    @Transactional
    public List<DoingBattleListDto> doingBattleList(Member member) {
        List<JoinBattle> joinedBattle = joinBattleRepository.findByMemberId(member.getId());

        //유저가 참여하고 있는 모든 배틀에 대한 정보
        List<DoingBattleListDto> dto = new ArrayList<>();
        for(JoinBattle battle : joinedBattle){
            //배틀코드 동일한 다른 참여자 불러오기
            List<String> partnerUser = joinBattleRepository.findByBattleCodeWithoutCurrentMember(battle.getBattle().getBattleCode(), member.getId())
                    .stream()
                    .map(b -> b.getMember().getUsername())   //username만 반환하도록 매핑
                    .collect(Collectors.toList());

            DoingBattleListDto eachDto = DoingBattleListDto.createDto(battle.getBattle(), partnerUser);
            dto.add(eachDto);
        }

        return dto;
    }

    @Transactional
    public BattlePageResponseDto getBattlePage(Long battleCode, Member member) {
        Battle battle = battleRepository.findById(battleCode)
                .orElseThrow(()-> new BattleNullException("배틀코드에 해당하는 배틀이 존재하지 않습니다."));

        //현재 사용자의 퍼센트 별개의 dto에 저장
        double currentUserPercent = calculatePercent(member, LocalDate.now(), battle);
        MemberAndPercentDto currentUserDto = MemberAndPercentDto.createDto(member, currentUserPercent);
        
        //파트너 사용자의 퍼센트
        List<Member> partnerUser = joinBattleRepository.findByBattleCodeWithoutCurrentMember(battle.getBattleCode(), member.getId())
                .stream()
                .map(b -> b.getMember())   //member로 매핑
                .collect(Collectors.toList());

        List<MemberAndPercentDto> partnerDto = new ArrayList<>();
        for(Member partner : partnerUser){
            double percent = calculatePercent(partner, LocalDate.now(), battle);
            MemberAndPercentDto dto = MemberAndPercentDto.createDto(partner, percent);
            partnerDto.add(dto);
        }
        
        //해당 사용자의 오늘날짜 모든 투두
        List<TodoResponseDto> todo = todoRepository.findByBattleCodeAndMemberIdAndDate(battle.getBattleCode(), member.getId(), LocalDate.now())
                .stream()
                .map(t -> TodoResponseDto.createDto(t))
                .collect(Collectors.toList());

        return BattlePageResponseDto.createDto(battle, currentUserDto, partnerDto, todo);
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

    //퍼센트 구하기
    public Double calculatePercent(Member member, LocalDate date, Battle battle){
        int allTodoNum = todoRepository.findByBattleCodeAndMemberIdAndDate(battle.getBattleCode(), member.getId(), date).size();
        int successTodoNum = todoRepository.findByBattleCodeAndMemberIdAndDateWithTrueValue(battle.getBattleCode(), member.getId(), date).size();

        double percent;
        if(allTodoNum == 0 || successTodoNum == 0) percent = 0;
        else percent = (successTodoNum * 100.0)/allTodoNum;

        return percent;
    }
}
