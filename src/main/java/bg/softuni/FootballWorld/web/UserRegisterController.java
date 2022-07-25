package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.dto.UserRegisterDTO;
import bg.softuni.FootballWorld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserRegisterController {

    private final UserService userService;

    @Autowired
    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRegisterDTO")
    public UserRegisterDTO init() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);

            return "redirect:/users/register";
        }

        this.userService.register(userRegisterDTO);

        return "redirect:/users/login";
    }
}
