package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.model.dto.GameDTO;
import bg.softuni.FootballWorld.model.dto.GameResultDTO;
import bg.softuni.FootballWorld.model.dto.PlayerCreateDTO;
import bg.softuni.FootballWorld.service.GameService;
import bg.softuni.FootballWorld.service.PlayerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class GameController {

    private final GameService gameService;
    private final PlayerService playerService;

    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @ModelAttribute("gameDTO")
    public GameDTO init() {
        return new GameDTO();
    }

    @GetMapping("/game")
    public String game(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("players", this.playerService.getMyPlayers(userDetails));
        model.addAttribute("opponents", this.gameService.loadOpponents());

        return "game";
    }

    @GetMapping("/game/result")
    public String result(Model model) {

        if (!model.containsAttribute("result")) {
            return "redirect:/game";
        }

        return "game-result";
    }

    @PostMapping("/game")
    public String play(@Valid GameDTO gameDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("gameDTO", gameDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gameDTO", bindingResult);

            return "redirect:/game";
        }

        if (Objects.equals(gameDTO.getFirstPlayer(), gameDTO.getSecondPlayer())) {
            redirectAttributes.addFlashAttribute("samePlayers", true);

            return "redirect:/game";
        }

        GameResultDTO resultDTO = this.gameService.play(gameDTO);

        redirectAttributes.addFlashAttribute("result", resultDTO);

        return "redirect:/game/result";
    }

}
