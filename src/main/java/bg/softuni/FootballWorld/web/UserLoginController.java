package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.dto.UserLoginDTO;
import bg.softuni.FootballWorld.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserLoginController {

    private UserServiceImpl userService;

    @Autowired
    public UserLoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ModelAttribute("userLoginDTO")
    public UserLoginDTO init() {
        return new UserLoginDTO();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDTO userLoginDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO",
                    bindingResult);

            return "redirect:/login";
        }

        this.userService.login(userLoginDTO);

        return "redirect:/";
    }
}
