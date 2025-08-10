package com.github.selosele.chatbot.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.model.dto.BotRequestDTO;
import com.github.selosele.chatbot.core.model.dto.BotResponseDTO;
import com.github.selosele.chatbot.core.util.BotUtil;
import com.github.selosele.chatbot.core.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 봇 요청을 검사하는 AOP
 */
@Slf4j
@Aspect
@Component
public class ValidateBotRequestAspect {

	/**
	 * 봇 요청을 검사하는 메소드
	 * 
	 * @param joinPoint AOP JoinPoint
	 * @param response  봇의 요청
	 */
	@Around("@annotation(com.github.selosele.chatbot.core.annotation.ValidateBotRequest)")
	public Object validateBotRequest(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		if (args.length == 0 || !(args[0] instanceof BotRequestDTO)) {
			return BotResponseDTO.of(Message.IS_INPUT_BLANK.getMessage());
		}

		BotRequestDTO dto = (BotRequestDTO) args[0];
		String input = dto.getInput();
		if (!BotUtil.isValidInput(input)) {
			log.error(Message.IS_INPUT_BLANK.getMessage());
			return BotResponseDTO.of(Message.IS_INPUT_BLANK.getMessage());
		}

		String category = BotUtil.extractCategory(input);
		if (CommonUtil.isBlank(category)) {
			log.error(Message.UNSUPPORTED_COMMAND.getMessage());
			return BotResponseDTO.of(Message.UNSUPPORTED_COMMAND.getMessage());
		}

		dto.setCategory(category);

		return joinPoint.proceed(args);
	}

}
