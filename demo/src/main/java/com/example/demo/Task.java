package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // 1. Avisa ao Spring que esta classe é uma tabela no banco de dados
public class Task {

    @Id // 2. Marca este campo como a Chave Primária (ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 3. Configura o ID para ser gerado automaticamente
    private Long id;

    private String title;
    private String description;
    private boolean completed;

    // Construtor padrão (necessário para o JPA)
    public Task() {
        // O H2 Database vai usar isso aqui
    }

    // Construtor para facilitar a criação (a regra é iniciar como 'false')
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false; // 4. Garante que a tarefa sempre comece como 'false'
    }

    // Getters e Setters
    // (O Spring usa eles para acessar os campos)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}