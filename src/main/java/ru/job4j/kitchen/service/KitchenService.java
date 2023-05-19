package ru.job4j.kitchen.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.KitchenOrder;
import ru.job4j.kitchen.model.Order;
import ru.job4j.kitchen.repository.KitchenRepository;


@ThreadSafe
@Service
@Slf4j
@AllArgsConstructor
public class KitchenService {

    private final KitchenRepository kitchenRepository;
    private final KafkaTemplate<Order, Object> kafkaTemplate;

    @KafkaListener(topics = "preorder")
    public void receiveOrder(Order order) {
        log.debug(order.toString());
        boolean isPossibleToCook = isPossibleToCook(order);
        order.setOrderStatus("impossible to complete");
        if (isPossibleToCook) {
            order.setOrderStatus("cooking");
            kafkaTemplate.send("cooked_order", order);
            var savedOrder = kitchenRepository.save(new KitchenOrder(0, order.getId(), order.getDishId()));
            order.setOrderStatus("ready for delivery");
        }
        kafkaTemplate.send("cooked_order", order);
    }

    /*Random imitation of a possibility to cook some dish in the kitchen*/
    private boolean isPossibleToCook(Order order) {
        return Math.random() < 0.5;
    }
}