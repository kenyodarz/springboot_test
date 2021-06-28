package com.bykenyodarz.springboot_test.repositories;

import com.bykenyodarz.springboot_test.models.Banco;

import java.util.List;

public interface BancoRepository {
    List<Banco> findAll();

    Banco findByID(Long id);

    void update(Banco banco);
}
