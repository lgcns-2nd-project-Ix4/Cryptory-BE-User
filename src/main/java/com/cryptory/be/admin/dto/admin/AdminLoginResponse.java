package com.cryptory.be.admin.dto.admin;

import lombok.Data;

@Data
public class AdminLoginResponse {
    private Long id;
    private String username;
    private String nickname;

    public AdminLoginResponse(Long id, String username, String nickname) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }
}
