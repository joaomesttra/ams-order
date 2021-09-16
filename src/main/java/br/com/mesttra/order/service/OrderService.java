package br.com.mesttra.order.service;

import br.com.mesttra.order.amqp.RabbitConfig;
import br.com.mesttra.order.amqp.message.BarRequestMessage;
import br.com.mesttra.order.amqp.message.KitchenRequestMessage;
import br.com.mesttra.order.entity.Order;
import br.com.mesttra.order.enums.OrderStatus;
import br.com.mesttra.order.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;

    public OrderService(RabbitTemplate rabbitTemplate, OrderRepository orderRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.orderRepository = orderRepository;
    }

    public Order acceptOrder(Order order) {

        String orderId = UUID.randomUUID().toString();

        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ORDER_BAR_KEY,
                new BarRequestMessage(order.getBarItems(), orderId));
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ORDER_KITCHEN_KEY,
                new KitchenRequestMessage(order.getKitchenItems(), orderId));

        order.setId(orderId);
        order.setBarStatus(OrderStatus.PREPARING);
        order.setKitchenStatus(OrderStatus.PREPARING);

        return orderRepository.save(order);
    }

    @Cacheable(cacheNames = "orders", key = "#root.method.name")
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}
