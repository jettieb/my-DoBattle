package com.doBattle.mydoBattle.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequestDto {
    private Long id;
    private String content;
}