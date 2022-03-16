package com.example.springstatemachine.config.simple;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.*;

//@WithStateMachine
public class SimpleSMAction2 {
    @OnTransition
    public void anyTransition(StateContext<String, String> stateContext) {
        System.out.println("SIMPLE 1****************************** transition " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
    }

    @OnTransition(source = "INITIATED", target = "AUTHORIZED")
    public void executeAuthorize(StateContext<String, String> stateContext) {
        System.out.println("SIMPLE 1****************************** executeAuthorize " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
    }

    @OnTransitionStart
    public void anyTransitionStart(StateContext<String, String> stateContext) {
        System.out.println("SIMPLE 1****************************** transition started " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
    }

    @OnTransitionEnd
    public void anyTransitionEnd(StateContext<String, String> stateContext) {
        System.out.println("SIMPLE 1****************************** transition end " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
    }

    @OnTransitionEnd(source = "AUTHORIZED", target = "VALIDATED")
    public void validateTransitionEnd(StateContext<String, String> stateContext) {
        System.out.println("SIMPLE 1****************************** validate transition end " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
    }

    @OnStateEntry(source = "VALIDATED")
    public void validatedStateExit(StateContext<String, String> stateContext) {
        System.out.println("SIMPLE 1****************************** validated state entry " + stateContext.getSource().getId() + " --> " + stateContext.getTarget().getId());
    }
}
