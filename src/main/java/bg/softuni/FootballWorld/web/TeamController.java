package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.dto.TeamCreateDTO;
import bg.softuni.FootballWorld.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @ModelAttribute("teamCreateDTO")
    public TeamCreateDTO init() {
        return new TeamCreateDTO();
    }

    @GetMapping("/create")
    public String create() {
        return "team-add";
    }

    @PostMapping("/create")
    public String createPlayer(@Valid TeamCreateDTO teamCreateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("teamCreateDTO", teamCreateDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.teamCreateDTO", bindingResult);

            return "redirect:/teams/create";
        }

        this.teamService.create(teamCreateDTO);

        return "redirect:/";
    }
}