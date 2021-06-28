package com.bykenyodarz.springboot_test.repositories;

import com.bykenyodarz.springboot_test.models.Cuenta;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository {
    List<Cuenta> findAll();

    Cuenta findById(Long id);

    void update(Cuenta cuenta);
}
