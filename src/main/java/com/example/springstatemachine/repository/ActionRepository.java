package com.example.springstatemachine.repository;

import com.example.springstatemachine.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
