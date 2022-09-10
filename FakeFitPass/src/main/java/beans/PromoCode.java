package beans;

import java.time.LocalDate;

public class PromoCode {

	private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int quantity;
    private double discountPercentage;

    public PromoCode() {}

    public PromoCode(String id, LocalDate startDate, LocalDate endDate, int quantity,
            double discountPercentage) {
        super();
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.discountPercentage = discountPercentage;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
