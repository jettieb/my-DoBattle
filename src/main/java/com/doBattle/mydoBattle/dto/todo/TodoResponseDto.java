package com.doBattle.mydoBattle.dto.todo;

import com.doBattle.mydoBattle.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String content;
    private boolean value;

    public static TodoResponseDto createDto(Todo todo) {
        return new TodoResponseDto(
                todo.getId(),
                todo.getContent(),
                todo.getClick()
        );
    }
}
