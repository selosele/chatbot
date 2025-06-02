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
	 * @param date 사용자 입력
	 * @return 봇의 응답
	 */
	public BotResponseDTO getResponse(String date) {

		// date 값이 없는 경우 현재 날짜로 설정
		if (date == null || date.isEmpty()) {
			date = new SimpleDateFormat("yyyy-MM").format(new Date());
		}
		
		String[] dateParts = date.split("-");
		if (dateParts.length != 2) {
    	throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. yyyy-MM 형식으로 입력해주세요.");
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
				return BotResponseDTO.builder()
					.message(Message.BOT_RESPONSE_ERROR.getMessage())
					.build();
			}

			List<Map<String, String>> list = new ArrayList<>();
			JsonNode items = rootNode.path("body").path("items");
			for (JsonNode item : items.path("item")) {
				if (item.isMissingNode()) continue;
				if (!item.has("dateName") || !item.has("isHoliday") || !item.has("locdate")) continue;
				
				Map<String, String> map = new HashMap<>();
				map.put("dateName", item.get("dateName").asText());
				map.put("isHoliday", item.get("isHoliday").asText());
				map.put("locdate", item.get("locdate").asText());
				list.add(map);
			}

			return BotResponseDTO.builder()
				.data(list)
				.build();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return BotResponseDTO.builder()
				.message(Message.BOT_RESPONSE_ERROR.getMessage())
				.build();
		}
	}

}
