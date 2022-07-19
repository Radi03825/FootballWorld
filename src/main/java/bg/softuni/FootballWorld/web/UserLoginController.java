package bg.softuni.FootballWorld.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String loginError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }
}
