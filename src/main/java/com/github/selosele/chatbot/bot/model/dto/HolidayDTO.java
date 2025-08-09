package com.github.selosele.chatbot.bot.model.dto;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 공휴일 데이터 DTO
 */
@Getter
@Builder
@ToString
public class HolidayDTO {
	
	private String dateName;
	private String isHoliday;
	private String locDate;

	public static HolidayDTO of(JsonNode item) {
		return HolidayDTO.builder()
			.dateName(item.path("dateName").asText(""))
			.isHoliday(item.path("isHoliday").asText(""))
			.locDate(item.path("locdate").asText(""))
			.build();
	}
	
}
