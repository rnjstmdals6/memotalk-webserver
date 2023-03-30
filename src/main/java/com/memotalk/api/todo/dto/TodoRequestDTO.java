package com.memotalk.api.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoRequestDTO {

    @Schema(description = "작업을 등록할 워크스페이스 ID", required = true, example = "1")
    @NotNull(message = "워크스페이스 ID는 필수 입력 항목입니다.")
    private Long workspaceId;

    @Schema(description = "할 일 내용", example = "해커톤 수행")
    private String content;
}
