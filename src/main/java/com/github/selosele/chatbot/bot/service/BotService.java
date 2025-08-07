package com.github.selosele.chatbot.bot.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.selosele.chatbot.bot.model.dto.BotRequestDTO;
import com.github.selosele.chatbot.bot.model.dto.BotResponseDTO;
import com.github.selosele.chatbot.bot.model.dto.BotResultDataDTO;
import com.github.selosele.chatbot.bot.model.dto.SkillResponseDTO;
import com.github.selosele.chatbot.core.api.service.ApiService;
import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.util.CommonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotService {

	@Value("${api.endpoint}")
	private String endpoint;

	@Value("${api.serviceKey}")
	private String serviceKey;

	private final ApiService api;
	private final ObjectMapper objectMapper;

	/**
	 * 봇의 응답을 처리하는 메소드
	 * @param input 사용자 입력
	 * @return 봇의 응답
	 */
	public SkillResponseDTO getResponse(BotRequestDTO botRequestDTO) {
		String input = botRequestDTO.getUserRequest().getUtterance();

		if (!isValidInput(input)) {
			log.error(Message.IS_INPUT_BLANK.getMessage());
			// return BotResponseDTO.builder()
			// 	.message(Message.IS_INPUT_BLANK.getMessage())
			// 	.input(input)
			// 	.build();
			return null;
		}

		String category = input.split("/")[0];
		if (category.equals("공휴일")) {
			StringBuilder text = new StringBuilder();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			var response = getHolidayResponse(input);

			if (response.getData() != null && !response.getData().isEmpty()) {
				for (var holiday : response.getData()) {
					if (holiday.getIsHoliday().equals("Y")) {
						// 날짜 문자열을 LocalDate로 변환
						LocalDate date = LocalDate.parse(holiday.getLocdate(), formatter);
						// 요일을 한글 요일로 변환 (예: 월, 화, 수, 목, 금, 토, 일)
						String dayOfWeekKor = switch (date.getDayOfWeek()) {
							case MONDAY -> "월";
							case TUESDAY -> "화";
							case WEDNESDAY -> "수";
							case THURSDAY -> "목";
							case FRIDAY -> "금";
							case SATURDAY -> "토";
							case SUNDAY -> "일";
						};
						
						// 출력 예시: 20250606(금): 현충일
						text.append(String.format("%s(%s): %s\n", holiday.getLocdate(), dayOfWeekKor, holiday.getDateName()));
					}
				}
			} else {
				text.append(Message.FOUND_NO_HOLIDAY_DATA.getMessage());
			}
			
			return SkillResponseDTO.builder()
				.template(SkillResponseDTO.Template.builder()
					.outputs(List.of(
						SkillResponseDTO.Output.builder()
							.simpleText(SkillResponseDTO.SimpleText.builder()
								.text(text.toString())
								.build())
							.build()
					))
					.build())
				.build();
		}
		else if (category.equals("버스")) {
			// return BotResponseDTO.builder()
			// 	.message("버스 API는 준비 중입니다.")
			// 	.input(input)
			// 	.build();
			return null;
		}
		else if (category.equals("지하철")) {
			// return BotResponseDTO.builder()
			// 	.message("지하철 API는 준비 중입니다.")
			// 	.input(input)
			// 	.build();
			return null;
		}
		
		// return BotResponseDTO.builder()
		// 	.message(Message.UNSUPPORTED_COMMAND.getMessage())
		// 	.input(input)
		// 	.build();
		return null;
	}

	/**
	 * 공휴일 정보를 조회하는 메소드
	 * @param input 사용자 입력 (공휴일/yyyy/MM 형식)
	 * @return 공휴일 정보
	 */
	private BotResponseDTO<BotResultDataDTO.Holiday> getHolidayResponse(String input) {

		// 입력 값 예시) 공휴일/2025/06
		String[] parts = {
			input,
			new SimpleDateFormat("yyyy").format(new Date()),
			new SimpleDateFormat("MM").format(new Date())
		};

		if (input.split("/").length >= 3) {
			parts = input.split("/");
		}
		
		// 날짜 형식이 올바른지 확인
		if (parts.length < 3 || !parts[1].matches("\\d{4}") || !parts[2].matches("\\d{2}")) {
			String message = "날짜 형식이 올바르지 않습니다. yyyy/MM 형식으로 입력해주세요.";
			log.error(message);
    	return BotResponseDTO.<BotResultDataDTO.Holiday>builder()
				.message(message)
				.input(input)
				.build();
    }

		Map<String, Object> params = new HashMap<>();
		params.put("serviceKey", serviceKey);
		params.put("solYear", parts[1]);
		params.put("solMonth", parts[2]);

		String response = api.request(endpoint, params, HttpMethod.GET.name(), "xml");

		try {
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode resultCode = rootNode.path("header").path("resultCode");
			if (!resultCode.asText().equals("00")) {
				log.error("API 호출 실패: {}", resultCode.asText());
				return BotResponseDTO.<BotResultDataDTO.Holiday>builder()
					.message(Message.BOT_RESPONSE_ERROR.getMessage())
					.input(input)
					.build();
			}

			JsonNode body = rootNode.path("body");
			JsonNode totalCount = body.path("totalCount");
			JsonNode items = body.path("items").path("item");

			return BotResponseDTO.<BotResultDataDTO.Holiday>builder()
				.data(parseHolidayItems(items))
				.message(totalCount.asText("0") + Message.FOUND_DATA_COUNT.getMessage())
				.input(input)
				.build();
		}
		catch (Exception ex) {
			log.error("API 응답 처리 중 오류 발생: {}", ex.getMessage());
			return BotResponseDTO.<BotResultDataDTO.Holiday>builder()
				.message(Message.BOT_RESPONSE_ERROR.getMessage())
				.input(input)
				.build();
		}
	}

	/**
	 * 공휴일 정보를 파싱하는 메소드
	 * @param items JSON 노드
	 * @return 공휴일 정보 리스트
	 */
	private List<BotResultDataDTO.Holiday> parseHolidayItems(JsonNode items) {
		List<BotResultDataDTO.Holiday> list = new ArrayList<>();

		// items가 배열인지 객체인지 확인
		if (items.isArray()) {
			for (JsonNode item : items) {
				if (item.isMissingNode() || item.isEmpty()) continue;

				list.add(BotResultDataDTO.Holiday.builder()
					.dateName(item.get("dateName").asText(""))
					.isHoliday(item.get("isHoliday").asText(""))
					.locdate(item.get("locdate").asText(""))
					.build());
			}
		}
		else if (items.isObject()) {
			JsonNode item = items;
			if (!item.isMissingNode() && !item.isEmpty()) {
				list.add(BotResultDataDTO.Holiday.builder()
					.dateName(item.path("dateName").asText(""))
					.isHoliday(item.path("isHoliday").asText(""))
					.locdate(item.path("locdate").asText(""))
					.build());
			}
		}
		return list;
	}

	/**
	 * 입력 값이 유효한지 검사하는 메소드
	 * @param input 사용자 입력
	 * @return 유효성 검사 결과
	 */
	private boolean isValidInput(String input) {
		return CommonUtil.isNotBlank(input);
	}

}
