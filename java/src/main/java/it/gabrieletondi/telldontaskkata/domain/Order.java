package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.exception.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.exception.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.exception.ShippedOrdersCannotBeChangedException;

import java.math.BigDecimal;
import java.util.List;

import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.*;

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public static void approve(Order order, boolean isApprovedRequest) {
        if (order.isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (isApprovedRequest && order.isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (isApprovedRequest && order.isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }
        order.setStatus(isApprovedRequest ? APPROVED : REJECTED);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isApproved() {
        return status.equals(APPROVED);
    }

    public boolean isRejected() {
        return status.equals(REJECTED);
    }

    public boolean isShipped() {
        return status.equals(SHIPPED);
    }
}
