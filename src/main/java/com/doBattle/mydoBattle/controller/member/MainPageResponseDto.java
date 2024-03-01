package com.doBattle.mydoBattle.controller.member;

import com.doBattle.mydoBattle.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainPageResponseDto {
    private String currentUserName;

    public static MainPageResponseDto createDto(Member member) {
        return new MainPageResponseDto(
                member.getUsername()
        );
    }
}
