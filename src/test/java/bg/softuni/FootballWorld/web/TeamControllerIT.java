package bg.softuni.FootballWorld.web;


import bg.softuni.FootballWorld.model.entity.SkillsEntity;
import bg.softuni.FootballWorld.model.entity.TeamEntity;
import bg.softuni.FootballWorld.model.entity.UserEntity;
import bg.softuni.FootballWorld.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private UserEntity testUser, testAdmin;

    private TeamEntity testTeam;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.createTestUser("user@test.com");
        testAdmin = testDataUtils.createTestAdmin("admin@test.com");
        testTeam = testDataUtils.createTestTeam(testDataUtils.createTestStadium());
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testTeamsPageShown() throws Exception {
        mockMvc.perform(get("/teams/all")).
                andExpect(status().isOk()).
                andExpect(view().name("teams"));
    }

    @Test
    @WithMockUser(value = "admin@test.com", roles = {"USER", "MODERATOR", "ADMIN"})
    void testTeamsCreatePageShown() throws Exception {


        mockMvc.perform(get("/teams/create")).
                andExpect(status().isOk()).
                andExpect(view().name("team-add"));
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = {"USER", "MODERATOR", "ADMIN"})
    void createTeam() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/teams/create")
                        .param("name", "Test").
                        param("established", "2000-02-03").
                        param("badgeImageUrl", "https://image.com/badge").
                        param("stadium", "Stadium").
                        with(csrf())).
                andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = {"USER", "MODERATOR", "ADMIN"})
    void testDeleteByAdmin() throws Exception {
        mockMvc.perform(delete("/teams/{id}", testTeam.getId()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/teams/all"));
    }


    @WithMockUser(username = "user@test.com", roles = "USER")
    @Test
    public void testDeleteNotAdmin_Forbidden() throws Exception {
        mockMvc.perform(delete("/teams/{id}", testTeam.getId()).
                        with(csrf())
                ).
                andExpect(status().isForbidden());
    }

    @Test
    public void testViewDetails() throws Exception {
        mockMvc.perform(get("/teams/{id}", testTeam.getId())).
                andExpect(status().isOk());
    }

    @Test
    public void testViewDetailsFail() throws Exception {
        mockMvc.perform(get("/teams/{id}", -1)).
                andExpect(status().isNotFound());
    }
}
