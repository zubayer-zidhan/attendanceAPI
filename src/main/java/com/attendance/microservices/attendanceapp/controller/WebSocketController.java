package com.attendance.microservices.attendanceapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/device.sendSignal")
    @SendTo("/topic/signal")
    public String sendSignal(String signal) {
        return signal;
    }

}

