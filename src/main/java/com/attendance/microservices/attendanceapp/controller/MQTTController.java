package com.attendance.microservices.attendanceapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.attendance.microservices.attendanceapp.config.MQTTConfig;
import com.attendance.microservices.attendanceapp.dto.TestPayload;

import io.jsonwebtoken.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin
@RestController
@RequestMapping("api/v1/iot")
public class MQTTController {
    
    @Autowired
    MQTTConfig mqttConfig;

    @PostMapping("/publishToShadow")
    public String publishMessage(@RequestBody TestPayload payload) throws IOException {
        mqttConfig.publishToShadow();
        return "Message published successfully.";
    }
    
}
