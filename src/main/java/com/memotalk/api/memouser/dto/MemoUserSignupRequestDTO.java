package com.memotalk.api.memouser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class MemoUserSignupRequestDTO {

    @Schema(description = "이메일", example = "example@example.com", required = true)
    @Email
    private String email;

    @Schema(description = "비밀번호 (8자 이상)", example = "password123", required = true)
    @Size(min = 8, message = "비밀번호는 8자 이상이여야 합니다.")
    private String password;
}