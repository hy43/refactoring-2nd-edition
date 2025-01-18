package refactoring.app.dto;

import refactoring.app.domain.Invoice;
import refactoring.app.domain.Performance;

import java.util.List;

public class StatementDto {
    private String customer;
    private List<Performance> performances;

    public StatementDto(Invoice invoice) {
        this.customer = invoice.getCustomer();
        this.performances = invoice.getPerformances();
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }
}
