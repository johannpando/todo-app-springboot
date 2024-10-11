package com.johannpando.todo_app;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johannpando.todo_app.controller.TodoController;
import com.johannpando.todo_app.entity.Todo;
import com.johannpando.todo_app.service.ITodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITodoService todoService;

    @InjectMocks
    private TodoController todoController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTodos() throws Exception {
        // Datos de prueba
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("Test Todo 1");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Test Todo 2");
        todo2.setCompleted(true);

        // Simulación del comportamiento del servicio
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(todo1, todo2));

        // Ejecución de la solicitud y verificación del resultado
        mockMvc.perform(get("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Test Todo 1"))
                .andExpect(jsonPath("$[1].title").value("Test Todo 2"));
    }

    @Test
    public void testGetTodoById() throws Exception {
        // Datos de prueba
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setCompleted(false);

        // Simulación del comportamiento del servicio
        when(todoService.getTodoById(1L)).thenReturn(Optional.of(todo));

        // Ejecución de la solicitud y verificación del resultado
        mockMvc.perform(get("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    public void testCreateTodo() throws Exception {
        // Datos de prueba
        Todo todo = new Todo();
        todo.setTitle("New Todo");
        todo.setCompleted(false);

        Todo createdTodo = new Todo();
        createdTodo.setId(1L);
        createdTodo.setTitle("New Todo");
        createdTodo.setCompleted(false);

        // Simulación del comportamiento del servicio
        when(todoService.createTodo(any(Todo.class))).thenReturn(createdTodo);

        // Ejecución de la solicitud y verificación del resultado
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Todo"));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        // Datos de prueba
        Todo todoDetails = new Todo();
        todoDetails.setTitle("Updated Todo");
        todoDetails.setCompleted(true);

        Todo updatedTodo = new Todo();
        updatedTodo.setId(1L);
        updatedTodo.setTitle("Updated Todo");
        updatedTodo.setCompleted(true);

        // Simulación del comportamiento del servicio
        when(todoService.updateTodo(eq(1L), any(Todo.class))).thenReturn(updatedTodo);

        // Ejecución de la solicitud y verificación del resultado
        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Todo"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void testDeleteTodoById() throws Exception {
        // Ejecución de la solicitud y verificación del resultado
        mockMvc.perform(delete("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verificación de que el servicio fue llamado correctamente
        verify(todoService, times(1)).deleteTodoById(1L);
    }
}
