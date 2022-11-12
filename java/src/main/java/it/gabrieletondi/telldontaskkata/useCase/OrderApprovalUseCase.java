package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.exception.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.exception.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.exception.ShippedOrdersCannotBeChangedException;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;

public class OrderApprovalUseCase {
    private final OrderRepository orderRepository;

    public OrderApprovalUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void run(OrderApprovalRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());
        boolean isApprovedRequest = request.isApproved();

        if (order.isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (isApprovedRequest && order.isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (isApprovedRequest && order.isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        order.setStatus(isApprovedRequest ? OrderStatus.APPROVED : OrderStatus.REJECTED);
        orderRepository.save(order);
    }

}
