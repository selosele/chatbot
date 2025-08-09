package com.github.selosele.chatbot.bot.model.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.selosele.chatbot.core.model.dto.ApiRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 공휴일 DTO
 */
public class HolidayDTO {
  
  /**
   * 공휴일 조회 요청 DTO
   */
  @Getter
  @Setter
  @Builder
  @ToString
  @NoArgsConstructor
  @AllArgsConstructor
  public static class GetHolidayRequestDTO extends ApiRequestDTO {

    private String solYear;
    private String solMonth;

    public static GetHolidayRequestDTO of(String serviceKey, String solYear, String solMonth, String numOfRows) {
      var dto = new GetHolidayRequestDTO();
      dto.setServiceKey(serviceKey);
      dto.setSolYear(solYear);
      dto.setSolMonth(solMonth);
      dto.setNumOfRows(numOfRows);
      return dto;
    }
  }

  /**
   * 공휴일 조회 결과 DTO
   */
  @Getter
  @Builder
  @ToString
  public static class HolidayResultDTO {

    private String dateName;
    private String isHoliday;
    private String locDate;

    public static HolidayResultDTO of(JsonNode item) {
      return HolidayResultDTO.builder()
        .dateName(item.path("dateName").asText(""))
        .isHoliday(item.path("isHoliday").asText(""))
        .locDate(item.path("locdate").asText(""))
        .build();
    }
  }

}
