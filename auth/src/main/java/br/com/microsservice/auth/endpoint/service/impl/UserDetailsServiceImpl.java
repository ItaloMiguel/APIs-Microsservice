package br.com.microsservice.auth.endpoint.service.impl;

import br.com.microsservice.auth.endpoint.model.ApplicationUser;
import br.com.microsservice.auth.endpoint.repository.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Searching in the DB the user by username '{}'", username);
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        log.info("ApplicationUser found? '{}'", applicationUser);
        verifyIfApplicationUserWasFound(applicationUser, username);

        return new CustomUserDetails(applicationUser);
    }

    private void verifyIfApplicationUserWasFound(ApplicationUser applicationUser, String username) {
        if(applicationUser == null) {
            throw new UsernameNotFoundException(String.format("ApplicationUser '%s' not found", username));
        }
    }

    private static final class CustomUserDetails extends ApplicationUser implements UserDetails{

        public CustomUserDetails(@NotNull ApplicationUser applicationUser) {
            super(applicationUser);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return commaSeparatedStringToAuthorityList("ROLE_" + this.getRole());
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    }
}

