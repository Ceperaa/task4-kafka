package ru.clevertec.task4kafka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.task4kafka.service.MessageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody String message) {
        messageService.sendMessage(message);
    }

    @GetMapping
    public ResponseEntity<String> findMessage() {
        return new ResponseEntity<>(messageService.findMessage(),
                HttpStatus.OK);
    }
}
