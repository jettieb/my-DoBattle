package com.doBattle.mydoBattle.dto.battle;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class MakeBattleRequestDto {
    private String battleName;
    private String battleCategory;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate battleEndDate;   //들어올땐 String 형식으로 들어오지만, 포맷 지정으로 LocalDate로 만들어줌
}
