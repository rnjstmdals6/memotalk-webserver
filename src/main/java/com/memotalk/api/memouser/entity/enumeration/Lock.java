package com.memotalk.api.memouser.entity.enumeration;

import lombok.Getter;

@Getter
public enum Lock {
    UNLOCK("잠금 해제"),
    LOCK("잠금 모드");

    private final String isLock;

    Lock(String status) {
        this.isLock = status;
    }
}
