package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.entity.PlayerEntity;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private UserEntity testUser, testAdmin;

    private PlayerEntity testPlayer, testAdminPlayer;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.createTestUser("user@test.com");
        testAdmin = testDataUtils.createTestAdmin("admin@test.com");
        TeamEntity testTeam = testDataUtils.createTestTeam(testDataUtils.createTestStadium());

        SkillsEntity testSkills = testDataUtils.createTestSkills(77);
        SkillsEntity testAdminSkills = testDataUtils.createTestSkills(88);

        testPlayer = testDataUtils.createTestPlayer(testUser, testTeam, testSkills);
        testAdminPlayer = testDataUtils.createTestPlayer(testAdmin, testTeam, testAdminSkills);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testGamePageShownWithoutLoggedUser() throws Exception {
        mockMvc.perform(get("/game"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user@test.com", roles = "USER")
    void testPlay() throws Exception {
        mockMvc.perform(post("/game")
                        .param("firstPlayer", "1")
                        .param("secondPlayer", "2")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game/result"));
    }

    @Test
    @WithMockUser(username = "user@test.com", roles = "USER")
    void testPlayWithoutPlayers() throws Exception {
        mockMvc.perform(post("/game")
                        .param("firstPlayer", "")
                        .param("secondPlayer", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"));
    }

    @Test
    @WithMockUser(username = "user@test.com", roles = "USER")
    void testPlayWithSamePlayers() throws Exception {
        mockMvc.perform(post("/game")
                        .param("firstPlayer", "1")
                        .param("secondPlayer", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"));
    }
}
