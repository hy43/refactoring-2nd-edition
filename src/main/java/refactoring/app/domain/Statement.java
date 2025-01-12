package refactoring.app.domain;

public class Statement {

    String statement(Invoice invoice, Plays plays) throws Exception {
        return renderPlainText(invoice, plays);
    }

    private String renderPlainText(Invoice invoice, Plays plays) throws Exception {
        StringBuilder result = new StringBuilder(String.format("청구 내역 (고객명: %s)\n", invoice.getCustomer()));

        for (Performance performance : invoice.getPerformances()) {
            result.append(String.format(" %s: %.2f (%d석)\n", playFor(performance, plays).getName(), (double) amountFor(performance, plays) / 100, performance.getAudience()));
        }

        result.append(String.format("총액: %.2f\n적립 포인트: %d점\n", totalAmount(invoice, plays) / 100, totalVolumeCredits(invoice, plays)));
        return result.toString();
    }

    private double totalAmount(Invoice invoice, Plays plays) throws Exception {
        double result = 0;
        for (Performance performance : invoice.getPerformances()) {
            result += amountFor(performance, plays);
        }
        return result;
    }

    private int totalVolumeCredits(Invoice invoice, Plays plays) {
        int volumeCredits = 0;
        for (Performance performance : invoice.getPerformances()) {
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