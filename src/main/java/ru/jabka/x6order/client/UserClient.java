package ru.jabka.x6order.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jabka.x6order.model.Exists;

@Component
@RequiredArgsConstructor
public class UserClient {
    private final RestTemplate userService;

    public boolean isUserExists(Long userId) {
        return userService.getForObject("/api/v1/user/exists?id=" + userId, Exists.class).exists();
    }
}