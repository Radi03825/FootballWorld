package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.dto.TeamCreateDTO;
import bg.softuni.FootballWorld.model.entity.TeamEntity;
import bg.softuni.FootballWorld.service.TeamService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

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

        return "redirect:/teams/all";
    }

    @GetMapping("/all")
    public String showAll(Model model, @PageableDefault(page = 0, size = 9) Pageable pageable) {

        model.addAttribute("teams", this.teamService.getAll(pageable));

        return "teams";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable("id") Long id, Model model) {
        model.addAttribute("team", this.teamService.getTeamDetails(id));

        return "team-details";
    }

}
