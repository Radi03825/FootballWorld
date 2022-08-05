package bg.softuni.FootballWorld.web;


import bg.softuni.FootballWorld.model.dto.CommentCreateDTO;
import bg.softuni.FootballWorld.model.entity.*;
import bg.softuni.FootballWorld.model.view.CommentView;
import bg.softuni.FootballWorld.service.CommentService;
import bg.softuni.FootballWorld.service.exceptions.ObjectNotFoundException;
import bg.softuni.FootballWorld.util.TestDataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @MockBean
    private CommentService commentService;

    private UserEntity testUser;

    private PlayerEntity testPlayer;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.createTestUser("user@test.com");
        TeamEntity testTeam = testDataUtils.createTestTeam(testDataUtils.createTestStadium());

        SkillsEntity testSkills = testDataUtils.createTestSkills(77);

        testPlayer = testDataUtils.createTestPlayer(testUser, testTeam, testSkills);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    public void getComments() throws Exception {
        when(commentService.getAllComments(testPlayer.getId())).thenReturn(List.of(
                new CommentView(1L,"First comment", "User1", "2006-03-04 11:30:40"),
                new CommentView(2L,"Second comment", "User2", "2002-03-04 11:00:40")
        ));

        mockMvc.perform(get("/api/" + testPlayer.getId() + "/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username", is("User1")))
                .andExpect(jsonPath("$.[0].message", is("First comment")))
                .andExpect(jsonPath("$.[0].created", is("2006-03-04 11:30:40")))
                .andExpect(jsonPath("$.[1].username", is("User2")))
                .andExpect(jsonPath("$.[1].message", is("Second comment")))
                .andExpect(jsonPath("$.[1].created", is("2002-03-04 11:00:40")));
    }

    @Test
    public void getCommentsWrongIdFail() throws Exception {

        when(commentService.getAllComments((long) -1))
                .thenThrow(ObjectNotFoundException.class);

        mockMvc.perform(get("/api/{id}/comments", -1))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user@test.com", roles = "USER")
    public void createComment() throws Exception {
        when(commentService.createComment(any())).thenAnswer(interaction -> {
            CommentCreateDTO commentCreationDto = interaction.getArgument(0);
            return new CommentView(1L, commentCreationDto.getMessage(), commentCreationDto.getUsername());
        });

        CommentCreateDTO commentCreateDTO = new CommentCreateDTO();
        commentCreateDTO.setMessage("Test comment");
        commentCreateDTO.setPlayer(testPlayer.getId());

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/" + testPlayer.getId() + "/comments")
                        .content(objectMapper.writeValueAsString(commentCreateDTO))
                        .with(csrf())
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(jsonPath("$.message", is("Test comment")))
                .andExpect(jsonPath("$.username", is("user@test.com")));
    }

}
