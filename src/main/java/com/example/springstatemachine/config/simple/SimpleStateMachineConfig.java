package com.example.springstatemachine.config.simple;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Configuration
@EnableStateMachine
public class SimpleStateMachineConfig extends StateMachineConfigurerAdapter<String, String> {
    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states
                .withStates()
                .initial(States.INITIATED.toString())
                .state(States.AUTHORIZED.toString())
                .state(States.VALIDATED.toString())
                .state(States.PROCESSED.toString())
                .end(States.COMPLETED.toString());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
                .withExternal().source(States.INITIATED.toString()).target(States.AUTHORIZED.toString()).event(Events.AUTHORIZE.toString())
                .and()
                .withExternal().source(States.AUTHORIZED.toString()).target(States.VALIDATED.toString()).event(Events.VALIDATE.toString())
                .and()
                .withExternal().source(States.VALIDATED.toString()).target(States.PROCESSED.toString()).event(Events.PROCESS.toString())
                .and()
                .withExternal().source(States.PROCESSED.toString()).target(States.COMPLETED.toString()).event(Events.COMPLETE.toString());
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        StateMachineListenerAdapter<String, String> adapter = new StateMachineListenerAdapter<String, String>() {

            @Override
            public void eventNotAccepted(Message<String> event) {
                System.out.println("SIMPLE************** event not accepted: " + event);
            }

            @Override
            public void transition(Transition<String, String> transition) {
                System.out.println("SIMPLE************** transition : " + transition.getSource().getId() + " -> " + transition.getTarget().getId());
            }

            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                System.out.println("SIMPLE************** state changed :" + from.getId()+ " -> " + to.getId());
            }

        };
        config.withConfiguration()
                .autoStartup(false)
                .listener(adapter);
    }
}
