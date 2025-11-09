package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 1. Marca a classe como um "Serviço" do Spring
public class TaskService {

    @Autowired // 2. Pede ao Spring para "injetar" (conectar) o TaskRepository aqui
    private TaskRepository taskRepository;

    // --- Esta é a regra de negócio principal ---
    public Task createTask(Task task) {
        // 3. Verifica se o título é nulo OU está em branco
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            // 4. Lança a exceção pedida na atividade [cite: 28]
            throw new IllegalArgumentException("O título não pode ser nulo ou vazio.");
        }

        // 5. Se o título for válido, salva a tarefa no banco
        return taskRepository.save(task);
    }

    // --- Métodos para os outros endpoints (vamos precisar deles no Controller) ---

    // Método para listar TODAS as tarefas
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Método para buscar UMA tarefa pelo ID
    public Task getTaskById(Long id) {
        // .orElse(null) retorna a tarefa se achar, ou null se não achar
        return taskRepository.findById(id).orElse(null);
    }
}