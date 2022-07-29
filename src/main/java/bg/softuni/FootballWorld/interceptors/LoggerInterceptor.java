package bg.softuni.FootballWorld.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LOGGER.info("[preHandle][" + request + "]" + " [method: " + request.getMethod()
                + "] [uri: " + request.getRequestURI() + "]");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView == null) {
            return;
        }

        LOGGER.info("[postHandler] [" + request +"]");

        modelAndView.getModel().keySet()
                .stream()
                .forEach(e -> LOGGER.info(e + ": " + modelAndView.getModel().get(e)));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (ex != null) {
            ex.printStackTrace();
        }

        LOGGER.info("[afterCompletion] [" + request + "] [exception: " + ex + "]");
    }
}
