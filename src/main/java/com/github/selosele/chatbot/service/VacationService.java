package com.github.selosele.chatbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.selosele.chatbot.core.constant.DataType;
import com.github.selosele.chatbot.core.http.service.HttpService;
import com.github.selosele.chatbot.core.model.dto.BotResultDTO;
import com.github.selosele.chatbot.core.util.CommonUtil;
import com.github.selosele.chatbot.core.util.DateUtil;
import com.github.selosele.chatbot.model.dto.LoginDTO;
import com.github.selosele.chatbot.model.dto.VacationDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationService {

    @Value("${api.svc.accessToken}")
    private String accessToken;

    @Value("${api.svc.vacation.endpoint}")
    private String endpoint;

    @Value("${api.svc.user.id}")
    private String userId;

    @Value("${api.svc.login.user.account}")
    private String userAccount;

    @Value("${api.svc.login.user.password}")
    private String userPassword;

    private final HttpService http;
    private final SvcService svcService;
    private final ObjectMapper objectMapper;

    /**
     * 휴가 정보를 조회하는 메소드
     *
     * @param input 사용자 입력 ("휴가" 형식)
     * @return 휴가 정보
     */
    public BotResultDTO<VacationDTO.Response> getResponse(String input) {

        // 액세스 토큰이 없거나 유효하지 않으면 로그인 진행
        if (CommonUtil.isBlank(accessToken)) {
            accessToken = svcService.login(LoginDTO.Request.of(userAccount, userPassword));
        }

        String response = http.request(endpoint, createParams(accessToken, userId), HttpMethod.GET.name(),
                DataType.JSON.getName());

        try {
            VacationDTO.Response vacationResponse = objectMapper.readValue(response, VacationDTO.Response.class);
            return BotResultDTO.<VacationDTO.Response>of(List.of(vacationResponse), input, "휴가 정보 조회 성공");
        } catch (Exception ex) {
            log.error("JSON 파싱 실패: {}", ex.getMessage());
            return BotResultDTO.<VacationDTO.Response>of(null, input, "휴가 정보 조회 실패");
        }
    }

    /**
     * 휴가 정보를 문자열로 변환하는 메소드
     * 
     * @param response 휴가 정보 응답
     * @return 휴가 정보를 문자열로 변환한 결과
     */
    public String responseToString(BotResultDTO<VacationDTO.Response> response) {
        StringBuilder text = new StringBuilder();

        if (CommonUtil.isNotEmpty(response.getData())) {
            for (var data : response.getData()) {
                var countInfo = data.getCountInfo();
                text.append("=====================================휴가 일수:=====================================\n")
                        .append(String.format("Total: %s", countInfo.getVacationTotalCount() + "개")).append(", ")
                        .append(String.format("사용: %s", countInfo.getVacationUseCount() + "개")).append(", ")
                        .append(String.format("잔여: %s", countInfo.getVacationRemainCount() + "개"))
                        .append("\n");
                text.append("=====================================휴가 목록:=====================================\n");
                if (!data.getVacationList().isEmpty()) {
                    for (var vacation : data.getVacationList()) {
                        var startYmd = DateUtil.getDateString("yyyyMMdd", "yyyy년 MM월 dd일",
                                vacation.getVacationStartYmd());
                        var endYmd = DateUtil.getDateString("yyyyMMdd", "yyyy년 MM월 dd일", vacation.getVacationEndYmd());
                        var useCount = vacation.getVacationUseCount();
                        var type = vacation.getVacationTypeCodeName();
                        var status = vacation.getVacationStatusCodeName();
                        var content = vacation.getVacationContent();
                        text.append(
                                String.format("%s ~ %s (%s일) | %s | %s | %s", startYmd, endYmd, useCount, type,
                                        status,
                                        content))
                                .append("\n");
                    }
                } else {
                    text.append("사용한 휴가 기록이 없어요.");
                }
            }
        } else {
            text.append(response.getMessage());
        }
        return text.toString();
    }

    /**
     * API 요청에 필요한 파라미터를 생성하는 메소드
     * 
     * @param parts 입력된 문자열을 구분자로 분리한 배열
     * @return VacationDTO.Request 객체
     */
    private VacationDTO.Request createParams(String accessToken, String userId) {
        return VacationDTO.Request.of(accessToken, userId);
    }

}
