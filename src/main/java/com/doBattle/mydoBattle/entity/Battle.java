package com.doBattle.mydoBattle.entity;

import com.doBattle.mydoBattle.dto.battle.MakeBattleRequestDto;
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

//    @ManyToOne
//    @JoinColumn(name="member_id")    //조인하려는 칼럼명이 아님! db 컬럼명
//    private Member joinMember;

    public static Battle createBattle(MakeBattleRequestDto dto, Long battleCode){
        return new Battle(
                battleCode,
                dto.getBattleName(),
                dto.getBattleCategory(),
                LocalDate.now(),
                dto.getBattleEndDate()
        );
    }
}
