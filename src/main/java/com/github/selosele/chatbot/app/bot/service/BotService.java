package com.github.selosele.chatbot.app.bot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
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
	private String endPoint;

	@Value("${api.serviceKey}")
	private String serviceKey;

	private final ApiService apiService;
	private final ObjectMapper objectMapper;

	/**
	 * 봇의 응답을 처리하는 메소드
	 * @param input 사용자 입력
	 * @return 봇의 응답
	 */
	public BotResponseDTO getResponse(String[] input) {
		Map<String, Object> params = Map.of(
			"serviceKey", serviceKey,
			"solYear", input[0],
			"solMonth", input[1]);
		String response = apiService.request(endPoint, params, "GET", "xml");

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
				Map<String, String> map = Map.of(
					"dateName", item.get("dateName").asText(),
					"isHoliday", item.get("isHoliday").asText(),
					"locdate", item.get("locdate").asText());
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
