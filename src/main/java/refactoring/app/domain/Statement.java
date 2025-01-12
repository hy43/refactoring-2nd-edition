package refactoring.app.domain;

import refactoring.app.dto.StatementDto;

import java.util.List;

public class Statement {

    String statement(Invoice invoice, Plays plays) throws Exception {
        StatementDto dto = new StatementDto(invoice);
        return renderPlainText(dto, plays);
    }

    private String renderPlainText(StatementDto data, Plays plays) throws Exception {
        StringBuilder result = new StringBuilder(String.format("청구 내역 (고객명: %s)\n", data.getCustomer()));

        for (Performance performance : data.getPerformances()) {
            result.append(String.format(" %s: %.2f (%d석)\n", playFor(performance, plays).getName(), (double) amountFor(performance, plays) / 100, performance.getAudience()));
        }

        result.append(String.format("총액: %.2f\n적립 포인트: %d점\n", totalAmount(data.getPerformances(), plays) / 100, totalVolumeCredits(data.getPerformances(), plays)));
        return result.toString();
    }

    private double totalAmount(List<Performance> performances, Plays plays) throws Exception {
        double result = 0;
        for (Performance performance : performances) {
            result += amountFor(performance, plays);
        }
        return result;
    }

    private int totalVolumeCredits(List<Performance> performances, Plays plays) {
        int volumeCredits = 0;
        for (Performance performance : performances) {
            volumeCredits += volumeCreditsFor(plays, performance);
        }
        return volumeCredits;
    }

    private int volumeCreditsFor(Plays plays, Performance performance) {
        int result = 0;
        result += Math.max(performance.getAudience() - 30, 0);

        if ("comedy".equals(playFor(performance, plays).getType())) {
            result += performance.getAudience() / 5;
        }
        return result;
    }

    private Play playFor(Performance performance, Plays plays) {
        return plays.findByPlayID(performance.getPlayID());
    }

    private int amountFor(Performance performance, Plays plays) throws Exception {
        int result = 0;
        switch (playFor(performance, plays).getType()) {
            case "tragedy": // 비극
                result = 40000;
                if (performance.getAudience() > 30) {
                    result += 1000 * (performance.getAudience() - 30);
                }
                break;
            case "comedy": // 희극
                result = 30000;
                if (performance.getAudience() > 20) {
                    result += 10000 + 500 * (performance.getAudience() - 20);
                }
                result += 300 * performance.getAudience();
                break;
            default:
                throw new Exception(String.format("알 수 없는 장르: %s", playFor(performance, plays).getType()));
        }
        return result;
    }
}