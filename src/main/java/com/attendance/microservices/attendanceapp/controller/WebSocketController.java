package com.attendance.microservices.attendanceapp.controller;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.attendance.microservices.attendanceapp.dto.IotSignalDTO;

@Controller
public class WebSocketController {

    @SendTo("/topic/signal")
    public IotSignalDTO sendSignal(@Payload IotSignalDTO signalDTO) {
        return signalDTO;
    }
}

