package com.github.selosele.chatbot.bot.model.dto;

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
	}
	
}
