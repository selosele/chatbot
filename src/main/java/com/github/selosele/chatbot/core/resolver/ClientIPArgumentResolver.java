package com.github.selosele.chatbot.core.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.github.selosele.chatbot.core.annotation.ClientIP;
import com.github.selosele.chatbot.core.util.GlobalUtil;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 클라이언트의 IP 주소를 메소드 인자로 주입하기 위한 Argument Resolver
 */
public class ClientIPArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(ClientIP.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    return GlobalUtil.getClientIP(webRequest.getNativeRequest(HttpServletRequest.class));
  }
  
}
