package org.example.repository;

import org.example.domain.entity.UserLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitRepository extends JpaRepository<UserLimit, Long> {

    @Modifying
    @Query("""
            UPDATE UserLimit l SET l.dailyUsage = 0
            """)
    int resetLimitsForNewDay();
}
