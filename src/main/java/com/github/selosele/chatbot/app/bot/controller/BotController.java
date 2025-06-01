package com.github.selosele.chatbot.app.bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.selosele.chatbot.app.bot.model.dto.BotResponseDTO;
import com.github.selosele.chatbot.app.bot.service.BotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BotController {

	private final BotService botService;

	/**
	 * 봇의 응답을 처리하는 엔드포인트
	 * @return 봇의 응답
	 */
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<BotResponseDTO> getBotResponse() {
		BotResponseDTO respone = botService.getResponse(new String[] { "2025", "06" });
		return ResponseEntity.ok(respone);
	}

}
