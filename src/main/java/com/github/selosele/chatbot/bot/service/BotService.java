package com.github.selosele.chatbot.bot.service;

import org.springframework.stereotype.Service;

import com.github.selosele.chatbot.bot.model.dto.BotRequestDTO;
import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.model.dto.SkillResponseDTO;
import com.github.selosele.chatbot.core.util.CommonUtil;

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
	public SkillResponseDTO getResponse(BotRequestDTO botRequestDTO) {
		String input = botRequestDTO.getUserRequest().getUtterance();

		if (!isValidInput(input)) {
			log.error(Message.IS_INPUT_BLANK.getMessage());
			return SkillResponseDTO.of(Message.IS_INPUT_BLANK.getMessage());
		}

		String category = input.split("/")[0];
		if (category.equals("공휴일")) {
			var response = holidayService.getResponse(input);
			return SkillResponseDTO.of(holidayService.responseToString(response));
		}
		else if (category.equals("버스"))   return SkillResponseDTO.of("버스 API는 준비 중입니다.");
		else if (category.equals("지하철")) return SkillResponseDTO.of("지하철 API는 준비 중입니다.");
		
		return SkillResponseDTO.of(Message.UNSUPPORTED_COMMAND.getMessage());
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
