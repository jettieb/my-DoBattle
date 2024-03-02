package com.doBattle.mydoBattle.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    private Long id;
    private String username;
    private String identify;
    private String password;
}
