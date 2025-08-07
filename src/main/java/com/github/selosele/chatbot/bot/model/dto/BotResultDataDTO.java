package com.github.selosele.chatbot.bot.model.dto;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 봇의 데이터 조회 결과 DTO
 */
public class BotResultDataDTO {

	/**
	 * 공휴일 데이터 DTO
	 */
	@Getter
	@Builder
	@ToString
	public static class Holiday {
		private String dateName;
		private String isHoliday;
		private String locdate;

		public static Holiday of(JsonNode item) {
			return Holiday.builder()
				.dateName(item.path("dateName").asText(""))
				.isHoliday(item.path("isHoliday").asText(""))
				.locdate(item.path("locdate").asText(""))
				.build();
		}
	}
	
}
