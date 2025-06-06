package com.github.selosele.chatbot.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 클라이언트의 IP 주소를 메소드 인자로 주입하기 위한 어노테이션
 * 이 어노테이션이 붙은 파라미터는 클라이언트의 IP 주소로 자동으로 채워진다.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientIP {

}
