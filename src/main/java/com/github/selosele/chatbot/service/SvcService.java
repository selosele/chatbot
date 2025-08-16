package com.github.selosele.chatbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.github.selosele.chatbot.core.constant.DataType;
import com.github.selosele.chatbot.core.http.service.HttpService;
import com.github.selosele.chatbot.model.dto.LoginDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SvcService {

    @Value("${api.svc.login.endpoint}")
    private String endpoint;

    private final HttpService http;

    /**
     * 로그인 메소드
     * 
     * @param userAccount  사용자 계정
     * @param userPassword 비밀번호
     * @return 액세스 토큰
     */
    public String login(LoginDTO.Request dto) {
        String response = http.request(endpoint, dto, HttpMethod.POST.name(), DataType.JSON.getName());
        String accessToken = LoginDTO.Response.of(response).getAccessToken();
        return accessToken;
    }

}
