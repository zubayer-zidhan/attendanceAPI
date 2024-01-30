// package com.attendance.microservices.attendanceapp.services.impl;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.amazonaws.services.iot.client.AWSIotException;
// import com.attendance.microservices.attendanceapp.config.MQTTConfig;
// import com.attendance.microservices.attendanceapp.dto.TestPayload;
// import com.attendance.microservices.attendanceapp.services.MQTTService;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @Service
// public class MQTTServiceImpl implements MQTTService {

//     @Autowired
//     MQTTConfig mqttConfig;

//     public void publishMessage(TestPayload payload) throws AWSIotException, JsonProcessingException {
//         mqttConfig.connectToAWS();

//         // Convert to stringified JSON
//         ObjectMapper mapper = new ObjectMapper();

//         mqttConfig.publish(mapper.writeValueAsString(payload));
//     }
// }
