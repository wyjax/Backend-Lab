package com.wyjax.querydsl.team.repository;

import com.wyjax.querydsl.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
