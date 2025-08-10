package com.github.selosele.chatbot.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * XML 관련 유틸리티
 */
public class XmlUtil {

    /**
     * XML에서 아이템을 파싱하여 리스트로 변환하는 메소드
     * 
     * @param items         XML에서 파싱된 아이템 노드
     * @param factoryMethod 아이템을 생성하는 팩토리 메소드
     * @return 아이템 리스트
     */
    public static <T> List<T> parseItems(JsonNode items, Function<JsonNode, T> factoryMethod) {
        List<T> list = new ArrayList<>();

        // items가 배열인지 객체인지 확인
        if (items.isArray()) {
            for (var item : items) {
                if (item.isMissingNode() || item.isEmpty())
                    continue;
                list.add(factoryMethod.apply(item));
            }
        } else if (items.isObject()) {
            var item = items;
            if (!item.isMissingNode() && !item.isEmpty()) {
                list.add(factoryMethod.apply(item));
            }
        }
        return list;
    }

}
