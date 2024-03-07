package com.doBattle.mydoBattle.dto.calender;

import com.doBattle.mydoBattle.dto.member.MemberAndPercentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalenderDto {
    private Map<LocalDate, List<MemberAndPercentDto>> calenderList;
}
