package bg.softuni.FootballWorld.config;

import bg.softuni.FootballWorld.repository.UserRepository;
import bg.softuni.FootballWorld.service.FootballWorldUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                 authorizeRequests().
                 requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                 antMatchers("/", "/players/all", "/players/search", "/teams/all", "/contacts", "/about").permitAll().
                 antMatchers("/users/all", "/users/**/upgrade", "/players/create", "/teams/create").hasRole("ADMIN").
                 antMatchers("/players/create").hasRole("MODERATOR").
                 antMatchers( "/players/my", "game").authenticated().
                 antMatchers("/players/**", "/teams/**", "/api/**").permitAll().
                 antMatchers( "/users/login", "/users/register").anonymous().
                 anyRequest().
                authenticated().
                and().
                 formLogin().
                 loginPage("/users/login").
                 usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                 passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                 defaultSuccessUrl("/").
                 failureForwardUrl("/users/login-error").
                and().
                 logout().
                 logoutUrl("/users/logout").
                 logoutSuccessUrl("/").
                 invalidateHttpSession(true).
                deleteCookies("JSESSIONID");


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new FootballWorldUserDetailsService(userRepository);
    }
}
