package ru.netodology.backendnet.controller;

import org.springframework.http.ResponseEntity;
import ru.netodology.backendnet.dto.LoginRq;

public interface IAuthController {

    ResponseEntity<?> login(LoginRq loginRq);
}
