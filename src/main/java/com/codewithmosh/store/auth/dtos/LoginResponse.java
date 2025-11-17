package com.codewithmosh.store.auth.dtos;

import com.codewithmosh.store.auth.services.Jwt;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private Jwt accessToken;
    private Jwt refreshToken;
}
