package com.github.selosele.chatbot.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 데이터 타입을 정의하는 Enum
 */
@Getter
@RequiredArgsConstructor
public enum DataType {
	JSON("json"),
	XML("xml");

	private final String name;
}
