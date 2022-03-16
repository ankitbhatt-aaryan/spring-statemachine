package com.example.springstatemachine.config.simple;

import org.springframework.statemachine.action.Action;

public class SimpleSMActions {

    public static Action<String, String> authorizeAction() {
        return stateContext -> {
            System.out.println("SIMPLE*************** authorize Init " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
        };
    }

    public static Action<String, String> completeAction() {
        return stateContext -> {
            System.out.println("SIMPLE*************** complete Init " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
        };
    }
}
