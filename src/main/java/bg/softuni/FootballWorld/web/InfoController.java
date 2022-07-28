package bg.softuni.FootballWorld.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {

    @GetMapping("/contacts")
    public String contacts() {
        return "contact";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
