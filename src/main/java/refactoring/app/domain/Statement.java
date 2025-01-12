package refactoring.app.domain;

public class Statement {
    StringBuilder statement(Invoice invoice, Plays plays) throws Exception {
        double totalAmount = 0;
        int volumeCredits = 0;
        StringBuilder result = new StringBuilder(String.format("청구 내역 (고객명: %s)\n", invoice.getCustomer()));

        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.findByPlayID(performance.getPlayID());
            int thisAmount = amountFor(performance, play);

            // 포인트를 적립한다.
            volumeCredits += Math.max(performance.getAudience() - 30, 0);

            // 희극 관객 5명마다 추가 포인트를 제공한다.
            if ("comedy".equals(play.getType())) {
                volumeCredits += performance.getAudience() / 5;
            }

            // 청구 내역을 출력한다.
            result.append(String.format(" %s: %.2f (%d석)\n", play.getName(), (double) thisAmount / 100, performance.getAudience()));
            totalAmount += thisAmount;
        }
        result.append(String.format("총액: %.2f\n적립 포인트: %d점\n", totalAmount / 100, volumeCredits));
        return result;
    }

    private int amountFor(Performance performance, Play play) throws Exception {
        int thisAmount;
        switch (play.getType()) {
            case "tragedy": // 비극
                thisAmount = 40000;
                if (performance.getAudience() > 30) {
                    thisAmount += 1000 * (performance.getAudience() - 30);
                }
                break;
            case "comedy": // 희극
                thisAmount = 30000;
                if (performance.getAudience() > 20) {
                    thisAmount += 10000 + 500 * (performance.getAudience() - 20);
                }
                thisAmount += 300 * performance.getAudience();
                break;
            default:
                throw new Exception(String.format("알 수 없는 장르: %s", play.getType()));
        }
        return thisAmount;
    }
}