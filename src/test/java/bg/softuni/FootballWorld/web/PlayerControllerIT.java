package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.entity.*;
import bg.softuni.FootballWorld.repository.PlayerRepository;
import bg.softuni.FootballWorld.service.cloudinary.CloudinaryService;
import bg.softuni.FootballWorld.util.TestDataUtils;
import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @MockBean
    private CloudinaryService cloudinaryService;

    private UserEntity testUser, testModerator, testAdmin;

    private PlayerEntity testPlayer, testAdminPlayer;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.createTestUser("user@test.com");
        testModerator = testDataUtils.createTestModerator("moderator@test.com");
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
    void testPlayersAllPageShown() throws Exception {
        mockMvc.perform(get("/players/all")).
                andExpect(status().isOk()).
                andExpect(view().name("players"));
    }

    @Test
    @WithMockUser(value = "moderator@test.com", roles = {"USER", "MODERATOR"})
    void testPlayerCreatePageShown() throws Exception {


        mockMvc.perform(get("/players/create")).
                andExpect(status().isOk()).
                andExpect(view().name("player-add"));
    }

    @WithMockUser(username = "user@test.com", roles = "USER")
    @Test
    public void testDeleteNotOwned_Forbidden() throws Exception {
        mockMvc.perform(delete("/players/{id}", testAdminPlayer.getId()).
                        with(csrf())
                ).
                andExpect(status().isForbidden());
    }

    @Test
    public void testViewDetails() throws Exception {
        mockMvc.perform(get("/players/{id}", testPlayer.getId())).
                andExpect(status().isOk());
    }

    @Test
    public void testViewDetailsFail() throws Exception {
        mockMvc.perform(get("/players/{id}", -1)).
                andExpect(status().isNotFound());
    }
}
