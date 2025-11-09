package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // 1. Carrega o contexto COMPLETO do Spring Boot (toda a aplicação)
@AutoConfigureMockMvc // 2. Configura o MockMvc para simular requisições HTTP
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc; // 3. Ferramenta para fazer as requisições (como um Postman automatizado)

    @Autowired
    private ObjectMapper objectMapper; // 4. Ajuda a converter objetos Java <-> JSON

    @Autowired
    private TaskRepository taskRepository; // 5. Acesso direto ao banco (para preparar o teste)

    // Teste para o endpoint POST /api/tasks
    @Test
    void deveCriarNovaTarefa() throws Exception {
        // Cenário (Arrange)
        Task task = new Task("Tarefa de Integração", "Testando o POST");
        String taskJson = objectMapper.writeValueAsString(task); // Converte o objeto para JSON

        // Ação (Act) e Verificação (Assert)
        mockMvc.perform(post("/api/tasks/") // Simula um POST
                        .contentType(MediaType.APPLICATION_JSON) // Diz que estamos enviando JSON
                        .content(taskJson)) // O JSON que estamos enviando
                .andExpect(status().isOk()) // 6. Verifica se o status é 200 OK
                .andExpect(jsonPath("$.id").exists()) // 7. Verifica se a resposta tem um 'id'
                .andExpect(jsonPath("$.title").value("Tarefa de Integração")) // 8. Verifica o título
                .andExpect(jsonPath("$.completed").value(false)); // 9. Verifica o estado 'completed'
    }

    // Teste para o endpoint GET /api/tasks
    @Test
    void deveListarTodasAsTarefas() throws Exception {
        // Cenário (Arrange)
        // Como o banco é limpo a cada teste, precisamos inserir um dado primeiro
        taskRepository.save(new Task("Tarefa na Lista", "Testando o GET"));

        // Ação (Act) e Verificação (Assert)
        mockMvc.perform(get("/api/tasks/")) // Simula um GET
                .andExpect(status().isOk()) // Verifica se o status é 200 OK
                // Verifica se a resposta é uma lista (array JSON)
                .andExpect(jsonPath("$").isArray())
                // Verifica se o primeiro item da lista ([0]) tem o título correto
                .andExpect(jsonPath("$[0].title").value("Tarefa na Lista"));
    }
}