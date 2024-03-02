package com.doBattle.mydoBattle.entity;

import com.doBattle.mydoBattle.dto.member.SignupDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String identify;

    @Column(nullable = false)
    private String password;

    public static Member createMember(SignupDto signupDto) {
        return new Member(
                signupDto.getId(),
                signupDto.getUsername(),
                signupDto.getIdentify(),
                signupDto.getPassword()
        );
    }
}
