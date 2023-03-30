package com.memotalk.api.memouser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class MemoUserUnlockRequestDTO {

    @Schema(description = "비밀번호 (8자 이상)", example = "password123", required = true)
    @Size(min = 8, message = "비밀번호는 8자 이상이여야 합니다.")
    private String password;
}
