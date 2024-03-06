package com.doBattle.mydoBattle.service;

import com.doBattle.mydoBattle.dto.todo.TodoRequestDto;
import com.doBattle.mydoBattle.dto.todo.TodoResponseDto;
import com.doBattle.mydoBattle.entity.Battle;
import com.doBattle.mydoBattle.entity.Member;
import com.doBattle.mydoBattle.entity.Todo;
import com.doBattle.mydoBattle.exception.battle.BattleNullException;
import com.doBattle.mydoBattle.repository.BattleRepository;
import com.doBattle.mydoBattle.repository.JoinBattleRepository;
import com.doBattle.mydoBattle.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoService {
    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private JoinBattleRepository joinBattleRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto dto, Member member, Long battleCode) {
        Battle battle = battleRepository.findById(battleCode)
                .orElseThrow(()-> new BattleNullException("배틀코드에 해당하는 배틀이 존재하지 않습니다."));
        if(joinBattleRepository.findByBattleCodeAndCurrentMember(battle.getBattleCode(), member.getId()).isEmpty())
            throw new BattleNullException("해당 유저는 해당 배틀에 아직 참여하지 않았습니다.");

        Todo todo = Todo.createTodo(dto, member, battle);
        todoRepository.save(todo);

        return TodoResponseDto.createDto(todo);
    }
}
