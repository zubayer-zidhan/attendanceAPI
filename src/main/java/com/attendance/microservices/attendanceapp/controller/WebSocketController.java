package com.attendance.microservices.attendanceapp.controller;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.attendance.microservices.attendanceapp.dto.IotSignalDTO;


@Controller
public class WebSocketController {
    // @Autowired
    // SimpMessagingTemplate template;

    // @PostMapping("/api/v1/testSignal")
    // public ResponseEntity<Void> testSignal(@RequestBody textMsgDTO textMesgDTO) {
    //     // String signal = "ON";
    //     template.convertAndSend("/topic/signal", textMesgDTO);
    //     System.out.println("Sent: " + textMesgDTO);
        
    //     return new ResponseEntity<>(HttpStatus.OK);
    // }
    

    // @MessageMapping("/device.sendSignal")
    @SendTo("/topic/signal")
    public IotSignalDTO sendSignal(@Payload IotSignalDTO signalDTO) {
        return signalDTO;
    }

}

