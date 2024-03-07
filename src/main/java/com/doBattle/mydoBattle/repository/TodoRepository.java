package com.doBattle.mydoBattle.repository;

import com.doBattle.mydoBattle.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query(value = "SELECT * FROM todo " +
            "WHERE battle_code = :battleCode AND member_id = :memberId AND create_date = :date " +
            "ORDER BY click DESC", nativeQuery = true)
    //참여자의 해당 배틀의 날짜에 따른 투두 찾기 (완료한 투두 먼저 보이도록)
    List<Todo> findByBattleCodeAndMemberIdAndDate(Long battleCode, Long memberId, LocalDate date);

    @Query(value = "SELECT * FROM todo WHERE battle_code = :battleCode AND member_id = :memberId AND create_date = :date AND click = true", nativeQuery = true)
    //참여자의 해당 배틀의 날짜에 따른 투두 찾기(True만)
    List<Todo> findByBattleCodeAndMemberIdAndDateWithTrueValue(Long battleCode, Long memberId, LocalDate date);
}
