package com.github.selosele.chatbot.app.bot.service;

import java.text.SimpleDateFormat;
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
import com.github.selosele.chatbot.app.bot.model.dto.BotResponseDTO;
import com.github.selosele.chatbot.app.core.api.service.ApiService;
import com.github.selosele.chatbot.app.core.constant.Message;

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
	public BotResponseDTO.Response getResponse(String input) {

		// 날짜 값이 없는 경우 현재 날짜로 설정
		if (input == null || input.isEmpty()) {
			input = new SimpleDateFormat("yyyy-MM").format(new Date());
		}
		
		String[] dateParts = input.split("-");
		if (dateParts.length != 2) {
			String message = "날짜 형식이 올바르지 않습니다. yyyy-MM 형식으로 입력해주세요.";
			log.error(message);
    	throw new IllegalArgumentException(message);
    }

		Map<String, Object> params = new HashMap<>();
		params.put("serviceKey", serviceKey);
		params.put("solYear", dateParts[0]);
		params.put("solMonth", dateParts[1]);

		String response = api.request(endpoint, params, HttpMethod.GET.name(), "xml");

		try {
			JsonNode rootNode = objectMapper.readTree(response);
			JsonNode resultCode = rootNode.path("header").path("resultCode");
			if (!resultCode.asText().equals("00")) {
				log.error("API 호출 실패: {}", resultCode.asText());
				return BotResponseDTO.Response.builder()
					.message(Message.BOT_RESPONSE_ERROR.getMessage())
					.build();
			}

			List<BotResponseDTO.Data> list = new ArrayList<>();
			JsonNode body = rootNode.path("body");
			JsonNode totalCount = body.path("totalCount");
			JsonNode items = body.path("items");
			for (JsonNode item : items.path("item")) {
				if (item.isMissingNode() || item.isEmpty()) continue;

				list.add(BotResponseDTO.Data.builder()
					.dateName(item.get("dateName").asText())
					.isHoliday(item.get("isHoliday").asText())
					.locdate(item.get("locdate").asText())
					.build());
			}

			return BotResponseDTO.Response.builder()
				.data(list)
				.message(totalCount.asText() + Message.FOUND_DATA_COUNT.getMessage())
				.build();
		}
		catch (Exception ex) {
			log.error("API 응답 처리 중 오류 발생: {}", ex.getMessage());
			return BotResponseDTO.Response.builder()
				.message(Message.BOT_RESPONSE_ERROR.getMessage())
				.build();
		}
	}

}
