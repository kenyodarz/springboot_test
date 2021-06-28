package com.bykenyodarz.springboot_test.repositories;

import com.bykenyodarz.springboot_test.models.Cuenta;

import java.util.List;

public interface CuentaRepository {
    List<Cuenta> findAll();

    Cuenta findById(Long id);

    void update(Cuenta cuenta);
}
