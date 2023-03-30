package com.memotalk.api.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthCodeRequestDTO {

    @Schema(description = "이메일 주소", example = "rnjstmdals6@gmail.com")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}