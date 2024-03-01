package com.doBattle.mydoBattle.repository;

import com.doBattle.mydoBattle.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
    Member findByIdentify(String identify);
}
