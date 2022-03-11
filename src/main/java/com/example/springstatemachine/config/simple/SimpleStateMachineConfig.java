package com.example.springstatemachine.config.simple;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

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
                .withExternal().source(States.AUTHORIZED.toString()).target(States.VALIDATED.toString()).event(Events.AUTHORIZE.toString());
    }
}
