package com.motafelipe.api.backoffice.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UserLoginResponseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private Long expireIn;
    private String tokenProvider;
}