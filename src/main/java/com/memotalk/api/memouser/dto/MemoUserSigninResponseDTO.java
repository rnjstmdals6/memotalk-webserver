package com.memotalk.api.memouser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoUserSigninResponseDTO {

    @Schema(description = "인증에 사용되는 액세스 토큰")
    private String accessToken;
}