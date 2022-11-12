package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.exception.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.*;

public class Order {
    private BigDecimal total;
    private final String currency;
    private final List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private final int id;

    public Order(){
        this.status = CREATED;
        this.items = new ArrayList<>();
        this.currency = "EUR";
        this.total = new BigDecimal("0.00");
        this.tax = new BigDecimal("0.00");
        this.id = 1;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return tax;
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

    public void addItem(OrderItem item){
        items.add(item);
        total = total.add(item.getTaxedAmount());
        tax = tax.add(item.getTax());
    }
}
