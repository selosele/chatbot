package com.github.selosele.chatbot.bot.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.selosele.chatbot.bot.model.dto.HolidayDTO;
import com.github.selosele.chatbot.core.api.service.ApiService;
import com.github.selosele.chatbot.core.constant.DataType;
import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.model.dto.BotResultDTO;
import com.github.selosele.chatbot.core.util.BotUtil;
import com.github.selosele.chatbot.core.util.CommonUtil;
import com.github.selosele.chatbot.core.util.DateUtil;
import com.github.selosele.chatbot.core.util.XmlUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

	@Value("${api.holiday.endpoint}")
	private String endpoint;

	@Value("${api.holiday.serviceKey}")
	private String serviceKey;

	private final ApiService api;
	private final ObjectMapper objectMapper;

	/**
	 * 공휴일 정보를 조회하는 메소드
	 * 
	 * @param input 사용자 입력 ("공휴일" or "공휴일/yyyy" or "공휴일/yyyy/MM" 형식)
	 * @return 공휴일 정보
	 */
	public BotResultDTO<HolidayDTO.HolidayResult> getResponse(String input) {
		String[] parts = BotUtil.getParts(input);

		String validationMessage = validateInput(parts, input);
		if (validationMessage != null) {
			log.error(validationMessage);
			return BotResultDTO.<HolidayDTO.HolidayResult>of(null, input, validationMessage);
		}

		String response = api.request(endpoint, createParams(parts), HttpMethod.GET.name(), DataType.XML.getName());

		try {
			var rootNode = objectMapper.readTree(response);
			var resultCode = rootNode.path("header").path("resultCode");
			if (!resultCode.asText().equals("00")) {
				log.error("API 호출 실패: {}", resultCode.asText());
				return BotResultDTO.<HolidayDTO.HolidayResult>of(null, input, Message.BOT_RESPONSE_ERROR.getMessage());
			}

			var body = rootNode.path("body");
			var totalCount = body.path("totalCount");
			var totalCountValue = totalCount.asText("0");
			var items = body.path("items").path("item");

			return BotResultDTO.<HolidayDTO.HolidayResult>of(
					XmlUtil.parseItems(items, HolidayDTO.HolidayResult::of),
					input,
					totalCountValue.equals("0") ? Message.FOUND_NO_HOLIDAY_DATA.getMessage()
							: totalCountValue + Message.FOUND_DATA_COUNT.getMessage());
		} catch (Exception ex) {
			log.error("API 응답 처리 중 오류 발생: {}", ex.getMessage());
			return BotResultDTO.<HolidayDTO.HolidayResult>of(null, input, Message.BOT_RESPONSE_ERROR.getMessage());
		}
	}

	/**
	 * 공휴일 정보를 문자열로 변환하는 메소드
	 * 
	 * @param response 공휴일 정보 응답
	 * @return 공휴일 정보를 문자열로 변환한 결과
	 */
	public String responseToString(BotResultDTO<HolidayDTO.HolidayResult> response) {
		StringBuilder text = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		if (CommonUtil.isNotEmpty(response.getData())) {
			for (var holiday : response.getData()) {
				LocalDate date = LocalDate.parse(holiday.getLocDate(), formatter); // 날짜 문자열을 LocalDate로 변환
				String dayOfWeekKor = DateUtil.dayOfWeekToKor(date.getDayOfWeek()); // 요일을 한글로 변환

				// 출력 예시: 2025년 06월 06일(금): 현충일 | 공공기관 휴무일
				text.append(String.format("%s(%s): %s | %s\n",
						DateUtil.getDateString("yyyyMMdd", "yyyy년 MM월 dd일", holiday.getLocDate()),
						dayOfWeekKor,
						holiday.getDateName(),
						holiday.getIsHoliday().equals("Y") ? "공공기관 휴무일" : " "));
			}
		} else {
			text.append(response.getMessage());
		}
		return text.toString();
	}

	/**
	 * 입력된 공휴일 정보의 형식을 검증하는 메소드
	 * 
	 * @param parts 입력된 문자열을 "/"로 분리한 배열
	 * @param input 원본 입력 문자열
	 * @return 검증 메시지 또는 null
	 */
	private String validateInput(String[] parts, String input) {

		// 1. "공휴일/yyyy" 형식
		if (parts.length == 2 && !DateUtil.isValidDate(parts[1], "yyyy")) {
			return "날짜 형식이 올바르지 않습니다. '공휴일/연도(4자리)' 형식으로 입력해주세요.";
		}

		// 2. "공휴일/yyyy/MM" 형식
		if (parts.length == 3 && (!DateUtil.isValidDate(parts[1], "yyyy") || !DateUtil.isValidDate(parts[2], "MM"))) {
			return "날짜 형식이 올바르지 않습니다. '공휴일/연도(4자리)/월(2자리)' 형식으로 입력해주세요.";
		}

		return null;
	}

	/**
	 * API 요청에 필요한 파라미터를 생성하는 메소드
	 * 
	 * @param parts 입력된 문자열을 "/"로 분리한 배열
	 * @return HolidayDTO.GetHolidayRequest 객체
	 */
	private HolidayDTO.GetHolidayRequest createParams(String[] parts) {
		var year = parts.length == 1 ? DateUtil.getCurrentYear() : parts[1];
		var month = parts.length == 3 ? parts[2] : "";
		return HolidayDTO.GetHolidayRequest.of(serviceKey, year, month, "365");
	}

}
