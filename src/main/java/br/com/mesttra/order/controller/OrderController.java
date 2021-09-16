package br.com.mesttra.order.controller;

import br.com.mesttra.order.entity.Order;
import br.com.mesttra.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Order addOrder(@RequestBody Order order) {
        return orderService.acceptOrder(order);
    }

    @GetMapping
    public List<Order> listOrders() {
        return orderService.listOrders();
    }

}
