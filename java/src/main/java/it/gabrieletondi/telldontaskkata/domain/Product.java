package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class Product {
    private final String name;
    private final BigDecimal price;
    private final Category category;

    public Product(String name, BigDecimal price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTaxedAmount(int quantity) {
        BigDecimal unitaryTaxedAmount = getUnitaryTaxedAmount();
        return unitaryTaxedAmount.multiply(BigDecimal.valueOf(quantity))
                .setScale(2, HALF_UP);
    }

    public BigDecimal getTaxAmount(int quantity) {
        BigDecimal unitaryTax = getUnitaryTax();
        return unitaryTax.multiply(BigDecimal.valueOf(quantity));
    }

    private BigDecimal getUnitaryTax() {
        return price.divide(BigDecimal.valueOf(100))
                .multiply(category.getTaxPercentage())
                .setScale(2, HALF_UP);
    }

    private BigDecimal getUnitaryTaxedAmount() {
        BigDecimal unitaryTax = getUnitaryTax();
        return price.add(unitaryTax).setScale(2, HALF_UP);
    }
}
