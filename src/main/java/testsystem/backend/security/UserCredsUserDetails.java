package testsystem.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import testsystem.backend.model.user.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class implementing Spring Security's UserDetails interface to represent the user's login credentials and roles.
 */
public class UserCredsUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;

    /**
     * Constructs a new UserCredsUserDetails object based on the given User object.
     *
     @param user User object from which to extract the user's credentials and roles.
     */
    public UserCredsUserDetails(User user) {
        email = user.getEmail();
        password = user.getPassword();
        authorities = Arrays.stream(
                user.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
