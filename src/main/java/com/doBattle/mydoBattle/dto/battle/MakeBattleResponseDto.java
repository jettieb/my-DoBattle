package com.doBattle.mydoBattle.dto.battle;

import com.doBattle.mydoBattle.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MakeBattleResponseDto {
    private String currentUserName;

    public static MakeBattleResponseDto createDto(Member member) {
        return new MakeBattleResponseDto(
                member.getUsername()
        );
    }
}
