package com.github.selosele.chatbot.app.bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.selosele.chatbot.app.bot.model.dto.BotResponseDTO;
import com.github.selosele.chatbot.app.bot.service.BotService;
import com.github.selosele.chatbot.app.core.util.GlobalUtil;

import jakarta.servlet.http.HttpServletRequest;
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
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<BotResponseDTO.Response> getBotResponse(
		HttpServletRequest request,
		@RequestParam(value = "date", required = false) String input) {

		var clientIP = GlobalUtil.getClientIP(request);
		var respone = botService.getResponse(input);

		log.info("Client IP: {}, Input: {}, Bot Response: {}", clientIP, input, respone.toString());
		return ResponseEntity.ok(respone);
	}

}
