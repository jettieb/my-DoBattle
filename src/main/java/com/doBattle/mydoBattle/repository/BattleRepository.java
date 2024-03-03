package com.doBattle.mydoBattle.repository;

import com.doBattle.mydoBattle.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {
    List<Battle> findByMemberId(Long memberId);

    @Query(value="SELECT * FROM battle WHERE battle_code = :battleCode AND member_id != :memberId", nativeQuery = true)
    //나를 제외한 배틀 참여자 리스트 찾기
    List<Battle> findByBattleCodeWithoutCurrentMember(Long battleCode, Long memberId);
}
