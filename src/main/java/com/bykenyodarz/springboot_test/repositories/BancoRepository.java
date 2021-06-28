package com.bykenyodarz.springboot_test.repositories;

import com.bykenyodarz.springboot_test.models.Banco;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BancoRepository {
    List<Banco> findAll();

    Banco findByID(Long id);

    void update(Banco banco);
}
