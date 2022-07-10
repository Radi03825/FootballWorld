package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.dto.PlayerCreateDTO;
import bg.softuni.FootballWorld.service.PlayerService;
import bg.softuni.FootballWorld.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;

    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @ModelAttribute("playerCreateDTO")
    public PlayerCreateDTO init() {
        return new PlayerCreateDTO();
    }

    @GetMapping("/create")
    public String createPlayer(Model model) {

        model.addAttribute("teams", this.teamService.getAllTeamsOrderAlphabetical());

        return "player-add";
    }

    @PostMapping("/create")
    public String createPlayer(@Valid PlayerCreateDTO playerCreateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("playerCreateDTO", playerCreateDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.playerCreateDTO", bindingResult);

            return "redirect:/players/create";
        }

        this.playerService.createPlayer(playerCreateDTO);

        return "redirect:/";
    }
}