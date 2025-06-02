package com.github.selosele.chatbot.app.bot.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 봇의 응답 DTO
 */
public class BotResponseDTO {

	@Getter
	@Builder
	@ToString
	public static class Response {
		private List<Data> data;
		private String message;
	}

	@Getter
	@Builder
	@ToString
	public static class Data {
		private String dateName;
		private String isHoliday;
		private String locdate;
	}
}
