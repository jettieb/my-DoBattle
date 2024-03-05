package com.doBattle.mydoBattle.entity;

import com.doBattle.mydoBattle.dto.battle.MakeBattleRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoinBattle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "battle_code")
    private Battle battle;

    public static JoinBattle createJoinBattle(Member member, Battle battle){
        return new JoinBattle(
                null,   //id 자동생성
                member,
                battle
        );

    }
}
