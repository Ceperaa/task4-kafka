package ru.clevertec.task4kafka.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.clevertec.task4kafka.service.MessageService;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private String message;

    @Override
    @HystrixCommand(fallbackMethod = "fallbackMethod",groupKey = "Example1")
    public void sendMessage(String message) {
        kafkaTemplate.send("some_message1", message);
        log.info("send message: " + message);
    }

    @Override
    public String findMessage() {
        return message;
    }

    @KafkaListener(topics = "some_message1", groupId = "group")
    public void listen(String message) {
        log.info("read message: " + message);
        this.message = message;
    }

    public void fallbackMethod(String message){
        log.info(message + "- fallbackMethod");
    }
}
