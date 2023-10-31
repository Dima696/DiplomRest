package ru.netodology.backendnet.service;

import ru.netodology.backendnet.dto.LoginRq;
import ru.netodology.backendnet.dto.TokenDto;
import ru.netodology.backendnet.model.User;

public interface AuthService {
    TokenDto login(LoginRq loginRq);

    User checkLoginAndPassword(String login, String password);

    Integer getUserIdByLogin(String login);
}
