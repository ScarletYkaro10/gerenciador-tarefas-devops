package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 1. Marca a classe como um Controlador REST (que retorna JSON)
@RequestMapping("/api/tasks") // 2. Define o caminho base para todos os endpoints desta classe
public class TaskController {

    @Autowired // 3. Pede ao Spring para injetar o TaskService
    private TaskService taskService;

    // Endpoint 1: Criar uma nova tarefa [cite: 31]
    // Mapeia para POST /api/tasks
    @PostMapping("/")
    public Task createTask(@RequestBody Task task) {
        // @RequestBody pega o JSON enviado na requisição e transforma em um objeto Task
        return taskService.createTask(task);
    }

    // Endpoint 2: Listar todas as tarefas [cite: 32]
    // Mapeia para GET /api/tasks
    @GetMapping("/")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Endpoint 3: Buscar uma tarefa por ID [cite: 33]
    // Mapeia para GET /api/tasks/{id} (ex: /api/tasks/1)
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        // @PathVariable pega o 'id' da URL e o transforma em uma variável Long
        return taskService.getTaskById(id);
    }
}