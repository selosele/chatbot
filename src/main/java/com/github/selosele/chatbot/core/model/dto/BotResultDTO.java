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

	private List<T> data;
	private String input;
	private String message;

	public static <T> BotResultDTO<T> of(List<T> data, String input, String message) {
		return BotResultDTO.<T>builder()
				.data(data)
				.input(input)
				.message(message)
				.build();
	}

}
