package com.bykenyodarz.springboot_test.models;

import com.bykenyodarz.springboot_test.exceptions.DineroInsuficienteException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    private Long id;
    private String persona;
    private BigDecimal saldo;

    public void debito(BigDecimal monto){
        var nuevoSaldo = this.saldo.subtract(monto);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO)< 0){
            throw new DineroInsuficienteException("Dinero insuficiente en la Cuenta");
        }
        this.saldo = nuevoSaldo;
    }
    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }
}
