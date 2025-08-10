package com.github.selosele.chatbot.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 봇의 카테고리를 정의하는 Enum
 */
@Getter
@RequiredArgsConstructor
public enum Category {
	HOLIDAY("공휴일"),
	BUS("버스"),
	SUBWAY("지하철");

	private final String name;
}
