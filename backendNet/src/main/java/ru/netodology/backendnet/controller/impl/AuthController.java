package ru.netodology.backendnet.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netodology.backendnet.controller.IAuthController;
import ru.netodology.backendnet.dto.LoginRq;
import ru.netodology.backendnet.dto.TokentDto;
import ru.netodology.backendnet.service.imp.LoginService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements IAuthController {
    private final LoginService loginService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<TokentDto> login(@RequestBody LoginRq loginRq) {
        log.info("User with login={}, password={} have sent request for authorization",
                loginRq.login(), loginRq.password());
        return ResponseEntity.ok(loginService.login(loginRq));
    }
}
