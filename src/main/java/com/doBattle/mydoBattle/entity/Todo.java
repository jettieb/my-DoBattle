package com.doBattle.mydoBattle.entity;

import com.doBattle.mydoBattle.dto.todo.TodoRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "battle_code")
    private Battle battleCode;

    @Column
    private String content;

    @Column
    private Boolean click;

    @Column
    private LocalDate createDate;

    public static Todo createTodo(TodoRequestDto dto, Member member, Battle battle){
        return new Todo(
                dto.getId(),
                member,
                battle,
                dto.getContent(),
                false,
                LocalDate.now()
        );
    }
}