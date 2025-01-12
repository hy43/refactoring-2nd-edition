package refactoring.app.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatementTest {

    @Test
    void statement() throws Exception {
        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Play> playsMap = objectMapper.readValue(
                new File("src/test/resources/data/plays.json"),
                new TypeReference<Map<String, Play>>() {}
        );
        Plays plays = new Plays();
        plays.setPlayMap(playsMap);

        Invoice[] invoices = objectMapper.readValue(new File("src/test/resources/data/invoices.json"), Invoice[].class);

        // 테스트할 Invoice 선택
        Invoice invoice = invoices[0];

        // Statement 객체 생성
        Statement statement = new Statement();

        // 실행 및 결과 확인
        String result = statement.statement(invoice, plays);
        String expected = "청구 내역 (고객명: BigCo)\n" +
                " Hamlet: 650.00 (55석)\n" +
                " As You Like It: 580.00 (35석)\n" +
                " Othello: 500.00 (40석)\n" +
                "총액: 1730.00\n" +
                "적립 포인트: 47점\n";

        // 결과 검증
        assertEquals(expected, result);
    }
}