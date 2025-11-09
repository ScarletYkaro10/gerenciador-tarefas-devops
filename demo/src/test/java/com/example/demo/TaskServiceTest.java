package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*; // Para assertThrows
import static org.mockito.Mockito.*; // Para when, verify, never

@ExtendWith(MockitoExtension.class) // 1. Habilita o Mockito para este teste
class TaskServiceTest {

    @Mock // 2. Cria uma simulação (mock) do TaskRepository
    private TaskRepository taskRepository;

    @InjectMocks // 3. Cria uma instância real do TaskService e injeta o mock acima nela
    private TaskService taskService;

    // --- Teste 1: O "Caminho Feliz" ---
    @Test
    void deveCriarTarefaComSucesso() {
        // Cenário (Arrange)
        Task taskParaSalvar = new Task("Título Válido", "Descrição");
        Task taskSalva = new Task("Título Válido", "Descrição");
        taskSalva.setId(1L); // Simula a tarefa como ela voltaria do banco

        // Configura o mock: "QUANDO o repository.save() for chamado, ENTÃO retorne taskSalva"
        when(taskRepository.save(any(Task.class))).thenReturn(taskSalva);

        // Ação (Act)
        Task resultado = taskService.createTask(taskParaSalvar);

        // Verificação (Assert)
        assertNotNull(resultado); // Verifica se o resultado não é nulo
        assertEquals("Título Válido", resultado.getTitle()); // Verifica se o título está correto
        assertNotNull(resultado.getId()); // Verifica se o ID foi gerado

        // Verifica se o método save() do repositório foi chamado exatamente 1 vez [cite: 46]
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // --- Teste 2: A Regra de Negócio (Caminho Triste) ---
    @Test
    void deveLancarExcecaoAoCriarTarefaComTituloVazio() {
        // Cenário (Arrange)
        Task taskComTituloVazio = new Task("", "Descrição"); // Título vazio
        Task taskComTituloNulo = new Task(null, "Descrição"); // Título nulo

        // Ação e Verificação (Act & Assert)

        // Verifica se a exceção correta (IllegalArgumentException) é lançada
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(taskComTituloVazio); // Tenta criar com título vazio
        });

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(taskComTituloNulo); // Tenta criar com título nulo
        });

        // Verifica se o método save() NUNCA foi chamado [cite: 47]
        verify(taskRepository, never()).save(any(Task.class));
    }
}