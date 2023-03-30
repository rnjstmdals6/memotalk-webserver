package com.memotalk.api.todo.entity.enumeration;

import lombok.Getter;

@Getter
public enum Status {
    TODO("할 것"),
    DONE("완료");

    private final String mode;

    Status(String status) {
        this.mode = status;
    }
}
