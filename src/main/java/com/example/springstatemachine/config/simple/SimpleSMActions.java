package com.example.springstatemachine.config.simple;

import org.springframework.statemachine.action.Action;

public class SimpleSMActions {

    public static Action<String, String> authorizeAction() {
        return stateContext -> {
            System.out.println("SIMPLE*************** authorize event Init " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
        };
    }

    public static Action<String, String> completeAction() {
        return stateContext -> {
            System.out.println("SIMPLE*************** complete event Init " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
        };
    }

    public static Action<String, String> authorizedStateEntryAction() {
        return stateContext -> {
            System.out.println("SIMPLE*************** authorized state entry " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
        };
    }

    public static Action<String, String> authorizedStateExitAction() {
        return stateContext -> {
            System.out.println("SIMPLE*************** authorized state exit " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
        };
    }

    public static Action<String, String> authorizedStateProcessAction() {
        return stateContext -> {
            System.out.println("SIMPLE*************** authorized state process " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
        };
    }
}
