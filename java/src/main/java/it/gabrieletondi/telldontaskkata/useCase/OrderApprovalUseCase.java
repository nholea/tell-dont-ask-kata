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

        if (isShipped(order)) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (request.isApproved() && isRejected(order)) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (!request.isApproved() && order.getStatus().equals(OrderStatus.APPROVED)) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        order.setStatus(request.isApproved() ? OrderStatus.APPROVED : OrderStatus.REJECTED);
        orderRepository.save(order);
    }

    private static boolean isRejected(Order order) {
        return order.getStatus().equals(OrderStatus.REJECTED);
    }

    private static boolean isShipped(Order order) {
        return order.getStatus().equals(OrderStatus.SHIPPED);
    }
}
