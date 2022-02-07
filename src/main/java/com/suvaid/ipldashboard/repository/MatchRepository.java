package com.suvaid.ipldashboard.repository;

import java.util.List;

import com.suvaid.ipldashboard.Model.Match;

import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2);
}
