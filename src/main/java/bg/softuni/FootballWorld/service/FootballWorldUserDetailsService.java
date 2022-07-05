package bg.softuni.FootballWorld.service;

import bg.softuni.FootballWorld.model.entity.UserEntity;
import bg.softuni.FootballWorld.model.entity.UserRoleEntity;
import bg.softuni.FootballWorld.model.user.FootballWorldUserDetails;
import bg.softuni.FootballWorld.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class FootballWorldUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public FootballWorldUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.
                findByEmail(username).
                map(this::map).
                orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found!"));
    }

    private UserDetails map(UserEntity userEntity) {

        return new FootballWorldUserDetails(
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.
                        getUserRoles().
                        stream().
                        map(this::map).
                        toList()
        );
    }

    private GrantedAuthority map(UserRoleEntity userRole) {
        return new SimpleGrantedAuthority("ROLE_" +
                userRole.
                        getUserRole().name());
    }
}
