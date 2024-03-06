package com.doBattle.mydoBattle.dto.battle;

import com.doBattle.mydoBattle.entity.Battle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoingBattleListDto {
    private String battleName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> joinMember;

    public static DoingBattleListDto createDto(Battle battle, List<String> memberList){
        return new DoingBattleListDto(
                battle.getBattleName(),
                battle.getStartDate(),
                battle.getEndDate(),
                memberList
        );
    }
}
