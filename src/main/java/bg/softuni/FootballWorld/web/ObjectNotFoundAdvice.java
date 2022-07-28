package bg.softuni.FootballWorld.web;

import bg.softuni.FootballWorld.service.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ObjectNotFoundAdvice {

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView onProductNotFound(ObjectNotFoundException onfe) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", onfe.getMessage());

        return modelAndView;

    }
}
