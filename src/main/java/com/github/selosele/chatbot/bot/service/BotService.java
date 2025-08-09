package com.github.selosele.chatbot.bot.service;

import org.springframework.stereotype.Service;

import com.github.selosele.chatbot.bot.model.dto.HolidayDTO;
import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.model.dto.BotResponseDTO;
import com.github.selosele.chatbot.core.model.dto.KakaoBotRequestDTO;
import com.github.selosele.chatbot.core.model.dto.KakaoSkillResponseDTO;
import com.github.selosele.chatbot.core.util.BotUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotService {

	private final HolidayService holidayService;

	/**
	 * 봇의 응답을 처리하는 메소드
	 * @param input 사용자 입력
	 * @return 봇의 응답
	 */
	public KakaoSkillResponseDTO getResponse(KakaoBotRequestDTO botRequestDTO) {
		String input = botRequestDTO.getUserRequest().getUtterance();

		if (!BotUtil.isValidInput(input)) {
			log.error(Message.IS_INPUT_BLANK.getMessage());
			return KakaoSkillResponseDTO.of(Message.IS_INPUT_BLANK.getMessage());
		}

		String category = BotUtil.extractCategory(input);
		if (category.equals("공휴일")) {
			BotResponseDTO<HolidayDTO> response = holidayService.getResponse(input);
			return KakaoSkillResponseDTO.of(holidayService.responseToString(response));
		}
		else if (category.equals("버스"))   return KakaoSkillResponseDTO.of("버스 API는 준비 중입니다.");
		else if (category.equals("지하철")) return KakaoSkillResponseDTO.of("지하철 API는 준비 중입니다.");
		
		return KakaoSkillResponseDTO.of(Message.UNSUPPORTED_COMMAND.getMessage());
	}

}
