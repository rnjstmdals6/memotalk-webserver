package com.memotalk.api.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemoMarkImportantRequestDTO {

    @NotNull
    private Long workspaceId;

    @NotNull
    private Long memoId;
}
