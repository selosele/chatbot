package com.github.selosele.chatbot.bot.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.selosele.chatbot.bot.model.dto.HolidayDTO;
import com.github.selosele.chatbot.core.api.service.ApiService;
import com.github.selosele.chatbot.core.constant.DataType;
import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.model.dto.BotResultDTO;
import com.github.selosele.chatbot.core.util.CommonUtil;
import com.github.selosele.chatbot.core.util.DateUtil;

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
	 * @param input 사용자 입력 (공휴일/yyyy or 공휴일/yyyy/MM 형식)
	 * @return 공휴일 정보
	 */
	public BotResultDTO<HolidayDTO> getResponse(String input) {
		String[] parts = input.split("/");
		
		// 날짜 형식이 올바른지 확인
		// 예시) 공휴일/yyyy 형식
		if (parts.length == 2 && !DateUtil.isValidDate(parts[1], "yyyy")) {
			String message = "날짜 형식이 올바르지 않습니다. '공휴일/yyyy' 형식으로 입력해주세요.";
			log.error(message);
			return BotResultDTO.<HolidayDTO>of(null, input, message);
		}

		// 예시) 공휴일/yyyy/MM 형식
		if (parts.length == 3 && (!DateUtil.isValidDate(parts[1], "yyyy") || !DateUtil.isValidDate(parts[2], "MM"))) {
			String message = "날짜 형식이 올바르지 않습니다. '공휴일/yyyy' 또는 '공휴일/yyyy/MM' 형식으로 입력해주세요.";
			log.error(message);
			return BotResultDTO.<HolidayDTO>of(null, input, message);
		}

		Map<String, Object> params = new HashMap<>();
		params.put("serviceKey", serviceKey);
		params.put("solYear", parts[1]);
		params.put("solMonth", parts.length == 3 ? parts[2] : "");
		params.put("numOfRows", "100");

		String response = api.request(endpoint, params, HttpMethod.GET.name(), DataType.XML.getName());

		try {
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode resultCode = rootNode.path("header").path("resultCode");
			if (!resultCode.asText().equals("00")) {
				log.error("API 호출 실패: {}", resultCode.asText());
				return BotResultDTO.<HolidayDTO>of(null, input, Message.BOT_RESPONSE_ERROR.getMessage());
			}

			JsonNode body = rootNode.path("body");
			JsonNode totalCount = body.path("totalCount");
			String totalCountValue = totalCount.asText("0");
			JsonNode items = body.path("items").path("item");

			return BotResultDTO.<HolidayDTO>of(
				parseHolidayItems(items),
				input,
				totalCountValue.equals("0") ? Message.FOUND_NO_HOLIDAY_DATA.getMessage() : totalCountValue + Message.FOUND_DATA_COUNT.getMessage()
			);
		}
		catch (Exception ex) {
			log.error("API 응답 처리 중 오류 발생: {}", ex.getMessage());
			return BotResultDTO.<HolidayDTO>of(null, input, Message.BOT_RESPONSE_ERROR.getMessage());
		}
	}

  /**
   * 공휴일 정보를 문자열로 변환하는 메소드
   * @param response 공휴일 정보 응답
   * @return 공휴일 정보를 문자열로 변환한 결과
   */
  public String responseToString(BotResultDTO<HolidayDTO> response) {
    StringBuilder text = new StringBuilder();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    if (CommonUtil.isNotEmpty(response.getData())) {
      for (var holiday : response.getData()) {
        if (holiday.getIsHoliday().equals("Y")) {
          LocalDate date = LocalDate.parse(holiday.getLocDate(), formatter);     // 날짜 문자열을 LocalDate로 변환
          String dayOfWeekKor = DateUtil.dayOfWeekToKor(date.getDayOfWeek());    // 요일을 한글로 변환
          
          // 출력 예시: 2025년 06월 06일(금): 현충일
          text.append(String.format("%s(%s): %s\n",
						DateUtil.getDateString("yyyyMMdd", "yyyy년 MM월 dd일", holiday.getLocDate()),
						dayOfWeekKor,
						holiday.getDateName())
					);
        }
      }
    } else {
      text.append(response.getMessage());
    }
    return text.toString();
  }

	/**
	 * 공휴일 정보를 파싱하는 메소드
	 * @param items JSON 노드
	 * @return 공휴일 정보 리스트
	 */
	private List<HolidayDTO> parseHolidayItems(JsonNode items) {
		List<HolidayDTO> list = new ArrayList<>();

		// items가 배열인지 객체인지 확인
		if (items.isArray()) {
			for (JsonNode item : items) {
				if (item.isMissingNode() || item.isEmpty()) continue;
				list.add(HolidayDTO.of(item));
			}
		}
		else if (items.isObject()) {
			JsonNode item = items;
			if (!item.isMissingNode() && !item.isEmpty()) {
				list.add(HolidayDTO.of(item));
			}
		}
		return list;
	}
  
}
