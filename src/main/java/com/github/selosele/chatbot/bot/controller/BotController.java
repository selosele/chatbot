package com.github.selosele.chatbot.bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.selosele.chatbot.bot.service.BotService;
import com.github.selosele.chatbot.core.annotation.LogBotResponse;
import com.github.selosele.chatbot.core.model.dto.BotRequestDTO;
import com.github.selosele.chatbot.core.model.dto.KakaoBotRequestDTO;
import com.github.selosele.chatbot.core.model.dto.KakaoSkillResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BotController {

	private final BotService botService;

	/**
	 * 카카오톡 봇의 응답을 처리하는 엔드포인트
	 * 
	 * @return 카카오톡 봇의 응답
	 */
	@PostMapping("/")
	@LogBotResponse
	public ResponseEntity<KakaoSkillResponseDTO> handleKakaoBot(@RequestBody KakaoBotRequestDTO dto) {
		return ResponseEntity.ok(botService.getResponse(BotRequestDTO.of(dto)).getKakaoSkillResponse());
	}

}
