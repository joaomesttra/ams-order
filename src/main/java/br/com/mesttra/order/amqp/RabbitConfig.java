package br.com.mesttra.order.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static String EXCHANGE_NAME = "order-exchange";

    public static String NOTIFICATION_QUEUE = "order-notification-queue";
    public static String ORDER_BAR_QUEUE = "order-bar-queue";
    public static String ORDER_KITCHEN_QUEUE  = "order-kitchen-queue";
    public static String BAR_ORDER_QUEUE = "bar-order-queue";
    public static String KITCHEN_ORDER_QUEUE = "kitchen-order-queue";

    public static String NOTIFICATION_KEY = "order-notification";
    public static String ORDER_BAR_KEY = "order-bar";
    public static String ORDER_KITCHEN_KEY = "order-kitchen";
    public static String KITCHEN_ORDER_KEY = "kitchen-order";
    public static String BAR_ORDER_KEY = "bar-order";


    @Bean
    public Exchange declareExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public Queue declareOrderBarQueue() {
        return QueueBuilder.durable(ORDER_BAR_QUEUE)
                .build();
    }


    @Bean
    public Queue declareOrderKitchenQueue() {
        return QueueBuilder.durable(ORDER_KITCHEN_QUEUE)
                .build();
    }

    @Bean
    public Queue declareBarOrderQueue() {
        return QueueBuilder.durable(BAR_ORDER_QUEUE)
                .build();
    }

    @Bean
    public Queue declareKitchenOrderQueue() {
        return QueueBuilder.durable(KITCHEN_ORDER_QUEUE)
                .build();
    }

    @Bean
    public Queue declareNotificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE)
                .build();
    }

    @Bean
    public Binding declareBindingOrderBar(Exchange exchange) {
        return BindingBuilder.bind(declareOrderBarQueue())
                .to(exchange)
                .with(ORDER_BAR_KEY)
                .noargs();
    }

    @Bean
    public Binding declareBindingOrderKitchen(Exchange exchange) {
        return BindingBuilder.bind(declareOrderKitchenQueue())
                .to(exchange)
                .with(ORDER_KITCHEN_KEY)
                .noargs();
    }

    @Bean
    public Binding declareBindingBarOrder(Exchange exchange) {
        return BindingBuilder.bind(declareBarOrderQueue())
                .to(exchange)
                .with(BAR_ORDER_KEY)
                .noargs();
    }

    @Bean
    public Binding declareBindingKitchenOrder(Exchange exchange) {
        return BindingBuilder.bind(declareKitchenOrderQueue())
                .to(exchange)
                .with(KITCHEN_ORDER_KEY)
                .noargs();
    }

    @Bean
    public Binding declareBindingNotification(Exchange exchange) {
        return BindingBuilder.bind(declareNotificationQueue())
                .to(exchange)
                .with(NOTIFICATION_KEY)
                .noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(RabbitConfig.EXCHANGE_NAME);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
