package com.example.springstatemachine.controller;

import com.example.springstatemachine.config.simple.Events;
import com.example.springstatemachine.model.Action;
import com.example.springstatemachine.service.SimpleActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/simpleaction")
public class SimpleActionController {
    @Autowired
    private SimpleActionService actionService;

    @GetMapping("/get/{id}")
    public void get(@PathVariable Long id) {
        Action action = actionService.getActionById(id);
        System.out.println(action.toString());
    }

    @GetMapping("/push/{actionType}")
    public void add(@PathVariable String actionType) {
        Action action = this.actionService.add(actionType);
        StateMachine<String, String> stateMachine= this.actionService.push(action.getId());
        System.out.println(stateMachine);
    }

    @GetMapping("/process/{actionId}")
    public void processEvent(@PathVariable Long actionId) {
        StateMachine<String, String> stateMachine = actionService.process(actionId);
        System.out.println(stateMachine);
    }
}
