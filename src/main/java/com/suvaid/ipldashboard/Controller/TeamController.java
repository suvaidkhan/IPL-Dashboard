package com.suvaid.ipldashboard.Controller;

import com.suvaid.ipldashboard.Model.Team;
import com.suvaid.ipldashboard.repository.MatchRepository;
import com.suvaid.ipldashboard.repository.TeamRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team = this.teamRepository.findByTeamName(teamName);
        team.setMatches(matchRepository.getByTeam1OrTeam2OrderByDateDesc(teamName, teamName));
        return team;
    }
}
