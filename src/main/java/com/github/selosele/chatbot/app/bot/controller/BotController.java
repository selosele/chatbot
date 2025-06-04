package com.github.selosele.chatbot.app.bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.selosele.chatbot.app.bot.model.dto.BotRequestDTO;
import com.github.selosele.chatbot.app.bot.model.dto.BotResponseDTO;
import com.github.selosele.chatbot.app.bot.service.BotService;
import com.github.selosele.chatbot.app.core.annotation.ClientIP;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BotController {

	private final BotService botService;

	/**
	 * 봇의 응답을 처리하는 엔드포인트
	 * @return 봇의 응답
	 */
	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<BotResponseDTO.Response<?>> getBotResponse(
		@ClientIP String ip,
		@RequestBody(required = false) BotRequestDTO botRequestDTO) {

		var response = botService.getResponse(botRequestDTO);

		log.info("Client IP: {}, Bot Response: {}", ip, response.toString());
		return ResponseEntity.ok(response);
	}

}
