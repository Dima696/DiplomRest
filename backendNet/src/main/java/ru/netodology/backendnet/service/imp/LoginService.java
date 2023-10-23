package ru.netodology.backendnet.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netodology.backendnet.config.JwtService;
import ru.netodology.backendnet.dto.LoginRq;
import ru.netodology.backendnet.dto.TokentDto;
import ru.netodology.backendnet.exception.AuthorizationException;
import ru.netodology.backendnet.model.User;
import ru.netodology.backendnet.repository.UserRepository;
import ru.netodology.backendnet.service.IAuthService;

@Service
@RequiredArgsConstructor
public class LoginService implements IAuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    @Override
    public TokentDto login(LoginRq loginRq) {
        User user = checkLoginAndPassword(loginRq.login(), loginRq.password());
        String token = jwtService.generateToken(user);
        authenticated(loginRq);
        return new TokentDto(token);
    }

    @Override

    public User checkLoginAndPassword(String login, String password) {
        User person = userRepository.findByLogin(login)
                .orElseThrow(() -> new AuthorizationException("Email '%s' is not registered".formatted(login)));

        if (!new BCryptPasswordEncoder().matches(password, person.getPassword())) {
            throw new AuthorizationException("Incorrect password");
        }

        return person;
    }

    private void authenticated(LoginRq loginRq) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRq.login(), loginRq.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
