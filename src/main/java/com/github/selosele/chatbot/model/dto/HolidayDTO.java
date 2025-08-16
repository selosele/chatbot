package com.github.selosele.chatbot.model.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.selosele.chatbot.core.model.dto.HttpRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 공휴일 DTO
 */
public class HolidayDTO {

	/**
	 * 공휴일 조회 요청 DTO
	 */
	@Getter
	@Setter
	@Builder
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request extends HttpRequestDTO {

		/** 연 */
		private String solYear;

		/** 월 */
		private String solMonth;

		public static Request of(String serviceKey, String solYear, String solMonth, String numOfRows) {
			var dto = new Request();
			dto.setServiceKey(serviceKey);
			dto.setSolYear(solYear);
			dto.setSolMonth(solMonth);
			dto.setNumOfRows(numOfRows);
			return dto;
		}
	}

	/**
	 * 공휴일 응답 DTO
	 */
	@Getter
	@Builder
	@ToString
	public static class Response {

		/** 명칭 */
		private String dateName;

		/** 공공기관 휴일여부 */
		private String isHoliday;

		/** 날짜 */
		private String locDate;

		public static Response of(JsonNode item) {
			return Response.builder()
					.dateName(item.path("dateName").asText(""))
					.isHoliday(item.path("isHoliday").asText(""))
					.locDate(item.path("locdate").asText(""))
					.build();
		}
	}

}
