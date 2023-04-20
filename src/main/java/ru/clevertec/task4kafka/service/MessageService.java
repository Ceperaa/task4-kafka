package ru.clevertec.task4kafka.service;

public interface MessageService {

    void sendMessage(String message);

    String findMessage();
}
