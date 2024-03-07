package com.doBattle.mydoBattle.dto.battle;

import com.doBattle.mydoBattle.dto.member.MemberAndPercentDto;
import com.doBattle.mydoBattle.dto.todo.TodoResponseDto;
import com.doBattle.mydoBattle.entity.Battle;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class BattlePageResponseDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String battleName;
    private MemberAndPercentDto currentMember;
    private List<MemberAndPercentDto> partner;
    private List<TodoResponseDto> todo;

    public static BattlePageResponseDto createDto(Battle battle, MemberAndPercentDto currentMember, List<MemberAndPercentDto> partner, List<TodoResponseDto> todo){
        return new BattlePageResponseDto(
                battle.getStartDate(),
                battle.getEndDate(),
                battle.getBattleName(),
                currentMember,
                partner,
                todo
        );
    }
}
