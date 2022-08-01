package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.dto.PlayerCreateDTO;
import bg.softuni.FootballWorld.model.dto.SearchPlayerDTO;
import bg.softuni.FootballWorld.model.view.PlayerView;
import bg.softuni.FootballWorld.schedules.PlayersSchedule;
import bg.softuni.FootballWorld.service.PlayerService;
import bg.softuni.FootballWorld.service.TeamService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final PlayersSchedule playersSchedule;

    public PlayerController(PlayerService playerService, TeamService teamService, PlayersSchedule playersSchedule) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.playersSchedule = playersSchedule;
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
    public String createPlayer(@Valid PlayerCreateDTO playerCreateDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("playerCreateDTO", playerCreateDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.playerCreateDTO", bindingResult);

            return "redirect:/players/create";
        }

        this.playerService.createPlayer(playerCreateDTO, userDetails);

        return "redirect:/players/all";
    }

    @GetMapping("/all")
    public String showAll(Model model, @PageableDefault(page = 0, size = 1) Pageable pageable) {
        model.addAttribute("top3players", playersSchedule.getTop3players());
        model.addAttribute("position", playersSchedule.getPosition());

        if (!model.containsAttribute("players")) {
            model.addAttribute("players", this.playerService.getAll(pageable));
        }


        return "players";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable("id") Long id, Model model) {
        model.addAttribute("player", this.playerService.getPlayerDetails(id));

        return "player-details";
    }

    @GetMapping("/my")
    public String myPlayers(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        List<PlayerView> myPlayers = this.playerService.getMyPlayers(userDetails);
        model.addAttribute("players", myPlayers);
        model.addAttribute("totalPrice", myPlayers.stream().mapToDouble(p -> p.getPrice().doubleValue()).sum());

        return "my-players";
    }

    @GetMapping("/{id}/buy")
    public String buyPlayer(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        this.playerService.buy(id, userDetails);

        return "redirect:/players/my";
    }

    @GetMapping("/{id}/sell")
    public String sellPlayer(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        this.playerService.sell(id, userDetails);

        return "redirect:/players/my";
    }



    @ModelAttribute("searchPlayerDTO")
    public SearchPlayerDTO initSearch() {
        return new SearchPlayerDTO();
    }


    @GetMapping("/search")
    public String searchQuery(@Valid SearchPlayerDTO searchPlayerDTO,
                              BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("searchPlayerDTO", searchPlayerDTO);
            model.addAttribute(
                    "org.springframework.validation.BindingResult.searchPlayerDTO",
                    bindingResult);
            return "redirect:/players/all";
        }

        if (!searchPlayerDTO.isEmpty()) {
            redirectAttributes.addFlashAttribute("players", playerService.searchOffer(searchPlayerDTO));
        }

        return "redirect:/players/all";
    }
}
