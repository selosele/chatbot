package com.github.selosele.chatbot.app.bot.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 봇의 응답 DTO
 */
public class BotResponseDTO {

	/**
	 * 봇의 응답을 위한 DTO
	 */
	@Getter
	@Builder
	@ToString
	public static class Response<T> {
		private List<T> data;
		private String message;
	}
	
}
