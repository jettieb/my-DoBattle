package com.doBattle.mydoBattle.entity;

import com.doBattle.mydoBattle.controller.member.SignupDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public static Member createMember(SignupDto dto){
        return new Member(
                dto.getId(),
                dto.getUsername(),
                dto.getIdentify(),
                dto.getPassword()
        );
    }
}
