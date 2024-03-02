package com.doBattle.mydoBattle.repository;

import com.doBattle.mydoBattle.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleRepository extends JpaRepository<Battle, Long> {
}
