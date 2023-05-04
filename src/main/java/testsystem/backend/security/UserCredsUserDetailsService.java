package testsystem.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import testsystem.backend.model.user.User;
import testsystem.backend.repository.user.UserRepository;

import java.util.Optional;

/**
 * A service that loads user-specific data based on the email.
 */
@Component
public class UserCredsUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load user-specific data based on the email.
     *
     * @param email User's email.
     * @return UserCredsUserDetails user details.
     * @throws UsernameNotFoundException If user not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserCredsUserDetails::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User " + email + " not found")
                );
    }
}
