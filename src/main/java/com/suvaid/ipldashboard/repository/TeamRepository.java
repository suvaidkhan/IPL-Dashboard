package com.suvaid.ipldashboard.repository;

import com.suvaid.ipldashboard.Model.Team;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByTeamName(String teamName);
}
