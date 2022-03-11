package com.example.springstatemachine.controller;

import com.example.springstatemachine.config.simple.Events;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/simpleaction")
public class SimpleActionController {
    private StateMachine<String, String> stateMachine;

    public SimpleActionController(StateMachine<String, String> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @GetMapping("/push/{actionType}")
    public void add(@PathVariable String actionType) {
        this.stateMachine.start();

        this.stateMachine.sendEvent(Events.AUTHORIZE.toString());

        System.out.println(this.stateMachine);
    }
}
