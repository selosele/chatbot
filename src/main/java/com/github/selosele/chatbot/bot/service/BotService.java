package com.github.selosele.chatbot.bot.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.github.selosele.chatbot.bot.model.dto.BotRequestDTO;
import com.github.selosele.chatbot.bot.model.dto.SkillResponseDTO;
import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.util.CommonUtil;
import com.github.selosele.chatbot.core.util.DateUtil;

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
			StringBuilder text = new StringBuilder();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			var response = holidayService.getResponse(input);

			if (CommonUtil.isNotEmpty(response.getData())) {
				for (var holiday : response.getData()) {
					if (holiday.getIsHoliday().equals("Y")) {
						LocalDate date = LocalDate.parse(holiday.getLocdate(), formatter);     // 날짜 문자열을 LocalDate로 변환
						String dayOfWeekKor = DateUtil.getDayOfWeekToKor(date.getDayOfWeek()); // 요일을 한글로 변환
						
						// 출력 예시: 20250606(금): 현충일
						text.append(String.format("%s(%s): %s\n", holiday.getLocdate(), dayOfWeekKor, holiday.getDateName()));
					}
				}
			} else {
				text.append(response.getMessage());
			}
			
			return SkillResponseDTO.of(text.toString());
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
