package com.github.selosele.chatbot.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 봇 요청을 검증하는 어노테이션
 * 이 어노테이션이 적용된 메소드는 봇 요청의 유효성을 검사한다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateBotRequest {

}
