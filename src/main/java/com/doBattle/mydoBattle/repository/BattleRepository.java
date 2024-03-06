package com.doBattle.mydoBattle.repository;

import com.doBattle.mydoBattle.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {

}
