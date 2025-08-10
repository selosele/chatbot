package com.github.selosele.chatbot.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.github.selosele.chatbot.core.constant.Message;
import com.github.selosele.chatbot.core.model.dto.BotRequestDTO;
import com.github.selosele.chatbot.core.model.dto.BotResponseDTO;
import com.github.selosele.chatbot.core.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 봇 요청을 처리하는 AOP
 * 카카오톡, 슬랙, 라인, 텔레그램 등 모든 봇의 요청을 처리
 */
@Slf4j
@Aspect
@Component
public class HandleBotRequestAspect {

    /**
     * 봇 요청을 생성하는 메소드
     * 
     * @param joinPoint AOP JoinPoint
     * @param response  봇의 요청
     */
    // @Around("@annotation(com.github.selosele.chatbot.core.annotation.BotHandler)")
    // public Object createBotRequest(ProceedingJoinPoint joinPoint) throws
    // Throwable {
    // Object[] args = joinPoint.getArgs();
    // if (args.length == 0 || !(args[0] instanceof BotRequestDTO)) {
    // return BotResponseDTO.of(Message.IS_INPUT_BLANK.getMessage());
    // }

    // BotRequestDTO newDto = null;
    // BotRequestDTO dto = (BotRequestDTO) args[0];
    // Class<?> targetClass = dto.getClass();
    // for (Field field : targetClass.getDeclaredFields()) {
    // if (field.isAnnotationPresent(BotRequest.class)) {
    // field.setAccessible(true); // private 필드 접근 허용
    // Object fieldValue = field.get(dto);

    // if (fieldValue instanceof KakaoBotRequestDTO) { // 카카오톡 봇 요청 DTO인 경우
    // newDto = BotRequestDTO.of(dto);
    // }
    // }
    // }

    // args[0] = newDto;

    // return joinPoint.proceed(args);
    // }

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
        if (!dto.isValidInput(input)) {
            log.error(Message.IS_INPUT_BLANK.getMessage());
            return BotResponseDTO.of(Message.IS_INPUT_BLANK.getMessage());
        }

        String category = dto.extractCategory(input);
        if (CommonUtil.isBlank(category)) {
            log.error(Message.UNSUPPORTED_COMMAND.getMessage());
            return BotResponseDTO.of(Message.UNSUPPORTED_COMMAND.getMessage());
        }

        dto.setCategory(category);

        return joinPoint.proceed(args);
    }

}
