package ru.clevertec.task4kafka.service.impl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.task4kafka.ApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@DirtiesContext
@SpringBootTest(classes = ApplicationContext.class)
class MessageServiceImplTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private int anInt = 0;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findMessage() throws ExecutionException, InterruptedException, TimeoutException {
        kafkaTemplate.send(new ProducerRecord<>("some_message", "id-3", "value"))
                .get(1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        assertThat(anInt).isEqualTo(1);
    }

    @KafkaListener(topics = "some_message", groupId = "test")
    public void setKafkaTemplate(String kafkaTemplate) {
        anInt++;
    }

}