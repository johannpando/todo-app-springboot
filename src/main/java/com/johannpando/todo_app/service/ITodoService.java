package com.johannpando.todo_app.service;

import com.johannpando.todo_app.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface ITodoService {

    // Obtener todas las tareas
    List<Todo> getAllTodos();

    // Obtener una tarea por ID
    Optional<Todo> getTodoById(Long id);

    // Crear una nueva tarea
    Todo createTodo(Todo todo);

    // Actualizar una tarea existente
    Todo updateTodo(Long id, Todo todoDetails);

    // Eliminar una tarea por ID
    void deleteTodoById(Long id);
}