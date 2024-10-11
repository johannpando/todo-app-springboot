package com.johannpando.todo_app.repository;

import com.johannpando.todo_app.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
