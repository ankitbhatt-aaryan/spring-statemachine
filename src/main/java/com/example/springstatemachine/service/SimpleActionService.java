package com.example.springstatemachine.service;

import com.example.springstatemachine.config.simple.Events;
import com.example.springstatemachine.config.simple.States;
import com.example.springstatemachine.model.Action;
import com.example.springstatemachine.repository.ActionRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleActionService {

    private final ActionRepository actionRepository;

    private final StateMachine<String, String> stateMachine;

    private static final String ACTION_ID_HEADER = "actionId";

    public SimpleActionService(ActionRepository actionRepository, StateMachine<String, String> stateMachine) {
        this.actionRepository = actionRepository;
        this.stateMachine = stateMachine;
    }

    private StateMachine<String, String> build(Long actionId) {
        Action action = this.actionRepository.getById(actionId);
        String actionIdKey = Long.toString(action.getId());

        StateMachine<String, String> sm = this.getStateMachine();
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<String, String>() {
                        @Override
                        public void preStateChange(State<String, String> state, Message<String> message, Transition<String, String> transition, StateMachine<String, String> stateMachine) {
                            Optional.ofNullable(message).ifPresent(msg -> {
                                Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(ACTION_ID_HEADER, -1L)))
                                        .ifPresent(actionId1 -> {
                                            Action action1 = actionRepository.getById(actionId1);
                                            action1.setState(state.getId());
                                            actionRepository.save(action1);
                                        });
                            });
                        }
                    });
                    sma.resetStateMachine(new DefaultStateMachineContext<>(action.getState(), null, null, null, null, actionIdKey));
                });
        sm.start();
        return sm;
    }

    private StateMachine<String, String> getStateMachine() {
        return this.stateMachine;
    }

    private Message<String> buildMessage(Long actionId, String event) {
        Message<String> message = MessageBuilder.withPayload(String.valueOf(event))
                .setHeader(ACTION_ID_HEADER, actionId)
                .build();
        return message;
    }

    public Action getActionById(Long id) {
        return this.actionRepository.getById(id);
    }

    public Action add(String actionType) {
        return this.actionRepository.save(new Action( actionType, States.INITIATED.toString()));
    }

    public StateMachine<String, String> push(Long actionId) {
        StateMachine<String, String> sm = this.build(actionId);
        return sm;
    }

    public StateMachine<String, String> process(Long actionId) {
        StateMachine<String, String> sm = this.build(actionId);
        Message<String> eventsMessage = this.buildMessage(actionId, Events.AUTHORIZE.toString());
        sm.sendEvent(eventsMessage);

        Message<String> eventsMessage2 = this.buildMessage(actionId, Events.VALIDATE.toString());
        sm.sendEvent(eventsMessage2);

        return sm;
    }
}
