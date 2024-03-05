package com.doBattle.mydoBattle.dto.battle;

import com.doBattle.mydoBattle.entity.Battle;
import com.doBattle.mydoBattle.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BattleListDto {
    private String battleName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> joinMember;

    public static BattleListDto createDto(Battle battle, List<Long> memberList){
        return new BattleListDto(
                battle.getBattleName(),
                battle.getStartDate(),
                battle.getEndDate(),
                memberList
        );
    }
}
