package com.github.selosele.chatbot.app.bot.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * 봇의 응답 DTO
 */
public class BotResponseDTO {

	@Getter
	@Builder
	public static class Response {
		private List<Data> data;
		private String message;
	}

	@Getter
	@Builder
	public static class Data {
		private String dateName;
		private String isHoliday;
		private String locdate;
	}
}
