package com.github.selosele.chatbot.service;

import org.springframework.stereotype.Service;

import com.github.selosele.chatbot.model.dto.HolidayDTO;
import com.github.selosele.chatbot.model.dto.VacationDTO;
import com.github.selosele.chatbot.core.annotation.ValidateBotRequest;
import com.github.selosele.chatbot.core.constant.Category;
import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.model.dto.BotRequestDTO;
import com.github.selosele.chatbot.core.model.dto.BotResponseDTO;
import com.github.selosele.chatbot.core.model.dto.BotResultDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotService {

	private final HolidayService holidayService;
	private final VacationService vacationService;

	/**
	 * 봇의 응답을 처리하는 메소드
	 * 
	 * @param input 사용자 입력
	 * @return 봇의 응답
	 */
	@ValidateBotRequest
	public BotResponseDTO getResponse(BotRequestDTO dto) {
		String category = dto.getCategory();
		// 공휴일
		if (category.equals(Category.HOLIDAY.getName())) {
			BotResultDTO<HolidayDTO.Response> response = holidayService.getResponse(dto.getInput());
			return BotResponseDTO.of(holidayService.responseToString(response));
		}
		// 휴가
		else if (category.equals(Category.VACATION.getName())) {
			BotResultDTO<VacationDTO.Response> response = vacationService.getResponse(dto.getInput());
			return BotResponseDTO.of(vacationService.responseToString(response));
		}

		return BotResponseDTO.of(Message.UNSUPPORTED_COMMAND.getMessage());
	}

}
