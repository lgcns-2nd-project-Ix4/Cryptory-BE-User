package com.cryptory.be.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private String userId;
    private String nickname;
    private String imageUrl;
}
