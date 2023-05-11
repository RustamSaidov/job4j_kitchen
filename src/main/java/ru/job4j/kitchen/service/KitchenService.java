package ru.job4j.kitchen.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.Order;
import net.jcip.annotations.ThreadSafe;


@ThreadSafe
@Service
@Slf4j
public class KitchenService {
//    @KafkaListener(topics = "job4j_orders")
//@KafkaListener(topics = "job4j_orders", groupId = "group-id")
//    public void receiveOrder(Order order) {
//        log.debug(order.toString());
//    }
//}

    @KafkaListener(topics = "job4j_orders")
    public void receiveOrder(Order order) {
        log.debug(order.toString());
    }
}