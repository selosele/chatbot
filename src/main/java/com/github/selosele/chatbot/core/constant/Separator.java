package com.github.selosele.chatbot.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 입력 값의 구분자를 정의하는 Enum
 */
@Getter
@RequiredArgsConstructor
public enum Separator {
    SLASH("/");

    private final String name;
}
