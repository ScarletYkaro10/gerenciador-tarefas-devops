package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 1. Indica ao Spring que esta é uma interface de repositório
public interface TaskRepository extends JpaRepository<Task, Long> {
    // 2. É SÓ ISSO!

    // Ao estender JpaRepository<Task, Long>, o Spring automaticamente
    // nos dá métodos como:
    // - save() (para criar e atualizar)
    // - findById() (para buscar por ID)
    // - findAll() (para listar todos)
    // - deleteById() (para deletar)
    // ... e muitos outros!
}