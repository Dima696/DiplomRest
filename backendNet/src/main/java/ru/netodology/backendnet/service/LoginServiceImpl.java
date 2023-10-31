package ru.netodology.backendnet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netodology.backendnet.config.JwtService;
import ru.netodology.backendnet.dto.LoginRq;
import ru.netodology.backendnet.dto.TokenDto;
import ru.netodology.backendnet.exception.AuthorizationException;
import ru.netodology.backendnet.model.User;
import ru.netodology.backendnet.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    @Override
    public TokenDto login(LoginRq loginRq) {
        User user = checkLoginAndPassword(loginRq.login(), loginRq.password());
        String token = jwtService.generateToken(user);
        authenticated(loginRq);
        return new TokenDto(token);
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

    @Override
    public Integer getUserIdByLogin(String login) {
        User person = userRepository.findByLogin(login)
                .orElseThrow(() -> new AuthorizationException("Email '%s' is not registered".formatted(login)));
        return person.getId();
    }

    private void authenticated(LoginRq loginRq) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRq.login(), loginRq.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
