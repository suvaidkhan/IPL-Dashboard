package com.suvaid.ipldashboard.repository;

import com.suvaid.ipldashboard.Model.Team;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByTeamName(String teamName);
}
