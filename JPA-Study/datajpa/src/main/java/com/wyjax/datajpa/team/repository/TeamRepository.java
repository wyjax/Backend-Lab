package com.wyjax.datajpa.team.repository;

import com.wyjax.datajpa.team.doamin.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
