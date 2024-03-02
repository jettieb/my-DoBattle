package com.doBattle.mydoBattle.entity;

import com.doBattle.mydoBattle.controller.battle.MakeBattleRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Battle {
    @Id
    private Long battleCode;    //난수코드 직접 생성 예정

    @Column(nullable = false)
    private String battleName;

    @Column(nullable = false)
    private String battleCategory;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name="member_create_id")    //조인하려는 칼럼명이 아님.
    private Member createUser;

    @ManyToOne
    @JoinColumn(name="member_join_id")
    private Member joinUser;

    public static Battle createBattle(MakeBattleRequestDto dto, Member member, Long battleCode){
        return new Battle(
                battleCode,
                dto.getBattleName(),
                dto.getBattleCategory(),
                LocalDate.now(),
                dto.getBattleEndDate(),
                member,
                null
        );
    }
}
