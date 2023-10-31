package ru.netodology.backendnet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netodology.backendnet.dto.LoginRq;
import ru.netodology.backendnet.dto.TokenDto;
import ru.netodology.backendnet.service.AuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRq loginRq) {
        log.info("User with login={}, password={} have sent request for authorization",
                loginRq.login(), loginRq.password());
        return ResponseEntity.ok(authService.login(loginRq));
    }
}
