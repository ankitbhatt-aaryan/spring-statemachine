package com.example.springstatemachine.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Action {
    @Id
    @GeneratedValue
    private Long id;

    private Date createdAt;
    private Date updatedAt;
    private String actionType;
    private String state;

    public Action(String actionType, String state) {
        this.createdAt = new Date();
        this.state = state;
        this.actionType = actionType;
    }

    public Action() {

    }

    public void setState(String s) {
        this.state = s;
        this.updatedAt = new Date();
    }

    public String getState() {
        return this.state;
    }
}
