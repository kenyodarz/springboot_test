package com.bykenyodarz.springboot_test.services;

import com.bykenyodarz.springboot_test.models.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {

    List<Cuenta> findAll();

    Cuenta findById(Long id);

    Cuenta save(Cuenta cuenta);

    void delete(Long id);

    Cuenta findByPersona(String persona);

    int revisarTotalTransactions(Long bancoId);

    BigDecimal revisarSaldo(Long cuentaId);

    void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId);
}
