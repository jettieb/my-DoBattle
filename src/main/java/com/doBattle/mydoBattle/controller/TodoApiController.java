package com.doBattle.mydoBattle.controller;

import com.doBattle.mydoBattle.dto.todo.TodoRequestDto;
import com.doBattle.mydoBattle.dto.todo.TodoResponseDto;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.entity.Todo;
import com.doBattle.mydoBattle.exception.member.MemberNullException;
import com.doBattle.mydoBattle.service.TodoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoApiController {
    @Autowired
    private TodoService todoService;

    @PostMapping("/todo/create/{battleCode}")
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto dto, @PathVariable Long battleCode, HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        TodoResponseDto returnDto = todoService.createTodo(dto, member, battleCode);

        return ResponseEntity.status(HttpStatus.OK).body(returnDto);
    }

    @PatchMapping("/todo/click/{todoId}")
    public ResponseEntity<TodoResponseDto> successTodo(@PathVariable Long todoId, HttpSession session){
        Member member = (Member) session.getAttribute("currentMember");
        if(member == null)
            throw new MemberNullException("세션 없음.");

        TodoResponseDto returnDto = todoService.successTodo(todoId, member);

        return ResponseEntity.status(HttpStatus.OK).body(returnDto);
    }
}
