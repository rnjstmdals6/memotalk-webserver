package com.memotalk.api.workspace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class WorkSpaceModifyRequestDTO {

    @Schema(description = "워크스페이스 ID", required = true, example = "1")
    private Long id;

    @NotNull
    @Schema(description = "수정할 새로운 워크스페이스 타이틀", required = true, example = "새로운 회의록")
    private String newTitle;
}