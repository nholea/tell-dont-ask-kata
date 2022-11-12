package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.SellItemRequest;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class OrderItem {
    private final Product product;
    private final int quantity;
    private final BigDecimal taxedAmount;
    private final BigDecimal tax;

    public OrderItem(Product product, int quantity) {
        this.product= product;
        this.quantity = quantity;
        this.taxedAmount = product.getTaxedAmount(quantity);
        this.tax = product.getTaxAmount(quantity);
    }

    public static OrderItem create(SellItemRequest itemRequest, Product product) {
        return new OrderItem(product, itemRequest.getQuantity());
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTaxedAmount() {
        return taxedAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }



}
