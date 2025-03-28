package com.cryptory.be.user.dto;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String userId;
    private String password;
}
