package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.exception.*;

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

   private boolean isApproved() {
        return status.equals(APPROVED);
    }

    private boolean isRejected() {
        return status.equals(REJECTED);
    }

    private boolean isShipped() {
        return status.equals(SHIPPED);
    }

    private boolean isCreatedOrRejected() {
        return isCreated() || isRejected();
    }

    private boolean isCreated() {
        return status.equals(CREATED);
    }

    public void approve(boolean isApprovedRequest) {
        if (isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (isApprovedRequest && isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (!isApprovedRequest && isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }
        status = isApprovedRequest ? APPROVED : REJECTED;
    }

    public void ship() {
        if (isCreatedOrRejected()) {
            throw new OrderCannotBeShippedException();
        }

        if (isShipped()) {
            throw new OrderCannotBeShippedTwiceException();
        }
        status = SHIPPED;
    }
}
