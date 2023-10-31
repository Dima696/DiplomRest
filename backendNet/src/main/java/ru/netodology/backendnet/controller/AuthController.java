package ru.netodology.backendnet.controller;

import org.springframework.http.ResponseEntity;
import ru.netodology.backendnet.dto.LoginRq;

public interface AuthController {

    ResponseEntity<?> login(LoginRq loginRq);
}
