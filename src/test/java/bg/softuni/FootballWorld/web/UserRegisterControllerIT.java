package bg.softuni.FootballWorld.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegistrationPageShown() throws Exception {
        mockMvc.perform(get("/users/register")).
                andExpect(status().isOk()).
                andExpect(view().name("register"));
    }

    @Test
    void testUserRegistration() throws Exception {
        mockMvc.perform(post("/users/register").
                        param("email", "test@register.com").
                        param("username", "RegisterTest").
                        param("firstName", "Test").
                        param("lastName", "Testov").
                        param("password", "password").
                        param("confirmPassword", "password").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/login"));
    }

    @Test
    void testUserRegistrationFail() throws Exception {
        mockMvc.perform(post("/users/register").
                        param("email", "test@register.com").
                        param("username", "").
                        param("firstName", "Test").
                        param("lastName", "Testov").
                        param("password", "password").
                        param("confirmPassword", "password").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/register"));
    }
}
