package com.suvaid.ipldashboard;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.suvaid.ipldashboard.Model.Match;
import com.suvaid.ipldashboard.Controller.TeamController;
import com.suvaid.ipldashboard.Model.Team;
import com.suvaid.ipldashboard.repository.MatchRepository;
import com.suvaid.ipldashboard.repository.TeamRepository;
import com.suvaid.ipldashboard.IplDashboardApplication;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TeamController.class)
// @SpringBootTest(classes = TeamController.class)
public class TeamControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private TeamRepository teamRepositoryMock;
        @MockBean
        private MatchRepository matchRepositoryMock;

        @Test
        void testGetAllTeamReturns200() throws Exception {
                when(teamRepositoryMock.findAll()).thenReturn(Collections.singletonList(new Team()));
                mockMvc
                                .perform(get("/team"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$").isNotEmpty());
        }

        @Test
        void testGetTeam_whenValidInput_thenReturns200() throws Exception {
                String expectedTeamName = "Test Team";
                int expectedTotalMatches = 50;
                int expectedNumberOfLatestMatches = 4;

                when(teamRepositoryMock.findByTeamName(expectedTeamName))
                                .thenReturn(new Team(expectedTeamName, expectedTotalMatches));
                when(matchRepositoryMock.findLatestMatchesByTeam(eq(expectedTeamName), anyInt()))
                                .thenReturn(Collections.nCopies(expectedNumberOfLatestMatches, new Match()));

                mockMvc
                                .perform(get("/team/{teamName}", expectedTeamName))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isNotEmpty())
                                .andExpect(jsonPath("$.teamName").value(expectedTeamName))
                                .andExpect(jsonPath("$.totalMatches").value(expectedTotalMatches))
                                .andExpect(jsonPath("$.matches").isNotEmpty())
                                .andExpect(jsonPath("$.matches", hasSize(expectedNumberOfLatestMatches)));
        }

        @Test
        void testGetMatchesForTeam_whenValidInput_thenReturns200() throws Exception {
                String teamName = "Test Team";
                int expectedNumberOfMatches = 2;

                when(matchRepositoryMock.getMatchesByTeamBetweenDates(
                                eq(teamName), any(LocalDate.class), any(LocalDate.class)))
                                .thenReturn(Collections.nCopies(expectedNumberOfMatches, new Match()));

                mockMvc
                                .perform(get("/team/{teamName}/matches", teamName).param("year", "2020"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$", hasSize(expectedNumberOfMatches)));
        }
}