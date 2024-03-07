package com.doBattle.mydoBattle.dto.member;


import com.doBattle.mydoBattle.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAndPercentDto {
    private String memberName;
    private Double percent;

    public static MemberAndPercentDto createDto(Member member, Double percent){
        return new MemberAndPercentDto(
                member.getUsername(),
                percent
        );
    }
}
