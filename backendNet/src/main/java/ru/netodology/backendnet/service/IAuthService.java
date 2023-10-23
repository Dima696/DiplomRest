package ru.netodology.backendnet.service;

import ru.netodology.backendnet.dto.LoginRq;
import ru.netodology.backendnet.dto.TokentDto;
import ru.netodology.backendnet.model.User;

public interface IAuthService {
    TokentDto login(LoginRq loginRq);

    User checkLoginAndPassword(String login, String password);
}
