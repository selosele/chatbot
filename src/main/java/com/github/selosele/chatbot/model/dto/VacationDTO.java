package com.github.selosele.chatbot.model.dto;

import java.util.List;

import com.github.selosele.chatbot.core.model.dto.HttpRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 휴가 DTO
 */
public class VacationDTO {

	/**
	 * 휴가 조회 요청 DTO
	 */
	@Getter
	@Setter
	@Builder
	@ToString
	public static class Request extends HttpRequestDTO {

		public static Request of(String accessToken, String userId) {
			var dto = new Request();
			dto.setAccessToken(accessToken);
			dto.setUserId(Integer.parseInt(userId));
			return dto;
		}
	}

	/**
	 * 휴가 응답 DTO
	 */
	@Getter
	@Builder
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {

		/** 휴가일수정보 */
		private CountInfoResult countInfo;

		/** 휴가 통계 목록 */
		private List<StatsResult> statsList;

		/** 휴가 목록 */
		private List<Result> vacationList;
	}

	/**
	 * 휴가일수정보 조회 결과 DTO
	 */
	@Getter
	@Builder
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CountInfoResult {

		/** 총 휴가 일수 */
		private int vacationTotalCount;

		/** 휴가 사용 일수 */
		private int vacationUseCount;

		/** 휴가 잔여 일수 */
		private int vacationRemainCount;
	}

	/**
	 * 휴가 통계 조회 결과 DTO
	 */
	@Getter
	@Builder
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StatsResult {

		/** 휴가 통계 ID */
		private int vacationStatsId;

		/** 직원 ID */
		private int employeeId;

		/** 근무이력 ID */
		private int workHistoryId;

		/** 연도 */
		private String yyyy;

		/** 휴가 구분 코드 */
		private String vacationTypeCode;

		/** 휴가 구분 코드명 */
		private String vacationTypeCodeName;

		/** 휴가 사용 일수 */
		private int vacationUseCount;
	}

	/**
	 * 휴가 조회 결과 DTO
	 */
	@Getter
	@Builder
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Result {

		/** 휴가 ID */
		private int vacationId;

		/** 직원 ID */
		private int employeeId;

		/** 근무이력 ID */
		private int workHistoryId;

		/** 휴가 구분 코드 */
		private String vacationTypeCode;

		/** 휴가 구분 코드명 */
		private String vacationTypeCodeName;

		/** 휴가 내용 */
		private String vacationContent;

		/** 휴가 시작일자 */
		private String vacationStartYmd;

		/** 휴가 종료일자 */
		private String vacationEndYmd;

		/** 휴가 사용일수 */
		private Double vacationUseCount;

		/** 휴가 상태 코드 */
		private String vacationStatusCode;

		/** 휴가 상태 코드명 */
		private String vacationStatusCodeName;

		/** 삭제 여부 */
		private String deleteYn;

		public String getVacationContent() {
			return vacationContent != null ? vacationContent : ""; // "null" 문자열 출력 방지
		}
	}

}
