package com.github.selosele.chatbot.core.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 봇의 조회 결과 DTO
 */
@Getter
@Builder
@ToString
public class BotResultDTO<T> {

	/** 조회된 데이터 리스트 */
	private List<T> data;

	/**
	 * 사용자 입력
	 * 예) "공휴일/2025/01"
	 */
	private String input;

	/** 결과 메시지 */
	private String message;

	public static <T> BotResultDTO<T> of(List<T> data, String input, String message) {
		return BotResultDTO.<T>builder()
				.data(data)
				.input(input)
				.message(message)
				.build();
	}

}
